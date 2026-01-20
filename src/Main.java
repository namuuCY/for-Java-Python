import java.io.*;
import java.util.*;

public class Main {
    private static int ceilDiv(int a, int b) {
        return (a + b - 1) / b;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // 그룹 카운트
        int g12 = 0;      // 1~2 전체
        int g34F = 0;     // 3~4 여
        int g34M = 0;     // 3~4 남
        int g56F = 0;     // 5~6 여
        int g56M = 0;     // 5~6 남

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int S = Integer.parseInt(st.nextToken()); // 0:여, 1:남
            int Y = Integer.parseInt(st.nextToken()); // 1~6

            if (Y == 1 || Y == 2) {
                g12++;
            } else if (Y == 3 || Y == 4) {
                if (S == 0) g34F++;
                else g34M++;
            } else { // Y == 5 || Y == 6
                if (S == 0) g56F++;
                else g56M++;
            }
        }

        int ans = 0;
        ans += ceilDiv(g12, K);
        ans += ceilDiv(g34F, K);
        ans += ceilDiv(g34M, K);
        ans += ceilDiv(g56F, K);
        ans += ceilDiv(g56M, K);

        System.out.println(ans);
    }
}
