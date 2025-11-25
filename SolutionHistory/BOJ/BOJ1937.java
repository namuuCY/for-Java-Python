import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/1937)
    // 1937 욕심쟁이 판다

    // n x n 크기
    static int n;
    static int[][] board;

    static int[] dx = new int[]{1, 0, -1, 0};
    static int[] dy = new int[]{0, 1, 0, -1};


    // 해당하는 대나무 먹어 치우면 이동,
    // 먹고 자리를 옮기면 옮긴 지역에 그 전 지역보다 대나무가 많이 있어야 한다.

    // bfs + 백트래킹 ?

    // 문제 1)
    // 최대 25만개의 숫자가 있는데, 가지치기를 어떻게 할 것인가?
    // -> 백트래킹은 불가

    // 문제 2)
    // 최댓값을 구하라

    // DP? DP[i][j] = i,j를 마지막으로 하는 최댓값?
    // 가장 낮은 숫자부터 시작? 25만개 있는데

    static int[][] DP; // DP[i][j] = i,j를 마지막으로 하는 최댓값




    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());

        board = new int[n][n];
        int[][] arr = new int[n * n][3];
        DP = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] input = br.readLine().strip().split(" ");
            for (int j = 0; j < n ; j++) {
                board[i][j] = Integer.parseInt(input[j]);
                DP[i][j] = 1;
                arr[i * n + j] = new int[]{board[i][j], i, j};
            }
        }

        Arrays.sort(arr, (ar1, ar2) -> {
            if (ar1[0] != ar2[0]) {
                return ar1[0] - ar2[0];
            }
            if (ar1[1] != ar2[1]) {
                return ar1[1] - ar2[1];
            }
            return ar1[2] - ar2[2];
        });

        for (int[] cij : arr) {

            // i,j에 대해서 nx, ny 세워놓고
            // bound 맞는지 확인
            // 이전 값이 작을 경우에만 선택지에 담아두고 갱신
            int cur = cij[0];

            int ans = DP[cij[1]][cij[2]];

            for (int dir = 0 ; dir < 4; dir++) {
                int nx = cij[1] + dx[dir];
                int ny = cij[2] + dy[dir];

                if (nx >= n || nx < 0 || ny >= n || ny < 0) continue;
                if (board[nx][ny] >= cur) continue;
                ans = Math.max(ans, DP[nx][ny] + 1);
            }
            DP[cij[1]][cij[2]] = ans;
        }

        int ans = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                ans = Math.max(ans, DP[i][j]);
            }
        }

        System.out.println(ans);
    }
}
