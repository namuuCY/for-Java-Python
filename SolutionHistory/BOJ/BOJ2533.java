import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/2533)
    // 2533 사회망 서비스(SNS)


    static int N;
    static List<Integer>[] adj;
    static int[] early; //
    static int[] nonEarly; //


    static void dfs(int currentNode, int parentNode) {
        early[currentNode] = 1;

        for (int nextNode : adj[currentNode]) {
            if (nextNode == parentNode) continue;
            dfs(nextNode, currentNode);
            early[currentNode] += Math.min(early[nextNode], nonEarly[nextNode]);
            nonEarly[currentNode] += early[nextNode];
        }
    }

//    static void dfs(int currentNode, int parentNode) {
//        early[currentNode] = 1;
//
//        for (int nextNode : adj[currentNode]) {
//            if (nextNode == parentNode) continue;
//            dfs(nextNode, currentNode);
//            early[currentNode] += nonEarly[nextNode];
//        }
//        // parentNode 제외한 나머지에서
//        // 현재가 early면 상관없는데,
//        // non-early면 어느 하나는 무조건 early 여야함
//        int size = (currentNode == 0) ? adj[currentNode].size() : adj[currentNode].size() - 1;
//
//        int[] ans = new int[size];
//        int count = 0;
//
//        for (int nextNode : adj[currentNode]) {
//            if (nextNode == parentNode) {
//                continue;
//            }
//
//            for (int i = 0; i < size; i++) {
//                if (count != i) {
//                    ans[i] += nonEarly[nextNode];
//                } else {
//                    ans[count] += early[nextNode];
//                }
//            }
//            count++;
//        }
//
//        int nonEarlyMin = Integer.MAX_VALUE;
//        for (int i = 0; i < size; i ++) {
//            nonEarlyMin = Math.min(nonEarlyMin, ans[i]);
//        }
//        nonEarly[currentNode] += (size == 0) ? 0 : nonEarlyMin;
//    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        adj = new ArrayList[N];
        early = new int[N];
        nonEarly = new int[N];

        for (int i = 0; i < N ; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            String[] sted = br.readLine().strip().split(" ");
            int start = Integer.parseInt(sted[0]) - 1;
            int end = Integer.parseInt(sted[1]) - 1;

            adj[start].add(end);
            adj[end].add(start);
        }

        dfs(0, -1);

        System.out.println(Math.min(early[0], nonEarly[0]));
    }
}