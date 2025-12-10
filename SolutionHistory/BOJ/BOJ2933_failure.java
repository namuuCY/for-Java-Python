import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/2933)
    // 2933 미네랄



    // R, C <= 100
    // 보드는 땅까지 + 1 한다
    static int[][] board;
    static int R;
    static int C;
    static int[] dx = new int[]{-1, 1, 0, 0};
    static int[] dy = new int[]{0, 0, 1, -1};
    // 미네랄을 깨는 로직
    // 미네랄이 깨졌을 때, 깨진 곳 주위로 어떤 미네랄이 있으면, 땅과 연결되어있는지를 확인
    // 위만 봐도 되나?

    // 땅과 만나는지 체크 로직
    // 떨어지는 로직
    // 미네랄 아래 쪽만 체크하는 로직
    // 아래쪽 기준으로 이동이 제일 작은거?

    static int crash(int height, boolean isFromLeft) {
        if (isFromLeft) {
            for (int i = 0; i < C ; i++) {
                if (board[height][i] != 1) continue;
                board[height][i] = 0;
                return i;
            }
        } else {
            for (int i = C - 1; i >= 0  ; i--) {
                if (board[height][i] != 1) continue;
                board[height][i] = 0;
                return i;
            }
        }
        return -1;
    }
    static int[][] deepcopy(int[][] origin) {
        int[][] copied = new int[R + 1][C];
        for (int i = 0 ; i < R+1 ; i++) {
            for (int j = 0; j < C ; j++) {
                copied[i][j] = origin[i][j];
            }
        }
        return copied;
    }

//    static int checkDistance(int r, int c) {
//        int ans = Integer.MAX_VALUE;
//        if ((r-1 < 0) || board[r-1][c] == 1) return ans;
//        while (board)
//        return ans;
//    }

    static List<int[]> bfs(int r, int c) {
//        System.out.println(r+"행" + c+"열 부터 bfs");
        Queue<int[]> Q = new LinkedList<>();
        List<int[]> points = new ArrayList<>();
        boolean[][] visited = new boolean[R + 1][C];
        Q.add(new int[]{r,c});
        int[] lowestHeight = new int[C];
        Arrays.fill(lowestHeight, Integer.MAX_VALUE);

        while (!Q.isEmpty()) {
            int[] p = Q.poll();
            visited[p[0]][p[1]] = true;
            points.add(new int[]{p[0], p[1]});
            for (int dir = 0; dir < 4; dir++) {
                int nx = p[0] + dx[dir];
                int ny = p[1] + dy[dir];
                if (nx < 0 || nx >= R + 1 || ny < 0 || ny >= C) continue;
                if (visited[nx][ny]) continue;
                if (board[nx][ny] == 1) {
                    visited[nx][ny] = true;
                    Q.add(new int[]{nx, ny});
                }
            }
        }
//        debug(visited);
        return points;
    }


    static void debug(int[][] temp) {
        StringBuilder sb = new StringBuilder();
        for (int i = R ; i >= 1 ; i--) {
            for (int j = 0 ; j< C; j++) {
                sb.append((temp[i][j] == 1)? "x":".");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static void debug(boolean[][] temp) {
        StringBuilder sb = new StringBuilder();
        for (int i = R ; i >= 1 ; i--) {
            for (int j = 0 ; j< C; j++) {
                sb.append((temp[i][j]) ? "x":".");
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    // 매 점마다 0이 아니면 추가하지 않음.
    // 그래서 아래의 케이스에서는 0인 경우가 발생해.
    //...xxxxx
    //...xx...
    //........
    static int gravity(List<int[]> points) {
        int ans = Integer.MAX_VALUE;
        for (int[] p : points) {
            int currentH = p[0];
            if (board[currentH-1][p[1]]==1) continue;
//            System.out.println("아래 있는 거가 미네랄이니?" + ((board[currentH - 1][p[1]]==1)? "네" : "아니오"));
            int gap = 0;
            while (board[currentH - gap - 1][p[1]] == 0) {
                gap ++;
            }
            ans = Math.min(gap, ans);
//            System.out.println("ans가 "+ ans + "으로 갱신됨");
        }
        return ans;
    }

    static void bfs(int r, int c, int dropped) {
//        System.out.println(r+"행" + c+"열 부터 bfs");
        Queue<int[]> Q = new LinkedList<>();
        Queue<int[]> origin = new LinkedList<>();
        boolean[][] visited = new boolean[R + 1][C];
        Q.add(new int[]{r,c});


        while (!Q.isEmpty()) {
            int[] p = Q.poll();
            visited[p[0]][p[1]] = true;
            origin.add(new int[]{p[0], p[1]});
            for (int dir = 0; dir < 4; dir++) {
                int nx = p[0] + dx[dir];
                int ny = p[1] + dy[dir];
                if (nx < 0 || nx >= R + 1 || ny < 0 || ny >= C) continue;
                if (visited[nx][ny]) continue;
                if (board[nx][ny] == 1) {
                    visited[nx][ny] = true;
                    Q.add(new int[]{nx, ny});
                }
            }
        }
        int[][] temp = new int[R + 1][C];

        while (!origin.isEmpty()) {
            int[] p = origin.poll();
            board[p[0]][p[1]] = 0;
            temp[p[0] - dropped][p[1]] = 1;
        }

        for (int i = 1 ; i < R+1; i++) {
            for (int j = 0; j < C; j++) {
                if(temp[i][j] == 0) continue;
                board[i][j] = 1;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        // 주어진 입력값은 맨 아래가
        // 입력을 거꾸로 받자
        board = new int[R + 1][C];
        // 땅은 -1 / 빈공간 0 / 미네랄 1
        Arrays.fill(board[0], -1);

        for (int i = R ; i >= 1 ; i--) {
            char[] input = br.readLine().strip().toCharArray();
            for (int j = 0 ; j< C; j++) {
                if (input[j]== '.') {
                    board[i][j] = 0;
                } else {
                    board[i][j] = 1;
                }
            }
        }

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        boolean isFromLeft = true;
        while (N-- > 0) {
            int height = Integer.parseInt(st.nextToken());
            int resultIdx = crash(height, isFromLeft);


            if (resultIdx != -1) {

                for (int dir = 0 ; dir < 4; dir++) {
                    int nx = height + dx[dir];
                    int ny = resultIdx + dy[dir];

                    if (nx < 0 || nx >= R + 1 || ny < 0 || ny >= C) continue;
                    if (board[nx][ny] == 0 || board[nx][ny] == -1) continue;
                    List<int[]> points = bfs(nx, ny);
                    int distance = gravity(points);
//                        System.out.println("떨어질 거리 : " + distance);
                    if (distance == 0) continue;
                    bfs(nx, ny, distance);
                }
            }
            isFromLeft = !isFromLeft;

        }
        debug(board);
    }
}