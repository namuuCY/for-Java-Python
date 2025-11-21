import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/11401)
    // 11401 이항계수 3

    // 자연수 N 정수 K

    // K 가 0이면 값 1
    // 자연수가 1이면 K값에 관계없이 값은 1

    // 수학 개념
    // 모듈로 역원 - 페르마의 소정리

    // n C k = n! / (n-k)! * (k)! = n .... n-k+1 / k!

    // 6 C 2 = 6 * 5 / 2!

    static int N;
    static int K;
    static int PRIME = 1_000_000_007;


    static int pactorial(int n) {
        long ans = 1;
        for (int i = 1 ; i <= n; i++) {
            ans = ans * (long) i % (long) PRIME;
        }
        return (int) ans;
    }

    static int partialPactorial(int n, int k) {
        long ans = 1;
        for (int i = n - k + 1 ; i <= n; i++) {
            ans = ans * (long) i % (long) PRIME;
        }
        return (int) ans;
    }

    static int pow(int n, int p) {
        // p값이 고정일거라 대충 큰수임

        if (n == 1) return 1;

        List<Integer> bm = bitmask(p);
        int bmSize = bm.size();
        int[] DP = new int[bmSize];
        DP[0] = n;

        for (int i = 1; i < bmSize; i++) {
            DP[i] = (int) ((long)DP[i - 1] * (long) DP[i - 1] % (long) PRIME);
        }

        int ans = 1;

        for (int i = 0; i < bmSize; i++) {
            if (bm.get(i) == 0) continue;
            ans = (int) ((long)ans * (long)DP[i] % (long) PRIME);
        }

        return ans;
    }


    static int pow2(int n, int p) {
        if (p == 0) return 1;
        if (p == 1) return n % PRIME;

        long temp = pow2(n, p / 2);

        if (p % 2 == 1) {
            return (int) ((temp * temp % PRIME) * (long) n % (long) PRIME);
        }

        return (int) (temp * temp % (long) PRIME);
    }

    static List<Integer> bitmask(int p) {
        List<Integer> arr = new ArrayList<>();
        int cur = p;
        while (cur > 0) {
            int temp = cur;
            arr.add(temp % 2);
            cur /= 2;
        }
        return arr;
    }

    static int combination(int n, int k) {

        long upper = (long) partialPactorial(n, k);
        int kPac = pactorial(k);

        long moduloInverse = pow2(kPac, PRIME - 2);
        // 페르마 소정리에 의해, kPac의 p 제곱이 kPac, p-1 제곱이 1, p-2 제곱이 인버스

        return (int) (upper * moduloInverse % (long) PRIME);

    }




    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] input = br.readLine().strip().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        if (N == 1 || K == 0) {
            System.out.println(1);
            return;
        }

        if (K == 1) {
            System.out.println(N);
            return;
        }

        int ans = combination(N, K);

        System.out.println(ans);
    }
}
