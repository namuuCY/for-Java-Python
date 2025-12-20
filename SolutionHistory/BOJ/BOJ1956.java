import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/1956
	// 1956 운동

	static int V;
	static int E;

	static int[][] dist;
	static int INF = 0x3f3f3f3f;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().strip().split(" ");

		V = Integer.parseInt(input[0]);
		E = Integer.parseInt(input[1]);

		dist = new int[V + 1][V + 1];
		for (int i = 1 ; i <= V; i++) {
			Arrays.fill(dist[i], INF);
			dist[i][i] = 0;
		}

		while (E-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			dist[s][e] = d;
		}

		for (int p = 1; p <= V; p++) {
			for (int s = 1; s <= V; s++) {
				for (int e = 1; e <= V; e++) {
					if (dist[s][e] <= dist[s][p] + dist[p][e]) continue;
					dist[s][e] = dist[s][p] + dist[p][e];
				}
			}
		}

		int ans = Integer.MAX_VALUE;
		for (int s = 1; s <= V ; s++) {
			for (int e = 1; e <= V; e++) {
				if (s == e) continue;
				if (dist[s][e] + dist[e][s] >= INF) continue;
				ans = Math.min(ans, dist[s][e] + dist[e][s]);
			}
		}

		System.out.println((ans >= INF) ? -1 : ans);
	}

}