import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/16947
	// 16947 서울 지하철 2호선

	// 첫번째 생각 - 플로이드?
	// -> 순환선 어떻게 생각할거임?
	// -> 시간복잡도 V^3 인데 3000 * 3000 * 3000 -> 90억이라 불가능

	// 두번째 생각 - 텀프로젝트
	// 계속 한방향으로 전진하면 이전에 방문한 배열을 다시 방문할 수 있음.
	// 이거를 dfs 로?

	static int N;
	static List<Integer>[] adj;
	static boolean[] visited;
	static boolean[] isCycle;
	static int[] dist;

	// dfs를 돌면 무조건 순환하는건 알게 되어있음.
	static int dfs(int current, int prev) {
		visited[current] = true;

		for (int nextNode : adj[current]) {
			if (nextNode == prev) continue;

			if (visited[nextNode]) {
				isCycle[nextNode] = true;
				isCycle[current] = true;
				return nextNode;
			}
			// visited[nextNode] = false 이므로
			int returnedNode = dfs(nextNode, current);

			// 아래부터는 마킹하고 돌아온 상태
			if (returnedNode != -1) {
				isCycle[current] = true;

				return (current == returnedNode)
						? -1
						: returnedNode;
			}
		}
		return -1;
	}

	static void bfs() {
		Queue<Integer> Q = new ArrayDeque<>();

		for (int i = 1 ; i <= N; i++) {
			if (!isCycle[i]) continue;
			Q.add(i);
			dist[i] = 0;
		}

		while (!Q.isEmpty()) {
			int cur = Q.poll();
			for (int next : adj[cur]) {
				if (isCycle[next] || (dist[next] != -1)) continue;
				Q.add(next);
				dist[next] = dist[cur] + 1;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		adj = new ArrayList[N + 1];
		visited = new boolean[N + 1];
		isCycle = new boolean[N + 1];
		dist = new int[N + 1];
		Arrays.fill(dist, -1);

		for (int i = 0 ; i <= N; i++) {
			adj[i] = new ArrayList<>();
		}

		int count = N;
		while (count -- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken());
			int v2 = Integer.parseInt(st.nextToken());

			adj[v1].add(v2);
			adj[v2].add(v1);
		}

		dfs(1, 0);
		bfs();

		StringBuilder sb = new StringBuilder();
		for (int i = 1 ; i <= N; i++) {
			sb.append(dist[i]);
			sb.append(" ");
		}
		System.out.println(sb);
	}


}