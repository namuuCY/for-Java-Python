import java.io.*;
import java.util.*;

public class Main {

    // https://www.acmicpc.net/problem/9079

    // 8가지 뒤집기 연산에 대한 비트마스크 미리 계산
    // 0~8번 인덱스 구조:
    // 0 1 2
    // 3 4 5
    // 6 7 8
    static final int[] MASKS = {
            7,      // 1행 (0, 1, 2) -> 1 + 2 + 4 = 7
            56,     // 2행 (3, 4, 5) -> 8 + 16 + 32 = 56
            448,    // 3행 (6, 7, 8) -> 64 + 128 + 256 = 448
            73,     // 1열 (0, 3, 6) -> 1 + 8 + 64 = 73
            146,    // 2열 (1, 4, 7) -> 2 + 16 + 128 = 146
            292,    // 3열 (2, 5, 8) -> 4 + 32 + 256 = 292
            273,    // 우하향 대각선 (0, 4, 8) -> 1 + 16 + 256 = 273
            84      // 좌하향 대각선 (2, 4, 6) -> 4 + 16 + 64 = 84
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer("");

        while (T-- > 0) {
            int startState = 0;

            // 9개의 동전 상태 입력 받기 (줄바꿈 및 공백에 유연하게 대응)
            for (int i = 0; i < 9; i++) {
                while (!st.hasMoreTokens()) {
                    st = new StringTokenizer(br.readLine());
                }
                String coin = st.nextToken();
                if (coin.equals("H")) {
                    startState |= (1 << i); // H일 경우 해당 자리의 비트를 1로 켬
                }
            }

            sb.append(bfs(startState)).append("\n");
        }

        System.out.print(sb);
    }

    static int bfs(int start) {
        // 이미 모든 면이 같은 경우 (모두 T=0 이거나 모두 H=511)
        if (start == 0 || start == 511) {
            return 0;
        }

        Queue<Integer> queue = new LinkedList<>();
        int[] dist = new int[512];
        Arrays.fill(dist, -1); // 방문 배열 초기화 (-1은 미방문)

        queue.offer(start);
        dist[start] = 0;

        while (!queue.isEmpty()) {
            int curr = queue.poll();

            // 8가지 뒤집기 연산 수행
            for (int mask : MASKS) {
                int next = curr ^ mask; // XOR 연산으로 뒤집기

                // 정답에 도달한 경우
                if (next == 0 || next == 511) {
                    return dist[curr] + 1;
                }

                // 아직 방문하지 않은 상태라면 큐에 추가
                if (dist[next] == -1) {
                    dist[next] = dist[curr] + 1;
                    queue.offer(next);
                }
            }
        }

        // 큐가 빌 때까지 목표에 도달하지 못했다면 불가능한 경우
        return -1;
    }
}