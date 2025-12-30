import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/15591
	// 15591 MooTube

	static int[][] usado;
	static List<Integer>[] adj;
	static int N;
	static boolean[] visited;
	static Queue<Integer> Q = new ArrayDeque<>();

	static void clearAll() {
		Q.clear();
		Arrays.fill(visited, false);
	}

	static int bfs(int start, int val) {
		int ans = 0;
		clearAll();
		visited[start] = true;
		Q.add(start);
		while (!Q.isEmpty()) {
			int cur = Q.poll();
			for (int next: adj[cur]) {
				if (visited[next] || usado[cur][next] < val) continue;
				visited[next] = true;
				ans ++;
				Q.add(next);
			}
		}
		return ans;
	}


	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		int query = Integer.parseInt(st.nextToken());

		usado = new int[N + 1][N + 1];
		adj = new ArrayList[N+1];
		for (int i = 1 ; i < N+1 ; i++) {
			adj[i] = new ArrayList<>();
		}
		visited = new boolean[N + 1];
		int temp = N - 1;

		while (temp-- > 0) {
			st = new StringTokenizer(br.readLine());
			int p = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());
			usado[p][q] = r;
			adj[p].add(q);
			usado[q][p] = r;
			adj[q].add(p);
		}

		StringBuilder sb = new StringBuilder();

		while (query -- > 0) {
			st = new StringTokenizer(br.readLine());
			int val = Integer.parseInt(st.nextToken());
			int currentVideo = Integer.parseInt(st.nextToken());
			sb.append(bfs(currentVideo, val));
			sb.append("\n");
		}

		System.out.println(sb);
	}
}