import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/1005)
    // BOJ 1005 ACM Craft


    // 건물 번호는 1번부터 ... N 번까지

    static int N; // 건물 개수
    static int K; // 규칙 개수
    static int[] bTime;
    static List<List<Integer>> edges;
    static int W;
    static int[] totalTime;

    static int DFS(int current) {
        if (totalTime[current] != -1) return totalTime[current];

        int ans = 0;
        for (int from : edges.get(current)) {
            ans = Math.max(DFS(from), ans);
        }
        totalTime[current] = ans + bTime[current];
        return totalTime[current];
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {
            String[] nkInput = br.readLine().split(" ");
            N = Integer.parseInt(nkInput[0]);
            K = Integer.parseInt(nkInput[1]);


            bTime = new int[N];
            edges = new ArrayList<>();

            totalTime = new int[N];
            Arrays.fill(totalTime, -1);

            String[] nInput = br.readLine().split(" ");
            for (int i =0 ; i < N; i++) {
                bTime[i] = Integer.parseInt(nInput[i]);
                edges.add(new ArrayList<>());
            }

            while (K-- > 0) {
                String[] kInput = br.readLine().split(" ");
                int from = Integer.parseInt(kInput[0]) - 1;
                int end = Integer.parseInt(kInput[1]) - 1;

                edges.get(end).add(from);

            }

            W = Integer.parseInt(br.readLine()) - 1;
            sb.append(DFS(W) + "\n");
        }

        bw.write(sb.toString());
        bw.flush();
    }
}
