import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/6159


	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken()); // 소의 수
		int S = Integer.parseInt(st.nextToken()); // 의상 크기 제한

		int[] lengths = new int[N];
		for (int i = 0; i < N; i++) {
			lengths[i] = Integer.parseInt(br.readLine());
		}

		// 1. 소들의 길이를 오름차순으로 정렬
		Arrays.sort(lengths);

		int left = 0;
		int right = N - 1;
		long count = 0;

		// 2. 투 포인터를 이용한 탐색
		while (left < right) {
			int sum = lengths[left] + lengths[right];

			if (sum <= S) {
				// lengths[left]와 더했을 때 S 이하가 되는 소들은
				// left+1 부터 right 까지 모두 가능함
				count += (right - left);
				left++; // 더 큰 값을 찾기 위해 왼쪽 포인터 이동
			} else {
				// 합이 S를 초과하면 오른쪽 포인터를 줄여서 합을 감소시킴
				right--;
			}
		}

		System.out.println(count);
	}
}