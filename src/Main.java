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
    static int[] selectedLands;
    static int[][][] bfsBoard;


    // R + G 10개 이하
    // R, G 도 5개씩 이하
    // => 10! / 5! * 5! = 10 9 2 7  / -> 1260 가지 케이스 * 최대 2500 BFS -> 조금 빡빡한가?
    // 전부 다 위치를 지정한 후 BFS
    // 만약 해당 위치에 같은 점수이면 큐에 넣지 않는 거로 -> 이거로 되나?



// 아래가 이전 로직
//    static void backtracking(int count, int gCount, int rCount, int gMax, int rMax, int[] marked) {
//        if (count == R+G) {
//            int currentAns = bfs(marked, false);
//            if (ans < currentAns) {
//                ans = currentAns;
////                show(marked);
////                bfs(marked, true);
//            }
////            System.out.println(combCount + 1);
//            return;
//        }
//
//        int[] temp = marked.clone();
//        for (int i = Math.min(gMax,rMax); i < zoneNumber; i++) {
//            if (temp[i] != 0) continue;
//            if (gCount == G) {
//                //2-> r추가
//                temp[i] = 2;
//                backtracking(count + 1, gCount, rCount + 1, gMax, i, temp);
//            } else {
//                // 1-> g 추가
//                temp[i] = 1;
//                backtracking(count + 1, gCount + 1, rCount, i, rMax, temp);
//            }
//            temp[i] = 0;
//        }
//    }

    static void comb() {
        greenBt(0, 0);
    }

    static void greenBt(int gMax, int gCount) {
        if (gCount == G) {
            redBt(0, 0);
            return;
        }
        for (int i = gMax ; i < zoneNumber; i++) {
            if (selectedLands[i] != 0) continue;
            selectedLands[i] = 1;
            greenBt(i, gCount + 1);
            selectedLands[i] = 0;
        }

    }

    static void redBt(int rMax, int rCount) {
        if (rCount == R) {
            int currentAns = bfs(selectedLands, false);
            ans = Math.max(ans, currentAns);
            return;
        }

        for (int i = rMax ; i < zoneNumber; i++) {
            if (selectedLands[i] != 0) continue;
            selectedLands[i] = 2;
            redBt(i, rCount + 1);
            selectedLands[i] = 0;
        }
    }

    static int[][] copy() {
        int[][] temp = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                temp[i][j] = board[i][j];
            }
        }
        return temp;
    }

    static void show(int[] marked) {
        int[][] temp = copy();
        for (int i = 0; i < marked.length ; i++) {
            if (marked[i] != 0) {
                int[] rc = available.get(i);
                temp[rc[0]][rc[1]] = (marked[i]==1) ? 8 : 9;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(temp[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static int bfs(int[] marked, boolean debug) {
        //
        int ans = 0;
        Queue<int[]> Q = new LinkedList<>();
        for (int r= 0; r < N ; r++) {
            for (int c = 0; c < M; c++) {
                bfsBoard[r][c][0] = -1;
                bfsBoard[r][c][1] = -1;
            }
        }
        for (int i = 0; i < zoneNumber ; i++) {
            if (marked[i] != 0) {
                // 0 -> g
                // 1 -> r
                int[] rc = available.get(i);
                Q.add(new int[]{rc[0], rc[1], marked[i] - 1, 0});
                bfsBoard[rc[0]][rc[1]][marked[i] - 1] = 0;
            }
        }

        while (!Q.isEmpty()) {
            // 어떻게 하면 두 개 다 같은 숫자일때 멈출것인가?
                int[] info = Q.poll();
                int r = info[0];
                int c = info[1];
                int gr = info[2];
                int dist = info[3];
                if (bfsBoard[r][c][0] == bfsBoard[r][c][1]) continue;

                for (int dir = 0; dir < 4; dir++) {
                    int nr = r + dx[dir];
                    int nc = c + dy[dir];
                    if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
                    if (board[nr][nc] == 0) continue;
                    if (bfsBoard[nr][nc][gr] != -1) continue;
                    bfsBoard[nr][nc][gr] = dist + 1;
                    if (bfsBoard[r][c][0] == bfsBoard[r][c][1]) continue;
                    Q.add(new int[]{nr, nc, gr, dist + 1});
                }
        }

        for (int i = 0 ; i < N; i++) {
            for (int j = 0 ; j < M ; j++) {
                if (bfsBoard[i][j][0] > 0 && bfsBoard[i][j][1] > 0 && (bfsBoard[i][j][0] == bfsBoard[i][j][1]) ) {
                    ans++;
                }
            }
        }
        if (debug) {
            debug(bfsBoard);
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
        bfsBoard = new int[N][M][2];

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
        selectedLands = new int[zoneNumber];
        comb();

        System.out.println(ans);
    }
}