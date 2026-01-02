import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/1238
    // 1238 파티

    static int N, M, X;
    static List<Node>[] adjList;
    static List<Node>[] revAdjList;

    static class Node implements Comparable<Node> {
        int index;
        int cost;

        Node(int index, int cost) {
            this.index = index;
            this.cost = cost;
        }

        public int compareTo(Node that) {
            if (this.cost != that.cost) return this.cost - that.cost;
            return this.index - that.index;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        adjList = new ArrayList[N + 1];
        revAdjList = new ArrayList[N + 1];

        for (int i = 1 ; i <= N; i++) {
            adjList[i] = new ArrayList<>();
            revAdjList[i] = new ArrayList<>();
        }

        while (M -- > 0) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            adjList[start].add(new Node(end, cost));
            revAdjList[end].add(new Node(start, cost));
        }
        int[] studentsToX = new int[N + 1];
        int[] xToStudents = new int[N + 1];

        dijkstra(adjList, X, xToStudents);
        dijkstra(revAdjList, X, studentsToX);

        int ans = 0;

        for (int i = 1; i <= N; i++) {
            ans = Math.max(studentsToX[i] + xToStudents[i], ans);
        }

        System.out.println(ans);
    }

    private static void dijkstra(List<Node>[] adj, int start, int[] ans) {
        PriorityQueue<Node> PQ = new PriorityQueue<>();
        Arrays.fill(ans, Integer.MAX_VALUE);
        ans[start] = 0;
        PQ.add(new Node(X, 0));

        while (!PQ.isEmpty()) {
            Node current = PQ.poll();
            int currentIdx = current.index;

            if (ans[currentIdx] < current.cost) continue;

            for (Node next : adj[currentIdx]) {
                if (ans[currentIdx] + next.cost < ans[next.index]) {
                    ans[next.index] = ans[currentIdx] + next.cost;
                    PQ.add(new Node(next.index, ans[next.index]));
                }
            }
        }
    }


}