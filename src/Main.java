import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/14923
    // 미로 탈출


    static int N, M;
    static int[][] map;
    static boolean[][][] visited; // [x][y][벽파괴여부(0 or 1)]
    static int[] dx = {-1, 1, 0, 0}; // 상하좌우
    static int[] dy = {0, 0, -1, 1};

    // BFS를 위한 노드 클래스
    static class Node {
        int x, y;
        int dist;   // 현재까지 이동 거리
        int broken; // 0: 벽 안 부숨, 1: 벽 부숨

        public Node(int x, int y, int dist, int broken) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.broken = broken;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int Hx = Integer.parseInt(st.nextToken()) - 1;
        int Hy = Integer.parseInt(st.nextToken()) - 1;

        st = new StringTokenizer(br.readLine());
        int Ex = Integer.parseInt(st.nextToken()) - 1;
        int Ey = Integer.parseInt(st.nextToken()) - 1;

        map = new int[N][M];
        visited = new boolean[N][M][2]; // 0: 안 부숨, 1: 부숨

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 2. BFS 탐색
        System.out.println(bfs(Hx, Hy, Ex, Ey));
    }

    static int bfs(int startX, int startY, int endX, int endY) {
        Queue<Node> q = new LinkedList<>();

        q.offer(new Node(startX, startY, 0, 0));
        visited[startX][startY][0] = true;

        while (!q.isEmpty()) {
            Node current = q.poll();

            if (current.x == endX && current.y == endY) {
                return current.dist;
            }

            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;

                if (map[nx][ny] == 0) {
                    if (!visited[nx][ny][current.broken]) {
                        visited[nx][ny][current.broken] = true;
                        q.offer(new Node(nx, ny, current.dist + 1, current.broken));
                    }
                }
                else {
                    if (current.broken == 0 && !visited[nx][ny][1]) {
                        visited[nx][ny][1] = true;
                        q.offer(new Node(nx, ny, current.dist + 1, 1));
                    }
                }
            }
        }

        return -1;
    }
}