import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

public class Main {
    // https://www.acmicpc.net/problem/2629
    // 양팔저울

    static int N; // 추의 개수
    static int[] weights; // 추의 무게들
    static boolean[][] dp; // dp[i][w]: i번째 추까지 고려했을 때 무게 w를 만들 수 있는지 여부

    // 추의 최대 개수 30개 * 최대 무게 500g = 15,000g이 측정 가능한 최대 무게
    static final int MAX_WEIGHT = 15000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        weights = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            weights[i] = Integer.parseInt(st.nextToken());
        }
        dp = new boolean[N + 1][MAX_WEIGHT + 1];

        solve(0, 0);

        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < M; i++) {
            int bead = Integer.parseInt(st.nextToken());

            if (bead > MAX_WEIGHT) {
                sb.append("N ");
            }
            else if (dp[N][bead]) {
                sb.append("Y ");
            } else {
                sb.append("N ");
            }
        }

        System.out.println(sb);
    }

    static void solve(int idx, int currentWeight) {
        if (dp[idx][currentWeight]) return;

        dp[idx][currentWeight] = true;
        if (idx == N) return;
        solve(idx + 1, currentWeight);
        solve(idx + 1, currentWeight + weights[idx]);
        solve(idx + 1, Math.abs(currentWeight - weights[idx]));
    }

}