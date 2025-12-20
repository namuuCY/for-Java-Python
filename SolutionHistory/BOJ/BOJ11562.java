import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/11562
	// 11562 백양로 브레이크

	static int n;
	static int[][] dist;
	static int INF = 0x3f3f3f3f;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		dist = new int[n+1][n+1];
		for (int i = 1; i <= n; i++) {
			Arrays.fill(dist[i], INF);
			dist[i][i] = 0;
		}

		while (m -- > 0) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			dist[u][v] = 0;

			if (b == 1) {
				dist[v][u] = 0;
			} else {
				dist[v][u] = 1;
			}
		}

		int k = Integer.parseInt(br.readLine());

		for (int p = 1; p <= n ; p++) {
			for (int s = 1; s <= n; s++) {
				for (int e = 1; e <= n; e++) {
					if (dist[s][e] <= dist[s][p] + dist[p][e]) continue;
					dist[s][e] = dist[s][p] + dist[p][e];
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		while (k -- > 0) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			sb.append(dist[s][e]);
			sb.append("\n");
		}

		bw.write(sb.toString());
		bw.flush();
	}

}