import java.io.*;
import java.util.*;

public class Main {
    // 일단 시간복잡도 10 10 10 10 ???????
    // 못풀었던 이유
    // 1. 파란색이 구멍안에 들어갔다고 너무 빨리 return해버림
    // 2. crx, rx를 헷갈려서 잘못씀...

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        Queue<int[]> Q = new LinkedList<>();
        int[] dx = new int[]{1, 0, -1, 0};
        int[] dy = new int[]{0, 1, 0, -1};
        char[][] board = new char[N][M];
        int[][][][] trial = new int[N][M][N][M];

        for (int i = 0; i < N; i++ ) {
            board[i] = br.readLine().trim().toCharArray();}

        int rx = 0;
        int ry = 0;
        int bx = 0;
        int by = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M ; j++) {
                if (board[i][j] == 'R') {
                    rx = i;
                    ry = j;
                    board[i][j] = '.';
                }
                if (board[i][j] == 'B') {
                    bx = i;
                    by = j;
                    board[i][j] = '.';
                }
                for (int k = 0 ; k < N ; k++) {
                    Arrays.fill(trial[i][j][k], -1);
                }
            }
        }
        Q.offer(new int[]{rx, ry, bx, by});
        trial[rx][ry][bx][by] = 0;

        while (!Q.isEmpty()) {
            int[] cur = Q.poll();
            int crx = cur[0];
            int cry = cur[1];
            int cbx = cur[2];
            int cby = cur[3];

            for (int i = 0; i < 4; i++) {
                int nrx = crx;
                int nry = cry;
                int nbx = cbx;
                int nby = cby;

                while (board[nbx + dx[i]][nby+dy[i]] == '.') {
                    nbx += dx[i];
                    nby += dy[i];
                }

                if (board[nbx + dx[i]][nby + dy[i]] == 'O') {
                    continue;
                }

                while (board[nrx + dx[i]][nry + dy[i]] == '.') {
                    nrx += dx[i];
                    nry += dy[i];
                }
                if (board[nrx + dx[i]][nry + dy[i]] == 'O') {
                    System.out.println(trial[crx][cry][cbx][cby] + 1);
                    return;
                }
                if ((nrx == nbx) && (nry == nby)) {
                    if (i == 0) {
                        if (crx > cbx) nbx -= 1;
                        else nrx -= 1;
                    } else if (i == 1) {
                        if (cry > cby) nby -= 1;
                        else nry -= 1;
                    } else if (i == 2) {
                        if (crx > cbx) nrx += 1;
                        else nbx += 1;
                    } else {
                        if (cry > cby) nry += 1;
                        else nby += 1;
                    }
                }
                if (trial[nrx][nry][nbx][nby] == -1) {
                    trial[nrx][nry][nbx][nby] = trial[crx][cry][cbx][cby] + 1;
                    Q.offer(new int[]{nrx, nry, nbx, nby});
                }
            }
        }
        System.out.println(-1);
    }
}