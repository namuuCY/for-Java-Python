import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/12920)
    // 12920 평범한 배낭 2

    // 배낭문제 응용
    // M 이 무게
    // DP[M] = 만족도
    // DP[M] = 현재만족도 + 이전 DP[M-V] 에서 count를 썼다면

    // count가 적용이 안된다. 왜냐하면 이전에 최적화된 DP가 내가 값을 사용한 DP라는 보장이 없어
    // 그때 됐던 이유는 안쓰려면 안쓰는게 맞았으니까. 지금은 안되는 이유 ? 쓰는게 맞는 지 아닌지 모름
    //

    // 이럴 경우, 갯수만큼 2진법으로 표시해
    // 9 -> 1 2 4 2 ->
    // 최대 1만개 있다고 했을 때, 8192  = 2^13 제곱 -> 13개 + 나머지 14개 여유롭게 20개라 치자
    // 총 N 이 100개니까 List로 해도 충분


    static int N, M;

    static int[] DP;
    static List<int[]> stuff;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] NM = br.readLine().strip().split(" ");
        N = Integer.parseInt(NM[0]);
        M = Integer.parseInt(NM[1]);

        DP = new int[M + 1];

        stuff = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String[] vck = br.readLine().strip().split(" ");
            // V : 물건 무게 , C: 만족도 , K : 물건 개수
            int v = Integer.parseInt(vck[0]);
            int c = Integer.parseInt(vck[1]);
            int k = Integer.parseInt(vck[2]);

            int temp = 1;
            while (k > 0) {
                // 무게 : v * temp / 만족도 c * temp
                int count = Math.min(temp, k);
                stuff.add(new int[]{v*count, c*count});
                k -= count;
                temp *= 2;
            }
        }

        // M에서 부터 0-1 배낭 문제

        for (int[] weightHappy : stuff) {
            int weight = weightHappy[0];
            int value = weightHappy[1];

            for (int i = M; i >= weight ; i--) {
                DP[i] = Math.max(DP[i - weight] + value , DP[i]);
            }
        }

        int ans = 0;
        for (int i = 0; i <=M ; i++) {
            ans = Math.max(ans, DP[i]);
        }
        System.out.println(ans);
    }
}
