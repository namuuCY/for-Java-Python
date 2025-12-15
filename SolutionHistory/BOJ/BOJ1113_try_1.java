import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/1113
    // 1113 수영장 만들기

    static int R;
    static int C;
    static int[][] board;
    static int[] dx = new int[]{1, 0, -1, 0};
    static int[] dy = new int[]{0, 1, 0, -1};
    static int[][] ans;
    static boolean[][] visited;
    static Queue<Integer> bfsQ = new LinkedList<>();
    static List<Integer> ansQ = new ArrayList<>();
    //    static Set<Integer> ansSet = new HashSet<>();
//    static Queue<int[]> bfsQ = new LinkedList<>();
//    static List<int[]> ansQ = new ArrayList<>();
    static int total = 0;


    static boolean oob(int r, int c) {
        return r < 0 || r >= R || c < 0 || c >= C;
    }

    static void clear() {
        for (int r = 0 ; r < R ; r++) {
            for (int c= 0 ; c < C; c++) {
                visited[r][c] = false;
            }
        }
    }

    static void bfsWithHeight() {
        for (int h = 9; h >= 2 ; h--) {
            clear();
            for (int r = 1 ; r < R - 1; r++) {
                for (int c = 1 ; c < C - 1 ; c++) {

                    // 1) visited 초기화가 잘못되었다.
                    // 2) 높이 계산이 7일때 되네?
                    if (visited[r][c]) continue;
                    if (board[r][c] >= h) continue;
                    if (ans[r][c] != 0) continue; // 이미 더 높은 수면을 가졌으므로
                    bfs(visited, h, r, c);
//                    System.out.println("이떄 높이 : " + h + "r-c : " + r +" - " + c);
//                    debug(board);
//                    debug(ans);
                }
            }
        }
    }

    static void debug(int[][] ans) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < R ; i++) {
            for (int j = 0; j < C; j++) {
                sb.append(ans[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    static boolean[][] copy(boolean[][] origin) {
        boolean[][] temp = new boolean[R][C];
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                temp[r][c] = origin[r][c];
            }
        }
        return temp;
    }


    static Integer pointToInt(int r, int c) {
        return r * 100 + c;
    }

    static Integer pointAnsToInt(int r, int c, int ans) {
        return r * 10000 + c * 100 + ans;
    }

    static void bfs(boolean[][] visited, int height, int r, int c) {
//        boolean[][] temp = copy(visited);
        boolean isPossible = true;
        bfsQ.clear();
        ansQ.clear();
        bfsQ.add(pointToInt(r,c));
        ansQ.add(pointAnsToInt(r, c, height - board[r][c]));
//        bfsQ.add(new int[]{r, c});
//        ansQ.add(new int[]{r, c, height - board[r][c]});
        visited[r][c] = true;
        while (!bfsQ.isEmpty()) {
            int rc = bfsQ.poll();
            for (int dir = 0 ; dir < 4; dir++) {
                int nr = (rc / 100) + dx[dir];
                int nc = rc % 100 + dy[dir];
                if (oob(nr, nc)) {
                    isPossible = false;
                    continue;
                }

//                System.out.println("nr, nc : " + nr + " 행 " + nc);
//                System.out.println("nr, nc visited? : " + visited[nr][nc]);

                if (visited[nr][nc]) continue; // 물높이가 현재와 같아도 담으면 안됨.
                if (board[nr][nc] >= height) continue;
                visited[nr][nc] = true;

                bfsQ.add(pointToInt(nr, nc));
                ansQ.add(pointAnsToInt(nr, nc, height - board[nr][nc]));
            }
        }

        if (isPossible) {
            for (int rca : ansQ) {
                int tr = rca / 10000;
                int tc = (rca % 10000) / 100;
                int gap = rca % 100;
                if (ans[tr][tc] != 0) continue;
                ans[tr][tc] = gap;
                total += gap;
            }
        }

    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        board = new int[R][C];
        ans = new int[R][C];
        visited = new boolean[R][C];

        for (int r = 0 ; r < R; r++) {
            char[] input = br.readLine().strip().toCharArray();
            for (int c = 0 ; c < C ; c++) {
                board[r][c] = (int) input[c] - (int) '0';
            }
        }
        bfsWithHeight();

        System.out.println(total);
    }
}