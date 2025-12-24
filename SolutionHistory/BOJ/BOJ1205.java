import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/1205
	// 1205 등수 구하기



	static int[] list;

	static int lowerBound(int val, int start, int end) {
		int tempStart = start;
		int tempEnd = end;

		while (tempStart < tempEnd) {
			int mid = (tempStart + tempEnd) / 2;
			if (list[mid] >= val) {
				tempEnd = mid;
			} else {
				tempStart = mid + 1;
			}
		}
		return tempStart;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] inputs = br.readLine().strip().split(" ");
		int n = Integer.parseInt(inputs[0]);
		int score = (-1) * Integer.parseInt(inputs[1]);
		int P = Integer.parseInt(inputs[2]);
		if (n == 0) {
			System.out.println(1);
			return;
		}

		list = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			list[i] = (-1) * Integer.parseInt(st.nextToken());
		}

		if (n == P && score >= list[n - 1]) {
			System.out.println(-1);
			return;
		}

		int rank = lowerBound(score, 0, n) + 1;
		System.out.println(rank);
	}

}