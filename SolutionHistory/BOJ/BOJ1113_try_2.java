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
    static Queue<Integer> bfsQ = new ArrayDeque<>();
    static List<Integer> ansQ = new ArrayList<>();
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
                    if (visited[r][c]) continue;
                    if (board[r][c] >= h) continue;
                    if (ans[r][c] != 0) continue; // 이미 더 높은 수면을 가졌으므로
                    bfs(visited, h, r, c);
                }
            }
        }
    }

    static Integer pointToInt(int r, int c) {
        return r * 100 + c;
    }

    static void bfs(boolean[][] visited, int height, int r, int c) {
        boolean isPossible = true;
        bfsQ.clear();
        ansQ.clear();

        bfsQ.add(pointToInt(r,c));
        ansQ.add(pointToInt(r,c));
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

                if (visited[nr][nc]) continue; // 물높이가 현재와 같아도 담으면 안됨.
                if (board[nr][nc] >= height) continue;
                visited[nr][nc] = true;

                bfsQ.add(pointToInt(nr, nc));
                ansQ.add(pointToInt(nr, nc));
            }
        }

        if (isPossible) {
            for (int rca : ansQ) {
                int tr = rca / 100;
                int tc = rca % 100;
                if (ans[tr][tc] != 0) continue;
                ans[tr][tc] = height - board[tr][tc];
                total += ans[tr][tc];
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