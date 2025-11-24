import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    // Source(https://www.acmicpc.net/problem/21609)
    // 21609 상어중학교

    // 크기 N x N

    static int[][] board;

    static int n;
    static int m;
    static int ans;

    static int[] dx = new int[]{1, 0, -1, 0};
    static int[] dy = new int[]{0, 1, 0, -1};

    // 색상블록 M 이하의 자연수
    // 검은 블록 -1
    // 무지개 블록 0
    // 9 를 빈 블록이라고 하자

    // 블록그룹 체크
    // 블록 그룹 by bfs
    // 블록 그룹이 존재하는지 확인하는 메서드

    static void reset(boolean[][] isVisited) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n ; j++) {
                if (isVisited[i][j] && (board[i][j] == 0)) {
                    isVisited[i][j] = false;
                }
            }
        }
    }

    static List<Bgroup> findAllBGroup() {
        List<Bgroup> ans = new ArrayList<>();

        // 포인트는, 1,2,3,4,5 얘네들은 다 만나면 표시되어야하는데
        // 무지개블록은 표시되면 리셋해야함
        boolean[][] isVisited = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n ; j++) {
                if (!isVisited[i][j] && (board[i][j] > 0) && (board[i][j] !=9 )) {
                    Bgroup bg = new Bgroup(new Point(i,j), board[i][j]);

                    bfs(isVisited, bg);
                    reset(isVisited);
                    if (bg.points.size() > 1) {
                        ans.add(bg);
                    }
                }
            }
        }

        return ans;

    }

    static void bfs(boolean[][] isVisited, Bgroup bg) {
        Queue<Point> Q = new LinkedList<>();

        Q.add(bg.leader);

        isVisited[bg.leader.x][bg.leader.y] = true;

        while (!Q.isEmpty()) {
            Point p = Q.poll();
            for (int dir =0; dir < 4 ; dir++) {
                int nx = p.x + dx[dir];
                int ny = p.y + dy[dir];

                if (nx >= n || nx < 0 || ny >= n || ny < 0) continue;
                if (isVisited[nx][ny]) continue;
                // 0(무지개) 와 색깔만
                if (board[nx][ny] == 0 || board[nx][ny] == bg.color) {
                    isVisited[nx][ny] = true;
                    bg.points.add(new Point(nx, ny));
                    if (board[nx][ny] == 0) {
                        bg.rCnt++;
                    }
                    Q.add(new Point(nx, ny));
                }
            }
        }
    }

    // 기준 블록 중심으로 해서 블록 선택 필요


    // 회전 메서드



    // 중력 메서드

    static void gravity() {
        for (int y = 0 ; y < n; y ++) {
            for (int x = n - 2 ; x >= 0 ; x--) {
                // 맨 밑에있는것부터 확인
                if (board[x][y] == -1 || board[x][y] == 9) continue;

                int target = x;

                for (int k = x + 1 ; k < n ; k++) {
                    if (board[k][y] == 9) {
                        target = k;
                    } else {
                        break;
                    }
                }
                if (target != x) {
                    board[target][y] = board[x][y];
                    board[x][y] = 9;
                }
            }

//
//                int temp = board[x][y];
//                int tempx = x;
//
//                for (int i = 1 ; i < n ; i++) {
//                    if ( (tempx + i) >= n ) break;
//                    if (board[tempx + i][y] == -1) break;
//                    if (board[tempx + i][y] != 9) break;
//                    tempx = ;
//                }
//                if (tempx != x) {
//                    board[tempx][y] = temp;
//                    board[x][y] = 9;
//                }
//
////                while (true) {
////                    int temp = board[tempx][y];
////                    if (temp == 9 || temp == -1) break;
////                    tempx++;
////                    if(tempx >= n) break;
////                    if(board[tempx][y] == -1) break;
////                    board[tempx][y] = temp;
////                }
//            }
        }
    }


    // 검은 블록 포함 X / 적어도 하나의 일반블록 / 일반 블록 색은 모두 같아야 함
    // 기준블록 : 무지개 블록이 아닌 블록 중에서 행의 번호가 가장 작은 블록 -> 같으면 열의 번호가 가장 작은 블록

    // 크기가 가장 큰 블록 -> 같으면 기준 블록 기준으로 행이 가장 큰 것, 그 다음은 열이 가장 큰것

    // 블록 제거
    // 중력 작용 -> 검은 블록 제외한 모든 블록이 행의 번호가 큰 칸으로 이동
    // 이동은 경계를 만나기 까지

    // 아래가 필요할까?
    static class Bgroup {
        //
        int color;
        Point leader;
        List<Point> points;
        int rCnt = 0;

        Bgroup() {
            points = new ArrayList<>();
        }

        Bgroup(Point leader, int color) {
            this.color = color;
            this.leader = leader;
            points = new ArrayList<>();
            points.add(leader);
        }
    }

    // 90도 반시계방향으로 회전
    // 아오 회전
    // 3 0 -> 4 3 -> 1 4 -> 0 1
    // i j -> n-1-j i -> n-1-i n-1-j -> j n-1-i
    // a          b          c             d

    static void rotate() {
        int[][] newboard = new int[n][n];

        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                newboard[n - 1 - j][i] = board[i][j];
            }
        }
        board = newboard;
    }

    static class Point {
        int x;
        int y;

        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] nm = br.readLine().strip().split(" ");
        n = Integer.parseInt(nm[0]);
        m = Integer.parseInt(nm[1]);

        board = new int[n][n];

        for (int i = 0; i < n; i++) {
            String[] input = br.readLine().strip().split(" ");
            for (int j = 0; j <n ; j ++) {
                board[i][j] = Integer.parseInt(input[j]);
            }
        }

        while (true) {
            // 블록 그룹 다 찾아
            List<Bgroup> bgs = findAllBGroup();
            if (bgs.isEmpty()) {
                break;
            }

            List<Bgroup> sorted = bgs.stream().sorted(
                    (bg1, bg2) -> {
                        int size1 = bg1.points.size();
                        int size2 = bg2.points.size();

                        if (size1 != size2) {
                            return size2 - size1;
                        }
                        if (bg1.rCnt != bg2.rCnt) {
                            return bg2.rCnt - bg1.rCnt;
                        }

                        Point l1 = bg1.leader;
                        Point l2 = bg2.leader;

                        if (l1.x != l2.x) {
                            return l2.x - l1.x;
                        }
                        return l2.y - l1.y;
                    }
            ).collect(Collectors.toList());

            Bgroup bg = sorted.get(0);
            int sz =  bg.points.size();
            ans += sz * sz;

            for (Point p : bg.points) {
                board[p.x][p.y] = 9;
            }

            // 중력
            gravity();

            // 회전
            rotate();
            // 중력
            gravity();
        }

        System.out.println(ans);

    }
}
