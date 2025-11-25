import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/4355)
    // 4355 서로소
    // n ≤ 1,000,000,000



    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder sb = new StringBuilder();
        while (true) {
            int n = Integer.parseInt(br.readLine());

            if (n == 0) break;

            if (n == 1) {
                sb.append(0 + "\n");
                continue;
            }

            int ans = n;

            for (int p = 2; p * p <= n ; p++) {
                if (n % p != 0) continue;
                ans = ans - (ans / p);
                while (n % p == 0) {
                    n /= p;
                }
            }
            if (n > 1) {
                ans = ans - (ans / n);
            }
            sb.append(ans + "\n");
        }
        System.out.println(sb);
    }
}
