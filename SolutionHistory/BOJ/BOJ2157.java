import java.io.*;
import java.util.StringTokenizer;

public class Main {
	// https://www.acmicpc.net/problem/2157
	// 2157 여행

	// 먹게되는 기내식 점수의 총합이 최대로
	// 일반적인 다익스트라, 플로이드 -> 이동거리 "최소"를 기록
	// M개 이하의 도시 지나야 함.

	// DP[총 다닌 도시][마지막에 경유한 도시]
	// 1 1 에서 start
	// 1 2
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
		// 입력의 경우, 더 큰 값이 있으면 그것으로 갱신하는거 필요
		StringTokenizer st = new StringTokenizer(br.readLine().strip());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		int[][] graph = parseInput(N, K, br);
		int[][] DP = new int[M + 1][N + 1];

		for (int cnt = 1; cnt < M; cnt++) {
			for (int i = 1; i <= N; i++) {
				if (cnt == 1 && i != 1) continue;
				if (cnt != 1 && DP[cnt][i] == 0) continue;

				for (int j = i + 1; j <= N; j++) {
					if (graph[i][j] > 0) {
						DP[cnt + 1][j] = Math.max(DP[cnt + 1][j], DP[cnt][i] + graph[i][j]);
					}
				}
			}
		}

		int maxScore = 0;
		for (int i = 2; i <= M; i++) {
			maxScore = Math.max(maxScore, DP[i][N]);
		}

		System.out.println(maxScore);
	}

	private static int[][] parseInput(int vertexSize, int queries, BufferedReader br) throws IOException {
		StringTokenizer st;
		int[][] graph = new int[vertexSize + 1][vertexSize + 1];
		while (queries -- > 0) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int score = Integer.parseInt(st.nextToken());

			if (start >= end) continue;
			graph[start][end] = Math.max(graph[start][end], score);
		}

		return graph;
	}
}