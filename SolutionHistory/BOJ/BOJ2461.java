import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

public class Main {
	// https://www.acmicpc.net/problem/2461
	// 대표 선수

	// N개의 학급, 각 학급별 M 명이 있음.
	// M명중 하나씩 뽑아서 총 N명 만들때, N명의 최대와 최솟값의 차이의 최소

	// 능력치는 0 이상 1_000_000_000 이하

	// 우선 브루트 포스로 가능한가? -> 불가능
	// 어느 두 개의 점수 선정하면, 그 사이의 값이 있는지로 최소가 되는 걸 판단 가능?
	// 두 개의 점이 최대와 최소가 아닌 케이스도 있을 수 있지 않나?
	// 매개변수탐색 or 이분탐색 스멜이 강하게 나는데
	// 최소가 되는 어떤 수를 잡으면, 이 수 보다 차이가 d 만큼 나는 수를 뽑을 수 있는지 없는지 추론이 가능?
	//  -> 100만 * 1000 * log(1000) 초과.

	// 최대 값과 최솟값을 정한다면? 정한다면 가능한가?  (투포인터? 100만?)
	// -> 어떤 학급에 들어있는 수인지는 어떻게 알 건데?
	// -> 투 포인터라면 언제 왼쪽 옮기고 언제 오른쪽 옮김?

	static class Student implements Comparable<Student> {
		int index;
		int potential;

		Student(int index, int potential) {
			this.index = index;
			this.potential = potential;
		}

		@Override
		public int compareTo(Student that) {
			return Integer.compare(this.potential, that.potential);
		}
	}

	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
		StringTokenizer st = new StringTokenizer(br.readLine().strip());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		List<Student> students = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0 ; j < M; j++) {
				students.add(new Student(i, Integer.parseInt(st.nextToken())));
			}
		}
		Collections.sort(students);

		int left = 0;
		int right = 0;
		int minDiff = Integer.MAX_VALUE;

		int[] classCount = new int[N];
		int includedClassKind = 0;

		while (right < students.size()) {
			Student rightStudent = students.get(right);
			if (classCount[rightStudent.index] == 0) {
				includedClassKind++;
			}
			classCount[rightStudent.index]++;

			while (includedClassKind == N) {
				Student leftStudent = students.get(left);
				int diff = rightStudent.potential - leftStudent.potential;
				if (diff < minDiff) minDiff = diff;
				if (minDiff == 0) {
					System.out.println(0);
					return;
				}

				classCount[leftStudent.index] --;
				if (classCount[leftStudent.index] == 0) includedClassKind --;
				left ++;
			}
			right ++;
		}

		System.out.println(minDiff);
	}
}