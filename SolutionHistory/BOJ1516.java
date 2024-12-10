import java.util.*;
import java.io.*;

public class Main {
    // N: 500 많으면 relation에만 25만개 -> 시간복잡도 O(N) or O(NlogN)
    // 그래프는 트리, 계층이 존재 -> 위상정렬 생각
    // indeg 0일때 해당 값을 계산.
    // 각각의 줄 파싱할때 두번째 값이 -1이 아닌 경우
    // 그리고 매번 연결된 노드마다 값 비교해서 최댓값 넣어야한다.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        LinkedList<Integer> Q = new LinkedList<>();


        int N = Integer.parseInt(br.readLine());

        int[] buildTime = new int[N + 1];
        List<List<Integer>> pre = new ArrayList<>();
        int[] indeg = new int[N + 1];
        int[] ans = new int[N + 1];

        for (int i = 0; i < N + 1; i++) {
            pre.add(new ArrayList<>());
            buildTime[i] = 0;
            indeg[i] = 0;
            ans[i] = 0;
        }

        for (int i = 1; i < N + 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            buildTime[i] = Integer.parseInt(st.nextToken());
            while (st.hasMoreTokens()) {
                int cur = Integer.parseInt(st.nextToken());
                if (cur == -1) {
                    break;
                }
                indeg[i]++;
                pre.get(cur).add(i);
            }
        }
        for (int i = 1 ; i <= N ; i++) {
            if (indeg[i] == 0) {
                Q.addLast(i);
                ans[i] += buildTime[i];
            }
        }

        while (!Q.isEmpty()) {
            int cur = Q.removeFirst();
            for (int next : pre.get(cur)) {
                indeg[next]--;
                ans[next] = Math.max(ans[next], ans[cur] + buildTime[next]);
                if (indeg[next] == 0) {
                    Q.addLast(next);
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            System.out.println(ans[i]);
        }
    }
}