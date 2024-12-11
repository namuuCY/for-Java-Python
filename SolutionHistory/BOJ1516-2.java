import java.io.*;
import java.util.*;

public class Main {
    // N: 500 많으면 relation에만 25만개 -> 시간복잡도 O(N) or O(NlogN)
    // 그래프는 트리, 계층이 존재 -> 위상정렬 생각
    // indeg 0일때 해당 값을 계산.
    // 각각의 줄 파싱할때 두번째 값이 -1이 아닌 경우
    // 그리고 매번 연결된 노드마다 값 비교해서 최댓값 넣어야한다.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Queue<Integer> Q = new LinkedList<>();

        int N = Integer.parseInt(br.readLine());
        int[] buildTime = new int[N + 1];
        // 이렇게 되어있을경우 0으로 초기화 된다.
        int[] indeg = new int[N + 1];
        List<List<Integer>> adj = new ArrayList<>();
        int[] ans = new int[N + 1];

        for (int i = 0 ; i<= N; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 1 ; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            buildTime[i] = Integer.parseInt(st.nextToken());
            while (st.hasMoreTokens()) {
                int cur = Integer.parseInt(st.nextToken());
                if (cur == -1) break;
                indeg[i]++;
                adj.get(cur).add(i);
            }
        }

        for (int i = 1 ; i <= N ; i++) {
            if (indeg[i] == 0) {
                Q.offer(i);
                ans[i] += buildTime[i];
            }
        }

        while (!Q.isEmpty()) {
            int cur = Q.poll();
            for (int i : adj.get(cur)) {
                indeg[i]--;
                ans[i] = Math.max(ans[i], ans[cur] + buildTime[i]);
                if (indeg[i] == 0) {
                    Q.offer(i);
                }
            }
        }

        for (int i = 1 ; i <= N ; i++) {
            System.out.println(ans[i]);
        }
    }
}