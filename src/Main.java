import java.io.*;
import java.util.*;

public class Main {

    // https://www.acmicpc.net/problem/31871

    static int N, M;
    static long[][] graph;
    static long maxAns = -1;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        // 정문(0) + 놀이기구(1~N) 이므로 크기를 N+1로 설정
        graph = new long[N + 1][N + 1];

        // 그래프 초기화 (-1은 연결되지 않음을 의미)
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                graph[i][j] = -1;
            }
        }

        // 간선 정보 입력
        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            long d = Long.parseLong(st.nextToken());

            // 동일한 경로의 간선이 여러 개 들어올 수 있으므로 최대값만 갱신
            graph[u][v] = Math.max(graph[u][v], d);
        }

        // DFS 탐색 시작 (현재 위치 0, 방문한 놀이기구 수 0, 방문 상태 0, 누적 거리 0)
        dfs(0, 0, 0, 0L);

        // 결과 출력
        System.out.println(maxAns);
    }


    static void dfs(int curr, int count, int visited, long dist) {
        // 모든 놀이기구를 한 번씩 다 방문한 경우
        if (count == N) {
            // 정문(0)으로 돌아가는 길이 존재한다면 최장 시간 갱신
            if (graph[curr][0] != -1) {
                maxAns = Math.max(maxAns, dist + graph[curr][0]);
            }
            return;
        }

        // 1번부터 N번 놀이기구까지 탐색 (0번은 마지막에만 가야 하므로 1부터 시작)
        for (int nxt = 1; nxt <= N; nxt++) {
            // 다음 놀이기구를 아직 방문하지 않았고, 가는 길이 존재하는 경우
            if ((visited & (1 << nxt)) == 0 && graph[curr][nxt] != -1) {
                // 방문 표시 후 재귀 호출
                dfs(nxt, count + 1, visited | (1 << nxt), dist + graph[curr][nxt]);
            }
        }
    }
}