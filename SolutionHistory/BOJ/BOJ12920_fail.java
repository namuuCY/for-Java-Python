import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/12920)
    // 12920 평범한 배낭 2

    // 배낭문제 응용
    // M 이 무게
    // DP[M] = 만족도
    // DP[M] = 현재만족도 + 이전 DP[M-V] 에서 count를 썼다면
    // 1. 왜 틀렸는가? (Why)
    // 작성하신 코드의 핵심 로직은 이것입니다.
    // "앞에서부터 채워나가면서(j: v -> M), 현재 물건을 쓴 횟수(count)를 기록하고, $K$개를 넘지 않으면 갱신한다."
    // 이 방식은 **동전 교환 문제(Change Making)**나 **가능성 판별(Boolean)**에서는 통하지만,
    // **가치 최대화(Knapsack)**에서는 치명적인 논리적 오류가 있습니다.
    //
    // 오류의 핵심: "Greedy한 갱신이 최적해를 보장하지 않음
    // "DP 배열(DP[j])에는 항상 **'지금까지의 최적값'**이 들어있습니다.
    // 작성하신 코드에서 DP[j]를 갱신할 때 count[j] = count[j-v] + 1로 기록하는데,
    // 문제는 DP[j-v]가 '현재 물건을 포함해서 만든 값'인지, '다른 물건들로만 만든 값'인지 명확히 구분할 수 없다는 점입니다.

    // 시나리오:
    // 이전 단계에서 아주 효율 좋은 물건들로 DP[10]을 100으로 만들어 뒀습니다. (현재 물건은 안 씀)
    // 이번 물건(무게 2, 가치 10, 개수제한 1개)의 루프를 돕니다.
    // j=12일 때, DP[10] + 10 vs DP[12]를 비교합니다.
    // 갱신된다면 count[12] = 1이 됩니다. (여기까진 OK)
    // 문제 발생: j=14일 때, DP[12] + 10을 봅니다. count[12]가 1이니까 count[14]는 2가 되겠죠?
    // 하지만 DP[12]는 이미 **'다른 물건들의 최적값 + 현재 물건 1개'**가 섞여 있는 상태입니다.
    // 이런 식으로 섞이다 보면, "가치가 높지만 무게 효율이 떨어지는 이전 상태" 위에 현재 물건을 덧붙이는 경우를 놓치거나,
    // count 배열이 꼬여서 실제로는 $K$개를 넘게 사용하거나 덜 사용하는 최적해가 덮어씌워집니다.

    static int N, M;

    //    static int[][] stuff;
    static int[] DP;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] NM = br.readLine().strip().split(" ");
        N = Integer.parseInt(NM[0]);
        M = Integer.parseInt(NM[1]);

        DP = new int[M + 1];
//        stuff = new int[N][3];

        for (int i = 0; i < N; i++) {
            String[] vck = br.readLine().strip().split(" ");
            // V : 물건 무게 , C: 만족도 , K : 물건 개수
            int v = Integer.parseInt(vck[0]);
            int c = Integer.parseInt(vck[1]);
            int k = Integer.parseInt(vck[2]);

            int[] count= new int[M + 1];

            for (int j = v ; j<= M; j++) {
                if ((DP[j] <= DP[j - v] + c) && (count[j - v] < k)) {
                    DP[j] = DP[j - v] + c;
                    count[j] = count[j - v] + 1;
                    continue;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i <=M ; i++) {

            ans = Math.max(ans, DP[i]);
        }
        System.out.println(ans);
    }
}
