import java.io.*;
import java.util.*;

public class Main {
    static final int INF = Integer.MAX_VALUE;
    static int V, E;

    static List<Node>[] graph;

    static int[][] edges;

    static class Node implements Comparable<Node>{
        int end;
        int cost;

        Node(int e, int c) {
            end = e;
            cost = c;
        }
        public int compareTo(Node n) {
            return this.cost - n.cost;
        }
    }

    static int[] dijkstra(int start) {
        int[] dist = new int[V+1];
        Arrays.fill(dist, INF);
        dist[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));

        while(!pq.isEmpty()){
            Node cur = pq.poll();
            int cNode = cur.end;
            int cCost = cur.cost;
            if(cCost > dist[cNode]) continue;

            for(Node nxt : graph[cNode]){
                int nNode = nxt.end;
                int nCost = cCost + nxt.cost;
                if(dist[nNode] > nCost){
                    dist[nNode] = nCost;
                    pq.offer(new Node(nNode, nCost));
                }
            }
        }
        return dist;
    }

    // 특정 간선 하나만 막고 다익스트라를 다시 돌려서
    // 1 -> V까지의 최단거리 반환. (도달 불가능이면 INF)
    static int blockedDijkstra(int blockU, int blockV, int standard) {
        int[] dist = new int[V+1];
        Arrays.fill(dist, INF);
        dist[1] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(1, 0));

        while(!pq.isEmpty()){
            Node cur = pq.poll();
            int cNode = cur.end;
            int cCost = cur.cost;
            if(cCost > dist[cNode]) continue;

            for(Node nxt : graph[cNode]){
                int nNode = nxt.end;
                int nCost = cCost + nxt.cost;
                // 만약 (cNode->nNode)가 차단된 간선이면 skip
                if((cNode == blockU && nNode == blockV) ||
                        (cNode == blockV && nNode == blockU)) {
                    continue;
                }
                if(dist[nNode] > nCost){
                    dist[nNode] = nCost;
                    pq.offer(new Node(nNode, nCost));
                }
            }
        }
        return dist[V];
    }

    public static void main(String[] args) throws Exception {
        // 입력 처리
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        graph = new ArrayList[V+1];
        edges = new int[E][3];

        for(int i=0; i<=V; i++){
            graph[i] = new ArrayList<>();
        }

        for(int i=0; i<E; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to   = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph[from].add(new Node(to, cost));
            graph[to].add(new Node(from, cost));
            edges[i] = new int[]{from, to, cost};
        }

        int[] dist1 = dijkstra(1);   // 1에서 각 노드까지
        int[] distN = dijkstra(V);   // V에서 각 노드까지 (역으로 다익스트라)
        int standard = dist1[V];

        //    dist1[u] + cost + distN[v] == dist1[V] 이거나 dist1[v] + cost + distN[u] == dist1[V] 이면 OK
        List<int[]> criticalEdges = new ArrayList<>();
        for(int i=0; i<E; i++){
            int u = edges[i][0];
            int v = edges[i][1];
            int c = edges[i][2];
            if(dist1[u] != INF && distN[v] != INF && dist1[u] + c + distN[v] == standard) {
                criticalEdges.add(edges[i]);
            }
            else if(dist1[v] != INF && distN[u] != INF && dist1[v] + c + distN[u] == standard) {
                criticalEdges.add(edges[i]);
            }
        }

        int gap = 0;
        for(int[] edge : criticalEdges){
            int blockU = edge[0];
            int blockV = edge[1];
            int distBlocked = blockedDijkstra(blockU, blockV, standard);

            if(distBlocked == INF){
                System.out.println(-1);
                return;
            }
            gap = Math.max(gap, distBlocked - standard);
        }

        System.out.println(gap);
    }
}
