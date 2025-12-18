import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/11780
    // 11780 플로이드 2

    static int N;
    static int[][] dist;
    static int[][] next;
    static int INF = 0x3f3f3f3f;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        dist = new int[N][N];
        next = new int[N][N];
        for (int i = 0 ; i < N; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
        int M = Integer.parseInt(br.readLine());

        while (M-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken()) - 1;
            int end = Integer.parseInt(st.nextToken()) - 1 ;
            int currentDist = Integer.parseInt(st.nextToken());
            if (dist[start][end] > currentDist) {
                dist[start][end] = currentDist;
                next[start][end] = end;
            }
        }

        for (int pass = 0 ; pass < N; pass++) {
            for (int st = 0 ; st < N; st ++) {
                for (int ed = 0 ; ed < N; ed++) {
                    if (dist[st][pass] + dist[pass][ed] >= dist[st][ed]) continue;
                    dist[st][ed] = dist[st][pass] + dist[pass][ed];
                    next[st][ed] = next[st][pass];
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0 ; j < N; j++) {
                if (dist[i][j] == INF) {
                    sb.append(0);
                } else {
                    sb.append(dist[i][j]);
                }
                sb.append(" ");
            }
            sb.append("\n");
        }


        for (int i = 0 ; i < N; i++) {
            for (int j = 0 ; j < N; j++) {
                if (dist[i][j] == 0 || dist[i][j] == INF) {
                    sb.append(0);
                    sb.append("\n");
                    continue;
                }
                StringBuilder tsb = new StringBuilder();
                int count = 0;
                int start = i;
                while (start != j) {
                    count ++;
                    tsb.append(start + 1);
                    tsb.append(" ");
                    start = next[start][j];
                }
                count ++;
                tsb.append(start + 1);
                tsb.append("\n");
                sb.append(count);
                sb.append(" ");
                sb.append(tsb);
            }
        }

        bw.write(sb.toString());
        bw.flush();
    }

}