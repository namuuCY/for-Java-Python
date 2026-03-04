import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/5616
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int r = Integer.parseInt(st.nextToken());

		// 추가로 자유롭게 더 뽑을 수 있는 구슬의 수
		int k = r - n * m;

		// 필요한 구슬의 수가 목표 개수(r)보다 많으면 0가지
		if (k < 0) {
			System.out.println(0);
			return;
		}

		// nHk = (n+k-1)C(n-1) = (n+k-1)C(k)
		int A = n + k - 1;

		// 조합의 대칭성 nCr = nC(n-r) 을 활용하여 반복 횟수 최소화
		int B = Math.min(n - 1, k);

		BigInteger result = BigInteger.ONE;

		// 조합 계산: A C B
		for (int i = 1; i <= B; i++) {
			// result = result * (A - i + 1) / i
			result = result.multiply(BigInteger.valueOf(A - i + 1)).divide(BigInteger.valueOf(i));
		}

		System.out.println(result);
	}
}