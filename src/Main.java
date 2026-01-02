import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/20183
    // 20183 골목 대장 호석 - 효율성 2

    static int V, E, start, end, maxW;
    static long C;
    static List<Node>[] adj;
    static PriorityQueue<Node> PQ = new PriorityQueue<>();
    static long[] dist;

    static class Node implements Comparable<Node> {
        int idx;
        long cost;

        Node(int idx, long cost) {
            this.idx = idx;
            this.cost = cost;
        }

        public int compareTo(Node that) {
            if (this.cost != that.cost) return Long.compare(this.cost, that.cost);
            return this.idx - that.idx;
        }
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());
        C = Long.parseLong(st.nextToken());

        adj = new ArrayList[V + 1];
        dist = new long[V + 1];
        for (int i = 1 ; i <= V ; i++) {
            adj[i] = new ArrayList<>();
        }
        maxW = 0;
        while (E -- > 0) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            adj[v1].add(new Node(v2, cost));
            adj[v2].add(new Node(v1, cost));
            maxW = Math.max(maxW, cost);
        }

        int ans = parametricSearch();
        System.out.println(ans);
    }

    private static int parametricSearch() {
        int startValue = 1;
        int endValue = maxW;
        int ans = -1;

        while (startValue <= endValue) {
            int midValue = (startValue + endValue) / 2;
            boolean isAvailable = checkByDijkstra(midValue);
            if (isAvailable) {
                ans = midValue;
                endValue = midValue - 1;
            } else {
                startValue = midValue + 1;
            }
        }
        return ans;
    }

    private static boolean checkByDijkstra(int maxRestrict) {
        // maxRestrict 보다 큰 간선은 pass;
        PQ.clear();
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[start] = 0;
        PQ.add(new Node(start, 0));

        while (!PQ.isEmpty()) {
            Node currentNode = PQ.poll();
            int currentNodeIdx = currentNode.idx;

            // [수정 1] 이미 비용이 C를 초과했다면 더 볼 필요 없음 (Pruning)
            if (currentNode.cost > C) continue;

            // [수정 2] 조기 종료 추가
            if (currentNodeIdx == end) return true;

            if (dist[currentNodeIdx] < currentNode.cost) continue;

            for (Node nextNode : adj[currentNodeIdx]) {
                if (nextNode.cost > maxRestrict) continue;
                if (dist[nextNode.idx] > dist[currentNodeIdx] + nextNode.cost) {
                    dist[nextNode.idx] = dist[currentNodeIdx] + nextNode.cost;
                    PQ.add(new Node(nextNode.idx, dist[nextNode.idx]));
                }
            }
        }

        return dist[end] <= C;
    }



}