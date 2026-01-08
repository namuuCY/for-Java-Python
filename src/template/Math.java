package template;

import java.util.*;

public class Math {

	public int euclideanAlgorithm(int a, int b) {
		if (b == 0) return a;
		return euclideanAlgorithm(b, a % b);
	}

	// n 이하의 소수 개수 모두 구하기 (시간 복잡도 O(N*loglogN))
	public List<Integer> sieveOfEratosthenes(int n) {
		boolean[] isPrime = new boolean[n + 1];
		Arrays.fill(isPrime, true);
		List<Integer> primes = new ArrayList<>();

		for (int i = 2; i * i <= n ; i++) {
			if (!isPrime[i]) continue;
			for (int j = i * i ; j <= n; j += i) {
				isPrime[j] = false;
			}
		}

		for (int i = 2; i <= n; i++) {
			if (isPrime[i]) primes.add(i);
		}

		return primes;
	}

	public long eulerPhi(long n) {
		if (n == 1 || n == 2) return 1;

		long ans = n;
		for (long i = 2; i * i <= n; i++) {
			// i가 소인수인지 확인 (합성수는 앞에서 소인수가 제거되어 여기 걸리지 않음)
			if (n % i == 0) {
				ans = ans - (ans / i);

				while (n % i == 0) {
					n /= i;
				}
			}
		}
		// 아직도 n이 1보다 크다면, 루트 n 보다 큰 소수가 남아있음. 현재의 수가 소수
		if (n > 1) {
			ans = ans - (ans / n);
		}

		return ans;
	}

	public boolean isPrime(long n) {
		// 1은 소수가 아님
		if (n <= 1) return false;

		// 2와 3은 소수임
		if (n <= 3) return true;

		// 2의 배수(짝수) 또는 3의 배수 미리 제외 (속도 최적화)
		if (n % 2 == 0 || n % 3 == 0) return false;

		// 5부터 시작해서 제곱근까지만 확인
		// 6k ± 1 규칙을 이용해 6씩 증가시키며 확인 (i, i+2)
		// 혹은 단순하게 i += 2로 홀수만 확인해도 충분함
		for (long i = 5; i * i <= n; i += 6) {
			if (n % i == 0 || n % (i + 2) == 0) {
				return false;
			}
		}

		return true;
	}

	// 뤼카의 정리
	// n >= k 일떄만 성립.
	// n, k를 p진법 으로 표현했을때 각각의 자릿수의 ()값을 곱한것이 (n,k)값이다.
	// 만약 어느 하나라도 n%p < k%p 이면 그 값은 0이 나온다.

}
