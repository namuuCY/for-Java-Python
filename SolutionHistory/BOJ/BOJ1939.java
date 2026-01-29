import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

public class Main {
	// https://www.acmicpc.net/problem/1939
	//


	static class Node {
		int to;
		int weight;

		public Node(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
	}

	static int N, M;
	static ArrayList<Node>[] adj;
	static boolean[] visited;
	static int startFactory, endFactory;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		adj = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			adj[i] = new ArrayList<>();
		}

		int maxWeight = 0;

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());

			adj[u].add(new Node(v, w));
			adj[v].add(new Node(u, w));

			maxWeight = Math.max(maxWeight, w);
		}

		st = new StringTokenizer(br.readLine());
		startFactory = Integer.parseInt(st.nextToken());
		endFactory = Integer.parseInt(st.nextToken());

		// 이분 탐색 진행
		long answer = 0;
		long low = 1;
		long high = maxWeight;

		while (low <= high) {
			long mid = (low + high) / 2;

			if (canGo(mid)) {
				answer = mid; // 가능하다면 답을 갱신하고
				low = mid + 1; // 더 큰 중량을 시도해봄
			} else {
				high = mid - 1; // 불가능하다면 중량을 줄임
			}
		}

		System.out.println(answer);
	}

	static boolean canGo(long limitWeight) {
		Queue<Integer> q = new LinkedList<>();
		visited = new boolean[N + 1];

		q.offer(startFactory);
		visited[startFactory] = true;

		while (!q.isEmpty()) {
			int current = q.poll();

			if (current == endFactory) {
				return true;
			}

			for (Node next : adj[current]) {
				if (!visited[next.to] && next.weight >= limitWeight) {
					visited[next.to] = true;
					q.offer(next.to);
				}
			}
		}

		return false;
	}
}