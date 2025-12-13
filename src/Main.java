import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/18809
    // 18809 Gaaaaaaaaaarden


    static int[] dx = new int[]{1, 0, -1, 0};
    static int[] dy = new int[]{0, 1, 0, -1};
    static List<int[]> available = new ArrayList<>();
    static int zoneNumber;
    static int[][] board;
    static int N;
    static int M;
    static int G;
    static int R;
    static int ans = 0;


    // R + G 10개 이하
    // R, G 도 5개씩 이하
    // => 10! / 5! * 5! = 10 9 2 7  / -> 1260 가지 케이스 * 최대 2500 BFS -> 조금 빡빡한가?
    // 전부 다 위치를 지정한 후 BFS
    // 만약 해당 위치에 같은 점수이면 큐에 넣지 않는 거로 -> 이거로 되나?


    static void backtracking(int count, int gCount, int rCount, int[] marked) {
        if (count == R+G) {
            int currentAns = bfs(marked, false);
            if (ans < currentAns) {
                ans = currentAns;
                bfs(marked, true);
                System.out.println("이떄야" + Arrays.toString(marked));
            }
            return;
        }

        int[] temp = marked.clone();
        for (int i = 0; i < zoneNumber; i++) {
            if (temp[i] != 0) continue;
            if (gCount == G) {
                //2-> r추가
                temp[i] = 2;
                backtracking(count + 1, gCount, rCount + 1, temp);
            } else {
                // 1-> g 추가
                temp[i] = 1;
                backtracking(count + 1, gCount + 1, rCount, temp);
            }
            temp[i] = 0;
        }
    }



    static int bfs(int[] marked, boolean debug) {
        //

        int ans = 0;
        Queue<int[]> Q = new LinkedList<>();
        int[][][] tempBoard = new int[N][M][2];
        for (int r= 0; r < N ; r++) {
            for (int c = 0; c < M; c++) {
                for (int b = 0 ; b < 2; b++) {
                    tempBoard[r][c][b] = -1;
                }
            }
        }
        for (int i = 0; i < zoneNumber ; i++) {
            if (marked[i] != 0) {
                // 0 -> g
                // 1 -> r
                int[] rc = available.get(i);
                Q.add(new int[]{rc[0], rc[1], marked[i] - 1, 0});
                tempBoard[rc[0]][rc[1]][marked[i] - 1] = 0;
            }
        }

        while (!Q.isEmpty()) {
            // 어떻게 하면 두 개 다 같은 숫자일때 멈출것인가?
            int[] info = Q.poll();
            int r = info[0];
            int c = info[1];
            int gr = info[2];
            int dist = info[3];

            for (int dir = 0; dir < 4; dir++) {
                int nr = r + dx[dir];
                int nc = c + dy[dir];
                if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
                if (board[nr][nc] == 0) continue;
                if (tempBoard[nr][nc][gr] != -1) continue;
                tempBoard[nr][nc][gr] = dist + 1;
                if (tempBoard[nr][nc][gr^1] == dist + 1) {
                    ans ++;
                    continue;
                }
                Q.add(new int[]{nr, nc, gr, dist + 1});
            }
        }
        if (debug) {
            debug(tempBoard);
        }
        return ans;
    }

    static void debug(int[][][] temp) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(temp[i][j][0]);
                sb.append("-");
                sb.append(temp[i][j][1]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        G = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        board = new int[N][M];

        for (int r = 0 ; r < N; r ++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < M; c++) {
                board[r][c] = Integer.parseInt(st.nextToken());
                if (board[r][c] == 2) {
                    available.add(new int[]{r,c});
                }
            }
        }
        zoneNumber = available.size();
        backtracking(0,0,0, new int[zoneNumber]);

        System.out.println(ans);
    }
}