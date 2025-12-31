import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/1753
    // 1753 최단 경로

    static int V, E, K;
    static final int INF = Integer.MAX_VALUE;
    static List<Node>[] graph;
    static int[] dist;

    static class Node implements Comparable<Node> {
        int idx;
        int cost;

        public Node(int idx, int cost) {
            this.idx = idx;
            this.cost = cost;
        }

        // 비용이 작은 순서대로 꺼내기 위해 오름차순 정렬
        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken()); // 정점 개수
        E = Integer.parseInt(st.nextToken()); // 간선 개수
        K = Integer.parseInt(br.readLine());  // 시작 정점

        // 그래프 초기화
        graph = new ArrayList[V + 1];
        for (int i = 1; i <= V; i++) {
            graph[i] = new ArrayList<>();
        }

        // 간선 정보 입력
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            // u에서 v로 가는 가중치 w
            graph[u].add(new Node(v, w));
        }

        // 다익스트라 실행
        dijkstra(K);

        // 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= V; i++) {
            if (dist[i] == INF) {
                sb.append("INF\n");
            } else {
                sb.append(dist[i]).append("\n");
            }
        }
        System.out.print(sb);
    }

    static void dijkstra(int start) {
        // 1. 수첩(dist) 초기화
        dist = new int[V + 1];
        Arrays.fill(dist, INF);

        // 2. PQ 준비 및 시작점 등록
        PriorityQueue<Node> pq = new PriorityQueue<>();
        dist[start] = 0;
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            // 3. 가장 가까운 곳 꺼내기
            Node current = pq.poll();
            int now = current.idx;
            int nowCost = current.cost;

            // 4. [중요] 이미 더 짧은 경로로 방문한 적이 있다면 스킵
            // (PQ에는 옛날에 넣은 더 비싼 경로가 남아있을 수 있음)
            if (dist[now] < nowCost) {
                continue;
            }

            // 5. 주변 도시 탐색
            for (Node next : graph[now]) {
                // 현재 위치를 거쳐서 가는 게 더 빠르다면?
                if (dist[next.idx] > dist[now] + next.cost) {
                    // 수첩 갱신
                    dist[next.idx] = dist[now] + next.cost;
                    // PQ에 "이 경로가 최신이에요!" 하고 등록
                    pq.offer(new Node(next.idx, dist[next.idx]));
                }
            }
        }
    }
}