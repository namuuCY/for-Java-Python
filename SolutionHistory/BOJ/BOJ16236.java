import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/16236)
    // BOJ 16236 - 아기상어

    // BFS로 이동
    // 자기 크기보다 큰 물고기 있는 칸 지나갈 수 없음 (같은 경우는 지나갈 수는 있음)
    //

    // 안됐던 이유 1 : distance, visited 같이 하려다 보니까 문제났음.
    // 안됐던 이유 2 : 매 update를 제대로 해주지 않았음. (이동한 distance 업데이트 하지 않음
    // 안됐던 이유 3 : 위치 비교 메서드를 잘못 설정함. x,y로 헷갈리게 하지 말고 인덱스 기준으로 할 것.
    // 안됐던 이유 4 : 우선순위 큐에 집어넣는 숫자를 아무 숫자나 막 넣었음. 그래서 이동 거리가 0인것도 들어가버리니까 무한루프 발생

    static int N;
    static int[][] board;
    static Baby baby;

    static class Baby {
        int currentSize = 2;
        int consumed = 0;
        int distance = 0;
        Coordinate current;

        Baby(int x, int y) {
            this.current = new Coordinate(x,y);
        }


        void move() {
            // 판 새로 짜기 / 못가는 곳 막기
            while(true) {
                int[][] field = new int[N][N];
                boolean[][] isVisited = new boolean[N][N];

                for (int i = 0; i < N ; i++) {
                    for (int j = 0; j < N ; j++) {
                        if (board[i][j] <= currentSize) continue;
                        field[i][j] = -1;
                    }
                }

                Target t = select(field, isVisited);

                if (Objects.isNull(t)) {

                    System.out.println(distance);
                    return;
                }

                consume(t);

            }

        }

        // 이동을 위한 평가
        // 이동할 대상 선택 BFS
        // 이동하고(시간 추가)
        Target select(int[][] field, boolean[][] isVisited) {

            PriorityQueue<Target> PQ = new PriorityQueue<>();
            BFS(field, PQ, isVisited);

            if (PQ.isEmpty()) return null;

            Target t=  PQ.poll();

//            System.out.println("들어가는 t 거리 : " + field[t.c.x][t.c.y] +" \n x,y="+ t.c.x + t.c.y );
            PQ.clear();
            return t;
        }

        void BFS(int[][] field, PriorityQueue<Target> PQ, boolean[][] isVisited) {

            int[] dx = new int[]{1, 0, -1, 0};
            int[] dy = new int[]{0, 1, 0, -1};
            Queue<Coordinate> Q = new LinkedList<>();

            Q.add(this.current);
            // 아래 조건이 잘못되긴 했다

//            // distance + visited 같이 쓰려고 하니까 이런 문제가 생김.
//            field[this.current.x][this.current.y] = -1;
            isVisited[this.current.x][this.current.y] = true;

            while (!Q.isEmpty()) {
                Coordinate c = Q.poll();
                for (int i = 0; i < 4; i++) {
                    int nx = c.x + dx[i];
                    int ny = c.y + dy[i];
                    // 경계체크
                    if (nx >= N || ny >= N || nx < 0 || ny < 0) continue;
                    // 못가는 곳이면 continue;
                    if (isVisited[nx][ny]) continue;
                    if (field[nx][ny] == -1) continue;

                    field[nx][ny] = field[c.x][c.y] + 1;
                    isVisited[nx][ny] = true;
                    Q.add(new Coordinate(nx, ny));

                    // 아 이거를 계속해서 막 넣으니까...
                    if (board[nx][ny] < currentSize && board[nx][ny] > 0) {

                        PQ.add(new Target(field[nx][ny],nx, ny));
                    }
                }
            }
        }

        //  판이랑 본인 상태 바꾸기
        void consume(Target t) {
            int current = consumed + 1;
            consumed = current % currentSize;
            currentSize += (current) / currentSize;
            board[t.c.x][t.c.y] = 0;
            this.current = t.c;
            this.distance += t.distance;
        }

    }

    static class Coordinate implements Comparable<Coordinate> {
        int x;
        int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // y 좌표가 작은게 먼저 -> 이렇게 하면 틀리네
        // 인덱스 기준으로 살펴봐야함
        // 00 01
        // 10 11   이 비교메서드 기준이면 20이 11보다 앞이라서 오류
        // 20 21
//        public int compareTo(Coordinate o) {
//            if (this.y != o.y) return this.y-o.y;
//            return this.x - o.x;
//        }

        public int compareTo(Coordinate o) {
            if (this.x != o.x) return this.x-o.x;
            return this.y - o.y;
        }
    }

    static class Target implements Comparable<Target> {
        int distance;
        Coordinate c;

        Target(int distance, int x, int y) {
            this.distance = distance;
            this.c = new Coordinate(x, y);
        }
        public int compareTo(Target o) {
            if (this.distance != o.distance) return this.distance-o.distance;
            return c.compareTo(o.c);
        }
    }





    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        board = new int[N][N];

        for (int i = 0; i < N ; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0 ; j < N ; j++) {
                Integer number = Integer.parseInt(st.nextToken());

                if (number == 9) {
                    board[i][j] = 0;
                    baby = new Baby(i, j);
                    continue;
                }

                board[i][j] = number;
            }
        }

        baby.move();

    }
}
