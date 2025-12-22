import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/14938
    // 14938 서강 그라운드

    static int INF = 0x3f3f3f3f;
    static int N;
    static int M;
    static int[][] dist;
    static int[] items;



    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        dist = new int[N + 1][N + 1];
        items = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            items[i] = Integer.parseInt(st.nextToken());
        }

        while (r -- > 0) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int distance = Integer.parseInt(st.nextToken());
            dist[start][end] = distance;
            dist[end][start] = distance;
        }

        for (int pass = 1; pass <= N; pass++) {
            for (int start = 1; start <= N; start ++) {
                for (int end = 1; end <= N; end ++) {
                    if (dist[start][end] <= dist[start][pass] + dist[pass][end]) continue;
                    dist[start][end] = dist[start][pass] + dist[pass][end];
                }
            }
        }

        int[] ans = new int[N + 1];

        for (int start = 1; start <= N; start++) {
            for (int end = 1; end <= N; end++) {
                if (start == end) {
                    ans[start] += items[end];
                    continue;
                }
                if (dist[start][end] > M) continue;
                ans[start] += items[end];
            }
        }
        int outputAnswer = 0;

        for (int i = 1 ; i <= N; i++) {
            outputAnswer = Math.max(outputAnswer, ans[i]);
        }

        System.out.println(outputAnswer);

    }

}