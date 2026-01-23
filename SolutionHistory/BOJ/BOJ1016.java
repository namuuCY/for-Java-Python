import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

public class Main {
	// https://www.acmicpc.net/problem/1016
	// 제곱 ㄴㄴ 수

	// - 1001000 이하의 소수를 전부 구함
	// - boolean[] checked 배열을 첫번째가 min값, 맨마지막이 max값에 일대일 대응되도록 하고
	// - for 문에서 (min값 / (소수 제곱 값)) * 소수제곱값 에서부터 소수제곱값씩 더하면서 checked를 올린다
	// - 다돌리고난다음에 false 인 것만 개수출력

	static long minNumber, maxNumber;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
		StringTokenizer st = new StringTokenizer(br.readLine());

		minNumber = Long.parseLong(st.nextToken());
		maxNumber = Long.parseLong(st.nextToken());

		List<Integer> primes = initPrimes();

		boolean[] isDividedBySquare = new boolean[(int) (maxNumber - minNumber + 1)];

		for (int prime : primes) {
			long square = (long) prime * prime;
			long start = minNumber / square;
			if (minNumber % square != 0) {
				start ++;
			}
			start = start * square;

			for (long j = start ; j <= maxNumber; j+= square) {
				isDividedBySquare[(int)(j - minNumber)] = true;
			}
		}

		int ans = 0;
		for (boolean isDivided : isDividedBySquare) {
			if (!isDivided) ans++;
		}

		System.out.println(ans);
	}

	private static List<Integer> initPrimes() {
		boolean[] isPrime = new boolean[1001001];
		Arrays.fill(isPrime, true);
		for (long i = 2; i * i <= maxNumber; i++) {
			if (!isPrime[(int) i]) continue;
			for (long j = i * i; j < 1001001; j += i) {
				isPrime[(int) j] = false;
			}
		}

		List<Integer> primes = new ArrayList<>();
		for (long i = 2; i * i <= maxNumber; i++) {
			if (isPrime[(int) i]) {
				primes.add((int) i);
			}
		}
		return primes;
	}
}