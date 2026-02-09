import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/1975

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		if (!sc.hasNextInt()) return;
		int T = sc.nextInt(); // 테스트 케이스 개수

		StringBuilder sb = new StringBuilder();
		while (T-- > 0) {
			int N = sc.nextInt();
			sb.append(solve(N)).append("\n");
		}
		// 한꺼번에 출력하여 출력 속도 최적화
		System.out.print(sb.toString());
	}

	public static long solve(int N) {
		long totalZeros = 0;

		// 진법 b는 2부터 N까지 확인 (b > N이면 0의 개수는 무조건 0)
		for (int b = 2; b <= N; b++) {
			int tempN = N;
			// N이 b로 나누어 떨어지는 동안 0의 개수 증가
			while (tempN > 0 && tempN % b == 0) {
				totalZeros++;
				tempN /= b;
			}
		}
		return totalZeros;
	}
}