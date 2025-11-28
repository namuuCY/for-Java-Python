import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/19237)
    // 19237 어른 상어

    static int N;
    static int M;
    static int k;

    // 위 0 아래 1 왼쪽 2 오른쪽 3
    static int[] dx = new int[]{-1, 1, 0, 0};
    static int[] dy = new int[]{0, 0, -1, 1};
    static Scent[][] board;
    static Shark[] sharks;
    static int sharksCount;

    static class Shark {
        int x;
        int y;
        int dir;
        int rank;
        boolean isAlive;

        List<List<Integer>> pr = new ArrayList<>();

        Shark(int x, int y, int rank) {
            this.isAlive = true;
            this.x = x;
            this.y = y;
            this.rank = rank;
            for (int i = 0; i < 4; i++) {
                pr.add(new ArrayList<>());
            }
        }

        void priority(int currentDir, String[] inputs) {
            for (String s : inputs) {
                pr.get(currentDir).add(Integer.parseInt(s) - 1);
            }
        }

        void move() {
            int possibleDir = findDirection();
            // 위치를 바꿈
            this.x += dx[possibleDir];
            this.y += dy[possibleDir];
            // 방향을 바꿈
            this.dir = possibleDir;
        }

        int findDirection() {
            List<Integer> currentPriority = pr.get(dir);

            for (int i = 0; i < 4 ; i ++) {
                int nx = x + dx[currentPriority.get(i)];
                int ny = y + dy[currentPriority.get(i)];
                if (nx < 0 || nx >= N || ny < 0 || ny >= N) continue;
                if (board[nx][ny] == null) {
                    return currentPriority.get(i);
                }
            }

            for (int i = 0; i < 4; i ++) {
                int nx = x + dx[currentPriority.get(i)];
                int ny = y + dy[currentPriority.get(i)];
                if (nx < 0 || nx >= N || ny < 0 || ny >= N) continue;
                if (board[nx][ny].marker == rank) {
                    return currentPriority.get(i);
                }
            }
            // 1) 아무 냄새가 없는 칸
            //  i ) 여러 개일 경우 자기 방향에 따라 우선순위 고려
            // 2) 자신의 냄새가 있는 칸
            //  i ) 여러 개일 경우 자기 방향에 따라 우선순위 고려
            // 없으면 무조건 오류뜰것
            return -1;
        }

    }

    static class Scent {
        int count;
        int marker;

        Scent(int marker) {
            this.count = k;
            this.marker = marker;
        }
    }

    static void sharkMove() {
        Shark[][] sboard = new Shark[N][N];
        Scent[][] cboard = new Scent[N][N];

        // 상어 이동해 & 먹어 & 냄새 새 보드에 저장해
        for (int i = 0; i < M; i ++) {

            Shark s = sharks[i + 1];
            if (!s.isAlive) continue;
            s.move();
            int nextX = s.x;
            int nextY = s.y;

            if (sboard[nextX][nextY] == null) {
                sboard[nextX][nextY] = s;
                cboard[nextX][nextY] = new Scent(s.rank);
            } else {
                Shark preExists = sboard[nextX][nextY];
                sharksCount --;
                if (preExists.rank < s.rank) {
                    sboard[nextX][nextY] = preExists;
                    s.isAlive = false;
                    cboard[nextX][nextY] = new Scent(preExists.rank);
                } else {
                    sboard[nextX][nextY] = s;
                    preExists.isAlive = false;
                    cboard[nextX][nextY] = new Scent(s.rank);
                }
            }
        }

        // 그리고 cboard 에는 없는데, board에는 있으면 업데이트

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N ; j++) {
                if (cboard[i][j] == null && board[i][j] == null) continue;
                // 기존에 있던것
                if (cboard[i][j] == null && board[i][j] != null) {
                    if (board[i][j].count == 1) {
                        board[i][j] = null;
                    } else {
                        board[i][j].count --;
                    }
                    continue;
                }
                if (cboard[i][j] != null ) {
                    board[i][j] = cboard[i][j];
                }
            }
        }
    }
    static void debug() {

        StringBuilder sb = new StringBuilder();
        int[][] board = new int[N][N];
        for (int i = 1 ; i < M + 1 ; i++) {
            Shark s = sharks[i];
            if (!s.isAlive) continue;
            board[s.x][s.y] = s.rank;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(board[i][j] + " ");
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
        k = Integer.parseInt(st.nextToken());

        sharks = new Shark[M + 1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                Integer rank = Integer.parseInt(st.nextToken());
                if (rank.equals(0)) continue;
                sharks[rank] = new Shark(i, j, rank);
            }
        }

        // 현재 방향 설정
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < M + 1 ; i++) {
            sharks[i].dir = Integer.parseInt(st.nextToken()) - 1;
        }

        // 우선순위 입력
        for (int i = 1; i < M + 1; i++) {
            for (int j = 0; j < 4; j++) {
                sharks[i].priority(j, br.readLine().strip().split(" "));
            }
        }

        // 냄새용 보드 세팅
        board = new Scent[N][N];

        // 냄새 보드에 초기 냄새 넣기
        for (int i = 1 ; i < M + 1 ; i++) {
            Shark s = sharks[i];
            board[s.x][s.y] = new Scent(s.rank);
        }

        // 정답을 위한 값 세팅

        sharksCount = M;

        // 상어 이동
        // 상어 이동시에는
        // 1) 아무 냄새가 없는 칸
        //  i ) 여러 개일 경우 자기 방향에 따라 우선순위 고려
        // 2) 자신의 냄새가 있는 칸
        //  i ) 여러 개일 경우 자기 방향에 따라 우선순위 고려
        // 상어는 방향을 바꿈
        // 상어가 잡아먹을때를 위해서 그 동안에는 board 사용

        // 기존 냄새는 1씩 줄이고 상어가 board에 추가 냄새 남김

        // 상어가 1이면 종료 혹은 1000초가 넘어도 종료
        int ans = 0;
        while (ans++ < 1000) {
            sharkMove();
//            System.out.println("현재 회차 : " + ans);
//            debug();
            if (sharksCount == 1) {
                System.out.println(ans);
                return;
            }
        }

        System.out.println(-1);

    }
}
