import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/1463

    static int[] dp ;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        int n = Integer.parseInt( br.readLine() );
        dp = new int[n + 1];
        Arrays.fill(dp, -1);

        int answer = dfs(n);

        System.out.println(answer);
    }

    private static int dfs(int n) {
        if (n == 1) return 0;

        if (dp[n] != -1) return dp[n];

        int answer = 10_000_000;

        if (n % 3 == 0) {
            answer = Math.min(answer, dfs(n / 3) + 1);
        }

        if (n % 2 == 0) {
            answer = Math.min(answer, dfs(n / 2) + 1);
        }

        answer = Math.min(answer, dfs(n - 1) + 1);

        return dp[n] = answer;
    }
}