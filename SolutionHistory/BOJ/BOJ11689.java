import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/11689)
    // 11689 GCD(n, k) = 1


    // 자연수 10^12 까지 -> long으로 볼것
    // 오일러 피 함수 없이 푼다면

    // 소수를 먼저 다 구한다 (root n 까지의 숫자)

    // 이 소수들 중에서 안나눠지는 숫자가 있다면 그 배수들은 전부 다 + 해야함
    // root n 이상의 소수들은?

    // root n은 최대 10^6

    static boolean[] isPrime = new boolean[1_000_005];
    static List<Long> dividable = new ArrayList<>();


    public static void main(String[] args) throws Exception {

        // 먼저, 에라토스테네스 체 이용해서 모든 소수 다 구하기

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        long n = Long.parseLong(br.readLine());


        long ans = n;

        if (n == 1) {
            System.out.println(ans);
            return;
        }

        Arrays.fill(isPrime, true);

        for (long i = 2; i * i <= n; i++) {
            if (!isPrime[(int) i]) continue;

            if (n % i == 0) {
                ans = ans - (ans / i);
            }

            while (n % i == 0) {
                n /= i;
            }
        }
        // 45 = 3, 5 -> 45 * 2/3 * 4/5 =

        if (n > 1) {
            ans = ans - (ans / n);

        }

        System.out.println(ans);
    }
}
