import java.io.*;
import java.util.*;

public class Main {
    static class Node implements Comparable<Node> {
        int city;
        int cost;

        public Node(int city, int cost) {
            this.city = city;
            this.cost = cost;
        }

        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());

        List<List<Node>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph.get(from).add(new Node(to, cost));
        }

        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        System.out.println(cal(N, graph, start, end));
    }

    private static int cal(int N, List<List<Node>> graph, int start, int end) {

        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<Node> P = new PriorityQueue<>();
        P.offer(new Node(start, 0));

        while (!P.isEmpty()) {
            Node current = P.poll();
            int currentCity = current.city;
            int currentCost = current.cost;

            if (currentCost > dist[currentCity]) continue;

            for (Node next : graph.get(currentCity)) {
                int nextCity = next.city;
                int nextCost = currentCost + next.cost;

                if (nextCost < dist[nextCity]) {
                    dist[nextCity] = nextCost;
                    P.offer(new Node(nextCity, nextCost));
                }
            }
        }

        return dist[end];
    }
}