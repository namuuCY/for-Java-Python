import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/23258
    // 23258 밤편지

    // 플로이드 알고리즘 : 다 돌렸을때는 전역 최단거리라서 2등 정보가 누락됨
    // 쿼리를 C에 대해서 정렬시켜서, 해당 쿼리가 돌아갔을 때 계산할 것
    static int N;
    static int Q;
    static int INF = 0x3f3f3f3f;
    static int[][] dist;
    static int[] ans;

    static class Query {
        int start;
        int end;
        int index;

        Query(int start, int end, int index) {
            this.start = start;
            this.end = end;
            this.index = index;
        }
    }

    static void command(int C, List<Query>[] queries) {
        for (Query q : queries[C]) {
            ans[q.index] = (dist[q.start][q.end] >= INF)
                    ? -1
                    : dist[q.start][q.end];
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        dist = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
        ans = new int[Q];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int currentDist = Integer.parseInt(st.nextToken());
                if (i == j) continue;
                if (currentDist == 0) continue;
                dist[i][j] = currentDist;
            }
        }

        List<Query>[] queries = new ArrayList[N + 2];
        for (int i = 1; i <= N + 1; i++) {
            queries[i] = new ArrayList<>();
        }

        for (int idx = 0 ; idx < Q; idx++) {
            st = new StringTokenizer(br.readLine());

            int C = Integer.parseInt(st.nextToken());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            queries[C].add(new Query(start, end, idx));
        }
        command(1, queries);

        for (int pass = 1; pass <= N; pass++) {

            for (int s = 1; s <= N; s++) {
                for (int e = 1; e <= N; e++) {
                    if (dist[s][pass] + dist[pass][e] >= dist[s][e]) continue;
                    dist[s][e] = dist[s][pass] + dist[pass][e];
                }
            }
            command(pass + 1, queries);
        }

        StringBuilder sb = new StringBuilder();

        for (int a : ans) {
            sb.append(a);
            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
    }

}