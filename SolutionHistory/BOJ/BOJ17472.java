import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/17472)
    // 17472 다리만들기 2

    static int N; // 행 길이
    static int M; // 열 길이

    static int[] dx = new int[]{1, 0, -1, 0};
    static int[] dy = new int[]{0, 1, 0, -1};

    // union-find 개념도 있어야하고,

    // 최소 거리를 위한 백트래킹도 있어야?

    static int[][] map;
    static boolean[][] visited;
    static int[][] edge; // edge[i][j] =  i 섬에서 j으로 가는 최소 다리 길이
    static int[] rank;
    static int[] root;

    static void bfs(int i, int j, int colorNum) {
        Queue<int[]> Q = new LinkedList<>();
        Q.add(new int[]{i,j});
        visited[i][j] = true;
        map[i][j] = colorNum;
        while (!Q.isEmpty()) {
            int[] xy = Q.poll();
            for (int dir = 0; dir < 4; dir ++) {
                int nx = xy[0] + dx[dir];
                int ny = xy[1] + dy[dir];

                if (nx < 0 || nx >= N || ny < 0 || ny >= M) continue;
                if (visited[nx][ny] || map[nx][ny] == 0) continue;
                visited[nx][ny] = true;
                map[nx][ny] = colorNum;
                Q.add(new int[]{nx, ny});
            }
        }
    }

    static int find(int u) {
        if (!(u == root[u])) {
            root[u] = find(root[u]);
        }
        return root[u];
    }

    // 두개가 union 작업이 이미 완료됐으면 false, 아니면 true
    static boolean union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rootU == rootV) return false;

        if (rank[rootU] > rank[rootV]) {
            root[rootV] = rootU;
        } else {
            root[rootU] = rootV;
            if (rank[rootU] == rank[rootV]) {
                rank[rootV] ++;
            }
        }
        return true;
    }

    static class Edge implements Comparable<Edge> {
        int st;
        int ed;
        int weight;

        Edge(int st, int ed, int weight) {
            this.st = st;
            this.ed = ed;
            this.weight = weight;
        }

        public int compareTo(Edge that) {
            if (this.weight != that.weight) {
                return this.weight - that.weight;
            }
            if (this.st != that.st) {
                return this.st - that.st;
            }
            return this.ed - that.ed;
        }
    }

    static void show() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N ; i++) {
            for (int j =0 ; j < M; j++) {
                sb.append(map[i][j] + " ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }


    static boolean isSameRoot(int length) {
        boolean ans = true;
        for (int i = 1 ; i < length; i++) {
            ans = ans && (find(i) == find(i + 1));
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 세로 N 가로 M;
        String[] nm = br.readLine().strip().split(" ");
        N = Integer.parseInt(nm[0]);
        M = Integer.parseInt(nm[1]);

        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            String[] ms = br.readLine().strip().split(" ");
            for (int j = 0 ; j < M ; j++) {
                map[i][j] = Integer.parseInt(ms[j]);
            }
        }

        // 지도 구역 정리 (섬) -> bfs 그리고 섬 숫자 이름좀 바꿔놓자
        int colorNumber = 0;
        visited = new boolean[N][M];
        for (int i = 0; i < N; i ++ ) {
            for (int j = 0; j < M ; j++) {
                if (map[i][j] != 0 && !visited[i][j]) {
                    colorNumber ++;
                    bfs(i,j, colorNumber);

                }
            }
        }

        edge = new int[colorNumber + 1][colorNumber + 1];

        for (int[] arr : edge) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }


        // 섬 별로 구할 수 있는 모든 다리 구하기
        for (int i = 0 ; i < N ; i++) {
            for (int j = 0 ; j < M ; j++) {
                // 4방향 전수조사 많아봤자 40개임
                // 다리 찾는 메서드
                int currentIsland = map[i][j];
                for (int dir = 0; dir < 4; dir++) {
                    int nx = i + dx[dir];
                    int ny = j + dy[dir];
                    if (nx < 0 || nx >= N || ny < 0 || ny >= M) continue;
                    if (map[nx][ny] != 0) continue;

                    int length = 1;
                    int nextIsland = 0;

                    while (true) {
                        nx += dx[dir];
                        ny += dy[dir];

                        // break 조건 경계 바깥 혹은 같은 색상
                        if (nx < 0 || nx >= N || ny < 0 || ny >= M || map[nx][ny] == currentIsland) {
                            length = -1;
                            break;
                        }
                        //
                        if (map[nx][ny] == 0) {
                            length ++;
                        } else {
                            nextIsland = map[nx][ny];
                            break;
                        }
                    }

                    if (length < 2) continue;

                    edge[currentIsland][nextIsland] = Math.min(edge[currentIsland][nextIsland], length);
                    edge[nextIsland][currentIsland] = Math.min(edge[nextIsland][currentIsland], length);
                }
            }
        }

        // 다리 길이는 무조건 2 이상
        // 빗겨치는 다리는 X 무조건 직선으로 이어져야함.
        // -> 만약 어떤 두 섬간 다리가 여러 개 라면? 최소인 하나만 생각하자

        rank = new int[colorNumber + 1];
        root = new int[colorNumber + 1];

        for (int i = 1; i < colorNumber+1 ; i++) {
            root[i] = i;
        }

//        show();
        PriorityQueue<Edge> PQ = new PriorityQueue<>();

        for (int i = 1; i < colorNumber ; i++ ) {
            for (int j = i + 1; j < colorNumber + 1 ; j++) {
                if (edge[i][j] != Integer.MAX_VALUE) {
                    PQ.add(new Edge(i, j, edge[i][j]));
                }
            }
        }


        // 최소 신장 트리? -> 프림, 크루스칼이니까 union-find가 나오는거겟지?

        // edge가 다 채워졌으므로 그걸 활용

        int ans = 0;
        int count = 0;
        while (!PQ.isEmpty()) {
            Edge e = PQ.poll();
//            System.out.println("st : " + e.st + " ed : " + e.ed + " weight : " + e.weight);

            if (!union(e.st, e.ed)) continue;
//            System.out.println(Arrays.toString(root));
            count ++;
            ans += e.weight;

            if (isSameRoot(colorNumber)) {

                break;
            }
        }
        //
//        System.out.println("count : " + count);
//        System.out.println(Arrays.toString(root));
        if (!isSameRoot(colorNumber)) {
            System.out.println(-1);
            return;
        }

        System.out.println(ans);

    }
}
