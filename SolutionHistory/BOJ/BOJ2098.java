import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/2098)
    // ⭐️ 2098 외판원 순회문제

    // 1~N 번까지 도시들
    // N개의 도시를 거쳐 다시 돌아오는 비용의 최소화
    // 비용은 대칭적이지 않아.
    // w[i][j] = 0이면 갈 수 없는 경우 -> 게산 넘겨야한다

    static int N;
    static int[][] edge;
    static int[][] DP; // [여태까지 방문한 인덱스들 비트화][마지막에 방문한 곳]

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        edge = new int[N][N];

        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().strip().split(" ");
            for (int j = 0; j < N; j++) {
                edge[i][j] = Integer.parseInt(input[j]);
            }
        }

        DP = new int[(1<<N)][N];
        for (int[] arr : DP) {
            Arrays.fill(arr, 20_000_000);
        }

        DP[1][0] = 0;

        // 이미 0번도시 시작인 상태임
        for (int bm = 1; bm < (1 << N); bm++) { // 2^16 최대
            // 0번 도시 안거쳤으면 pass
            if ((bm & 1) == 0) continue;

            for (int current = 0 ; current < N ; current++) { // 최대 16 DP[여태쓴 사람들 = i][j] // k는 다음
                // 계산안해도 되는 숫자 pass :
                if ((bm & (1 << current)) == 0 || DP[bm][current] == 20_000_000) continue;

                for (int next = 0; next < N; next++) {

                    if ((bm & (1 << next)) == 0 && edge[current][next] > 0) {

                        int nextBm = bm | (1 << next);

                        DP[nextBm][next] = Math.min(
                                DP[nextBm][next],
                                DP[bm][current] + edge[current][next]
                        );
                    }
                }
            }
        }
        int ans = 20_000_000;
        for (int i = 1; i < N; i++) {
            if (DP[(1 << N) - 1][i] != 20_000_000 && edge[i][0] > 0) {
                ans = Math.min(ans, DP[(1 << N) - 1][i] + edge[i][0]);
            }
        }

        System.out.println(ans);

    }
}
