import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
	// https://www.acmicpc.net/problem/16565
	// 16565 N포커

	// 1~3 까지는 쉬움
	// 4 쉬움
	// 5 ~ 7 쉬움
	// 8부터 시작
	// 13 * 4개 아무거나 뽑기 - 중복되는 케이스 ()
	// combination이 있어서 쉽지 않네
	// DP 적 개념으로 생각해볼때

	// DP[페어의 개수][뽑은 카드 숫자] -> 어려움.

	// DP[]

	// DP[n][k] = DP[n-1][k-1] + DP[n-1][k] 이용해서 채우고

	static int[][] DP = new int[53][53];
	static int prime = 10_007;

	static void init() {
		for (int n = 0 ; n <= 52; n++) {
			DP[n][0] = 1;
		}
		DP[1][1] = 1;

		for (int n = 1; n <= 52; n++) {
			for (int k = 1 ; k <= n; k++) {
				DP[n][k] = (DP[n - 1][k - 1] + DP[n - 1][k]) % prime;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 조합 + 포함배제의 원리
		int N = Integer.parseInt(br.readLine());
		init();

		int tuple = N / 4;
		int ans = 0;
		for (int i = 1; i <= tuple; i++) {
			int next = (DP[13][i] * DP[52- (i * 4)][N - (i * 4)]) % prime;
			if (i % 2 == 1) {
				ans = (ans + next) % prime;
			} else {
				ans = (ans - next + prime) % prime;
			}
		}

		System.out.println(ans);
	}
}