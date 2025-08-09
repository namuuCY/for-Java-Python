import java.io.*;
import java.util.*;

public class Main {
    // N=300, M=20, 아마 시간복잡도 O(N^2 * M)일것?
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] profit = new int[N + 1][M + 1];
        int[][] choice = new int[N + 1][M + 1];

        for (int i = 1; i <= N ; i++) {
            st = new StringTokenizer(br.readLine());
            st.nextToken();
            for (int j = 1; j <= M ; j++) {
                profit[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] DP = new int[N + 1][M + 1];

        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                for (int k = 0; k <= j ; k++) {
                    if (DP[j][i] < DP[j - k][i - 1] + profit[k][i]) {
                        DP[j][i] = DP[j - k][i - 1] + profit[k][i];
                        choice[j][i] = k;
                    }
                }
            }
        }
        int ans = DP[N][M];
        int[] history = new int[M + 1];
        int total = N;
        for (int i = M; i > 0; i--) {
            history[i] = choice[total][i];
            total -= choice[total][i];
        }
        System.out.println(ans);
        for (int i = 1; i <= M ; i++) {
            System.out.print(history[i] + " ");
        }
    }
}