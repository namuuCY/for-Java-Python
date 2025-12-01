import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/14517)
    // 14517 팰린드롬 개수 구하기
    // 점화식 구하는게 너무 빡셌다

    static long P = 10_007;
    static int N;
    static long[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[] origin = br.readLine().strip().toCharArray();

        N = origin.length;

        if (N == 1) {
            System.out.println(1);
            return;
        } else if (N == 2) {
            int ans = origin[0] == origin[1] ? 3 : 2;
            System.out.println(ans);
            return;
        }

        dp = new long[N][N];

        // gap 0 -> 다 채워넣어야함
        for (int gap = 0; gap < N; gap ++) {
            if (gap == 0) {
                for (int i = 0; i < N; i++) {
                    dp[i][i] = 1;
                }
                continue;
            }
            for (int i = 0 ; i < N - gap ; i ++) {
                if (origin[i] == origin[i + gap]) {
                    dp[i][i+gap] = ((dp[i + 1][i + gap] + dp[i][i + gap - 1]) + 1);
                } else {
                    dp[i][i+gap] =
                            (
                                    ((dp[i + 1][i + gap] + dp[i][i + gap - 1]))
                                            - (dp[i + 1][i + gap - 1])
                            );
                }
            }
        }
        System.out.println(dp[0][N - 1]);
    }
}