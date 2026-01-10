import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/5719
	// 거의 최단 경로

	static int INF = Integer.MAX_VALUE;

	static class Node implements Comparable<Node> {
		int nodeIdx;
		int cost;

		Node(int nodeIdx, int cost) {
			this.nodeIdx = nodeIdx;
			this.cost = cost;
		}

		public int compareTo(Node that) {
			return this.cost - that.cost;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		while (true) {
			st = new StringTokenizer(br.readLine());
			int vertexSize = Integer.parseInt(st.nextToken());
			int edgeSize = Integer.parseInt(st.nextToken());
			if (isTerminateCondition(vertexSize, edgeSize)) break;

			st = new StringTokenizer(br.readLine());
			int startNode = Integer.parseInt(st.nextToken());
			int endNode = Integer.parseInt(st.nextToken());

			List<Node>[] nodes = initGraph( br, vertexSize, edgeSize);

			boolean[][] isRemoved = runDijkstraAndRemoveLeastPaths(vertexSize, startNode, endNode, nodes);

			int ans = runDijkstraWithRemoveLeastPaths(vertexSize, startNode, endNode, nodes, isRemoved);
			sb.append(
					(ans == INF) ? -1 : ans
			);
			sb.append("\n");
		}

		System.out.println(sb);
	}

	private static boolean isTerminateCondition(int input1, int input2) {
		return input1 == 0 && input2 == 0;
	}

	private static List<Node>[] initGraph(BufferedReader br, int vertexSize, int edgeSize) throws IOException {
		List<Node>[] graph = new ArrayList[vertexSize];

		for (int i = 0 ; i < vertexSize; i++) {
			graph[i] = new ArrayList<>();
		}
		StringTokenizer st;
		while (edgeSize -- > 0) {
			st = new StringTokenizer(br.readLine());
			int startNode = Integer.parseInt(st.nextToken());
			Node edge = new Node(
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken())
			);
			graph[startNode].add(edge);
		}

		return graph;
	}

	private static boolean[][] runDijkstraAndRemoveLeastPaths(
			int vertexSize,
			int startNode,
			int endNode,
			List<Node>[] graph) {
		// 다익스트라 수행하면서 최단경로면 마킹해야함.
		int[] distances = new int[vertexSize];
		Arrays.fill(distances, INF);
		PriorityQueue<Node> pq = new PriorityQueue<>();
		distances[startNode] = 0;

		List<Integer>[] forTrace = new ArrayList[vertexSize];
		for (int i = 0 ; i < vertexSize ; i++) {
			forTrace[i] = new ArrayList<>();
		}
		pq.add(new Node(startNode, 0));

		while (!pq.isEmpty()) {
			Node currentNode = pq.poll();
			int currentNodeIdx = currentNode.nodeIdx;

			if (currentNode.cost > distances[currentNodeIdx]) continue;

			for (Node nextNode : graph[currentNodeIdx]) {
				int nextDistance = distances[nextNode.nodeIdx];
				int newDistance = distances[currentNodeIdx] + nextNode.cost;

				if (nextDistance > newDistance) {
					forTrace[nextNode.nodeIdx].clear();
					forTrace[nextNode.nodeIdx].add(currentNodeIdx);
					distances[nextNode.nodeIdx] = newDistance;
					pq.add(new Node(nextNode.nodeIdx, newDistance));
				} else if (nextDistance == newDistance) {
					forTrace[nextNode.nodeIdx].add(currentNodeIdx);
				}
			}
		}

		return removeLeastPathsByBFS(forTrace, endNode, vertexSize);
	}

	private static boolean[][] removeLeastPathsByBFS(List<Integer>[] forTrace, int endNode, int vertexSize) {
		Queue<Integer> q = new ArrayDeque<>();
		boolean[] visited = new boolean[vertexSize];
		boolean[][] isRemoved = new boolean[vertexSize][vertexSize];

		q.add(endNode);
		visited[endNode] = true;

		while (!q.isEmpty()) {
			int childNode = q.poll();

			for (int parentNode : forTrace[childNode]) {

				isRemoved[parentNode][childNode] = true;
				if (!visited[parentNode]) {
					visited[parentNode] = true;
					q.add(parentNode);
				}

			}
		}

		return isRemoved;
	}

	// 아래는 기존에 잘못된 로직
//    while (!q.isEmpty()) {
//        int currentNode = q.poll();
//
//        for (int nextNode : forTrace[currentNode]) {
//            if (visited[nextNode]) continue;
//            isRemoved[nextNode][currentNode] = true;
//            q.add(nextNode);
//        }
//    }

	private static int runDijkstraWithRemoveLeastPaths(
			int vertexSize,
			int startNode,
			int endNode,
			List<Node>[] graph,
			boolean[][] isRemoved
	) {

		int[] distances = new int[vertexSize];
		Arrays.fill(distances, INF);
		PriorityQueue<Node> pq = new PriorityQueue<>();
		distances[startNode] = 0;

		pq.add(new Node(startNode, 0));

		while (!pq.isEmpty()) {
			Node currentNode = pq.poll();
			int currentNodeIdx = currentNode.nodeIdx;

			if (currentNode.cost > distances[currentNodeIdx]) continue;

			for (Node nextNode : graph[currentNodeIdx]) {
				if (isRemoved[currentNodeIdx][nextNode.nodeIdx]) continue;

				int nextDistance = distances[nextNode.nodeIdx];
				int newDistance = distances[currentNodeIdx] + nextNode.cost;

				if (nextDistance > newDistance) {
					distances[nextNode.nodeIdx] = newDistance;
					pq.add(new Node(nextNode.nodeIdx, newDistance));
				}
			}
		}

		return distances[endNode];
	}


}