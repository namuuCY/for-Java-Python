import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

public class Main {
	// https://www.acmicpc.net/problem/1707
	//


	static ArrayList<Integer>[] adj; // 인접 리스트
	static int[] colors; // 정점의 색 (0: 미방문, 1: 빨강, -1: 파랑)
	static boolean isBipartite; // 이분 그래프 여부 저장

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 테스트 케이스 개수 K
		int K = Integer.parseInt(br.readLine());

		while (K-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int V = Integer.parseInt(st.nextToken()); // 정점 개수
			int E = Integer.parseInt(st.nextToken()); // 간선 개수

			// 그래프 초기화 (1번 정점부터 사용)
			adj = new ArrayList[V + 1];
			for (int i = 1; i <= V; i++) {
				adj[i] = new ArrayList<>();
			}

			colors = new int[V + 1]; // 0으로 자동 초기화
			isBipartite = true; // 기본적으로 true로 가정

			// 간선 입력
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				adj[u].add(v);
				adj[v].add(u);
			}

			for (int i = 1; i <= V; i++) {
				if (isBipartite && colors[i] == 0) {
					bfs(i);
				}
			}

			sb.append(isBipartite ? "YES" : "NO").append("\n");
		}
		System.out.println(sb);
	}

	// BFS 탐색
	static void bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		q.offer(start);
		colors[start] = 1; // 시작 정점을 빨강(1)으로 칠함

		while (!q.isEmpty()) {
			int curr = q.poll();

			for (int next : adj[curr]) {
				// 1. 아직 방문하지 않은 인접 정점인 경우
				if (colors[next] == 0) {
					colors[next] = -colors[curr]; // 현재와 반대 색(-1 * 1 = -1)으로 칠함
					q.offer(next);
				}
				// 2. 이미 방문했는데, 현재 정점과 색이 같다면 모순 발생
				else if (colors[next] == colors[curr]) {
					isBipartite = false;
					return;
				}
			}
		}
	}
}