import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int[][] w;
    static int[][] dp;


    static int getLCS(int cur, int mask) {

        if (dp[cur][mask] != 0) {
            return dp[cur][mask];
        }

        int maxLen = 1;

        for (int next = 0; next < N; next++) {
            if ((mask & (1 << next)) == 0 && w[cur][next] == 1) {
                maxLen = Math.max(maxLen, 1 + getLCS(next, mask | (1 << next)));
            }
        }

        return dp[cur][mask] = maxLen;
    }

    // 경로 역추적 및 출력
    static void reconstructPath(int cur, int mask) {
        StringBuilder sb = new StringBuilder();
        sb.append(cur + 1).append(" ");

        while (true) {
            boolean foundNext = false;
            for (int next = 0; next < N; next++) {
                if ((mask & (1 << next)) == 0 && w[cur][next] == 1) {
                    // "내 DP값" == "다음 노드 DP값 + 1" 이라면 그쪽이 최적 경로임
                    if (dp[cur][mask] == dp[next][mask | (1 << next)] + 1) {
                        sb.append(next + 1).append(" ");

                        // 상태 업데이트 및 이동
                        mask |= (1 << next);
                        cur = next;
                        foundNext = true;
                        break;
                    }
                }
            }
            if (!foundNext) break;
        }
        System.out.println(sb);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        w = new int[N][N];
        // dp[20][약 100만] -> int형(4byte) * 2천만 = 약 80MB (메모리 충분)
        dp = new int[N][1 << N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                w[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int maxLen = getLCS(0, 1);

        System.out.println(maxLen);
        reconstructPath(0, 1);
    }
}