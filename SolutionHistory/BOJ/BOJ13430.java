import java.io.*;
import java.util.Objects;


public class Main {

    // Source(https://www.acmicpc.net/problem/13430)
    // 13430 합 구하기
    // 하키스틱 정리임

    // S(n, k) = n+k C k + 1

    static int P = 1_000_000_007;

    static long factorial(long k) {
        long ans = 1;
        for (int i = 1; i < k+1 ; i++) {
            ans = (ans * i) % P;
        }
        return ans;
    }

    static long pow(long a, int p) {
        if (p == 0) return 1;
        if (p == 1) return a % P;

        long temp = pow(a, p / 2);

        if (p % 2 == 1) {
            return ( (temp * temp) % P ) * a % P;
        }
        return ( (temp * temp) % P );
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] kn = br.readLine().strip().split(" ");

        int k = Integer.parseInt(kn[0]);
        int n = Integer.parseInt(kn[1]);

        // n C k = n! / n-k ! k! = n ... n-k +1 / k!
        // n+k C k + 1 = n+k * ... n / (k+1)!

        long ans = 1;

        for (int i = n + k ; i >=n ; i--) {
            ans = ans * i % P;
        }

        long f = factorial(k + 1);
        long inv = pow(f, P - 2);

        ans = ans * inv % P;

        System.out.println(ans);
    }
}
