import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/20057
    // 20057 마법사 상어와 토네이도

    static int N;
    static int[][] board;
    // 왼, 아래, 오, 위
    static int[] dx = new int[]{0, 1, 0, -1};
    static int[] dy = new int[]{-1, 0, 1, 0};

    static long oob = 0;
    // 토네이도가 모래먼지 분배

    // 이동 방향
    // 이전 위치
    // 현재 위치
    static void distribute(int dir, int r, int c) {
        int forward = dir;
        int backward = (dir + 2) % 4;
        int left = (dir + 1) % 4;
        int right = (dir + 3) % 4;

        int originalSand = board[r][c];
        int restSand = board[r][c];

        // 해당 방향에 얼마만큼

        // 10퍼 for + le or right
        restSand -= oobOrAdd(r, c, new int[]{forward, left}, 0.1, originalSand);
        restSand -= oobOrAdd(r, c, new int[]{forward, right}, 0.1, originalSand);

        // 5퍼 for + for

        restSand -= oobOrAdd(r, c, new int[]{forward, forward}, 0.05, originalSand);

        // left or right
        restSand -= oobOrAdd(r, c, new int[]{left}, 0.07, originalSand);
        restSand -= oobOrAdd(r, c, new int[]{right}, 0.07, originalSand);

        // left left or right right
        restSand -= oobOrAdd(r, c, new int[]{left, left}, 0.02, originalSand);
        restSand -= oobOrAdd(r, c, new int[]{right, right}, 0.02, originalSand);

        // back left or back right
        restSand -= oobOrAdd(r, c, new int[]{backward, left}, 0.01, originalSand);
        restSand -= oobOrAdd(r, c, new int[]{backward, right}, 0.01, originalSand);

        int nr = r + dx[forward];
        int nc = c + dy[forward];

        if (oob(nr, nc)) {
            oob += restSand;
        } else {
            board[nr][nc] += restSand;
        }
        board[r][c] = 0;
    }

    static boolean oob(int r, int c) {
        return r < 0 || r >= N || c < 0 || c >= N;
    }

    static int oobOrAdd(int r, int c, int[] dir, double percent, int original) {
        int cr = r;
        int cc = c;
        for (int d : dir) {
            cr += dx[d];
            cc += dy[d];
        }

        int sand = (int) ((double) original * percent);

        if (oob(cr, cc)) {
            oob += sand;
        } else {
            board[cr][cc] += sand;
        }

        return sand;
    }

    // 토네이도 이동

    // 토네이도 이동 방향 변경 여부 확인
    // 현재 이동 방향 기준으로
    // 방향은 (dir + 1 ) % 4

    static int nextDirection(int currentDir, int r, int c) {
        if ((r + c == N - 1) ) {
            return (currentDir + 1) % 4;
        } else if ((r == c) && (r > (N / 2))) {
            return (currentDir + 1) % 4;
        } else if ((r - 1) == c && r <= N / 2) {
            return (currentDir + 1) % 4;
        }
        return currentDir;
    }



    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        board = new int[N][N];

        for (int i = 0 ; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int currentR = N / 2;
        int currentC = N / 2;
        int currentDir = 0; // (왼쪽)
        //
        int count = 0;
        while (count ++ < N * N - 1) {
            // 이동하고,
            currentR += dx[currentDir];
            currentC += dy[currentDir];
            // 분배하고,
            distribute(currentDir, currentR, currentC);
            // 다음방향 반영,
            currentDir = nextDirection(currentDir, currentR, currentC);
        }

        System.out.println(oob);

    }

}