import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/1944)
    // 1944 복제 로봇

    static int N;
    static int M;
    static int[][] board;
    static int[] dx = new int[]{1, 0, -1, 0};
    static int[] dy = new int[]{0, 1, 0, -1};

    static int[] rank;
    static int[] parent;
    // 로봇은 원하는 개수만큼 만들 수 있음.
    // 사실상 그냥 MST 문제로 보인다

    static int find(int u) {
        if (u != parent[u]) {
            parent[u] = find(parent[u]);
        }
        return parent[u];
    }

    static boolean union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rootU == rootV) return false;

        if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else {
            parent[rootU] = rootV;
            if (rank[rootU] == rank[rootV]) {
                rank[rootV] ++;
            }
        }
        return true;
    }

    static class Edge implements Comparable<Edge>{
        int start;
        int end;
        int len;

        Edge(int start, int end, int len) {
            this.start = start;
            this.end = end;
            this.len = len;
        }

        public int compareTo(Edge that) {
            if (this.len != that.len) return this.len - that.len;
            if (this.start != that.start) return this.start - that.start;
            return this.end - that.end;
        }
    }

    static void bfs(int[] xymarker, PriorityQueue<Edge> PQ) {
        int[][] distance = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(distance[i], -1);
        }
        Queue<int[]> Q = new LinkedList<>();

        int x = xymarker[0];
        int y = xymarker[1];
        int marker = xymarker[2];
        distance[x][y] = 0;
        Q.add(new int[]{x,y,0});
        while (!Q.isEmpty()) {
            int[] xy = Q.poll();
            int cx = xy[0];
            int cy = xy[1];
            int curDistance = xy[2];
            for (int dir = 0; dir < 4; dir++) {
                int nx = cx + dx[dir];
                int ny = cy + dy[dir];
                if (nx < 0 || nx >= N || ny < 0 || ny >= N) continue;
                if (board[nx][ny] == -1 || distance[nx][ny] >= 0) continue;
                distance[nx][ny] = curDistance + 1;
                Q.add(new int[]{nx, ny, curDistance + 1});
                if (board[nx][ny] >= 1) {
                    PQ.add(new Edge(marker, board[nx][ny], curDistance + 1));
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N][N];

        // BFS 로 거리 계산
        // 거리를 집어넣을 우선순위 큐가 필요
        PriorityQueue<Edge> PQ = new PriorityQueue<>();

        List<int[]> member = new ArrayList<>();
        int marker = 1;
        for (int i = 0; i < N; i++ ){
            char[] tmp = br.readLine().strip().toCharArray();
            for (int j = 0; j < N; j++) {
                if (tmp[j] == 'S' || tmp[j] == 'K') {
                    board[i][j] = marker;
                    member.add(new int[]{i,j, marker});
                    marker ++;
                } else if (tmp[j] == '1') {
                    // 미로의 벽
                    board[i][j] = -1;
                }
            }
        }
        // bfs 돌리고, 간선 PQ에 저장
        for (int[] xymarker : member) {
            bfs(xymarker, PQ);
        }

        rank = new int[M + 2];
        parent = new int[M + 2];

        for (int i = 1 ; i< M + 2 ; i++) {
            parent[i] = i;
        }

        int vCount = 0;
        int ans = 0;
        // union-find를 통해서 최소신장트리 계산
        //  열쇠는 M, 시작지점까지 1 이므로 총 M+1 -> 간선 개수는 M개
        while (!PQ.isEmpty()) {
            Edge e = PQ.poll();
            // 이미 같으면 건너뜀
            if (!union(e.start, e.end)) continue;
            vCount ++;
            ans += e.len;
            if (vCount >= M) {
                System.out.println(ans);
                return;
            }
        }

        System.out.println(-1);

    }
}
