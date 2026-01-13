import java.io.*;
import java.util.*;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {
	// https://www.acmicpc.net/problem/2610
	// 회의 준비

	// 1 4
	// 2 3
	// disjoint Set으로 구성하고, <- 순환 있어서 안된다...? 아님 중복이면 안하면 된다
	// set 별로 플로이드 돌리고
	// 최단거리 구하면 되지 않나?
	// 최단거리는 상태를 모르지 않나??
	// 대표 중, 가장 적은 사람을 거치는 경로로 의견을 전달
	// 맞네 플로이드다

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int entryNumber = Integer.parseInt(br.readLine());
		int edgeCount = Integer.parseInt(br.readLine());

		int[][] edges = inputProcess(br, entryNumber, edgeCount);

		List<List<Integer>> groups = groupByDfs(edges, entryNumber);
		StringBuilder sb = new StringBuilder();
		sb.append(groups.size());
		groups.stream()
				.map((group) -> leaderElectionByFloyd(group, edges))
				.sorted()
				.forEach(leader -> {
					sb.append("\n" + leader);
				});

		System.out.println(sb);
	}

	private static int[][] inputProcess(BufferedReader br, int entryNumber, int edgeCount) throws IOException {
		int[][] edges = new int[entryNumber][entryNumber];
		StringTokenizer st;
		while (edgeCount -- > 0) {
			st = new StringTokenizer(br.readLine());
			int startNode = Integer.parseInt(st.nextToken()) - 1;
			int endNode = Integer.parseInt(st.nextToken()) - 1;
			edges[startNode][endNode] = 1;
			edges[endNode][startNode] = 1;
		}
		return edges;
	}

	private static List<List<Integer>> groupByDfs(int[][] edges, int entryNumber) {
		boolean[] visited = new boolean[entryNumber];

		List<List<Integer>> groups = new ArrayList<>();

		for (int i = 0; i < entryNumber ; i++) {
			if (visited[i]) continue;
			List<Integer> currentGroup = dfs(edges, i, entryNumber);
			for (int idx : currentGroup) {
				visited[idx] = true;
			}
			groups.add(currentGroup);
		}

		return groups;
	}

	private static List<Integer> dfs(int[][] edges, int currentIdx, int entryNumber) {
		List<Integer> entries = new ArrayList<>();
		boolean[] visited = new boolean[entryNumber];
		visited[currentIdx] = true;
		entries.add(currentIdx);
		Deque<Integer> Q = new ArrayDeque<>();
		Q.add(currentIdx);

		while (!Q.isEmpty()) {
			int currentNode = Q.pop();
			for (int nextNode = 0 ; nextNode < entryNumber ; nextNode++) {
				if (edges[currentNode][nextNode] == 0) continue;
				if (visited[nextNode]) continue;

				visited[nextNode] = true;
				if (!entries.contains(nextNode)) {
					entries.add(nextNode);
				}
				Q.add(nextNode);
			}
		}

		return entries;
	}

	// 대표 선발하는건 1명, 2명일때는 엣지케이스 두자.

	private static int leaderElectionByFloyd(List<Integer> group, int[][] edges) {
		int groupSize = group.size();
		if (groupSize == 1 || groupSize == 2) {
			return group.get(0) + 1;
		}
		// 플로이드 세팅
		int[][] dist = new int[groupSize][groupSize];
		for (int i = 0 ; i < groupSize ; i++ ) {
			Arrays.fill(dist[i], 0x3f3f3f3f);
			dist[i][i] = 0;
			for (int j = 0 ; j < groupSize; j++) {
				int edge = edges[group.get(i)][group.get(j)];
				if (edge == 0) continue;
				dist[i][j] = 1;
			}
		}
		// 플로이드 돌림
		for (int passingBy = 0 ; passingBy < groupSize; passingBy++ ) {
			for (int startNode = 0; startNode < groupSize; startNode ++) {
				for (int endNode = 0; endNode < groupSize; endNode ++) {
					if (dist[startNode][endNode] <= dist[startNode][passingBy] + dist[passingBy][endNode]) continue;
					dist[startNode][endNode] = dist[startNode][passingBy] + dist[passingBy][endNode];
				}
			}
		}

		// 결과 종합
		int count = Integer.MAX_VALUE;
		int leader = -1;
		for (int i = 0 ; i < groupSize; i++) {
			int maxDistForNodeI = 0;
			for (int j = 0 ; j < groupSize; j++) {
				if (i == j) continue;
				maxDistForNodeI = Math.max(maxDistForNodeI, dist[i][j]);
			}
			if (count > maxDistForNodeI) {
				count = maxDistForNodeI;
				leader = group.get(i);
			}
		}

		return leader + 1;
	}
}