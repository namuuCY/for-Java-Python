import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/17451

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        long[] v = new long[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            v[i] = Long.parseLong(st.nextToken());
        }

        // 마지막 행성으로 가는 속도부터 역추적 시작
        long currentV = v[n - 1];

        // n-2번째 행성부터 0번째(지구)까지 거꾸로 확인
        for (int i = n - 2; i >= 0; i--) {
            if (currentV <= v[i]) {
                // 현재 속도가 다음 필요한 속도 v[i]보다 작거나 같으면
                // 그냥 v[i]로 맞추는 것이 이득 (속도는 줄일 수만 있으므로)
                currentV = v[i];
            } else {
                // currentV가 v[i]보다 크다면, v[i]의 배수 중
                // currentV 이상인 가장 작은 값을 찾아야 함
                if (currentV % v[i] != 0) {
                    // 배수가 아니라면 올림 처리
                    // (currentV / v[i] + 1) * v[i] 와 동일함
                    currentV = ((currentV / v[i]) + 1) * v[i];
                }
                // 이미 배수라면 currentV를 그대로 유지 (속도를 높일 필요 없음)
            }
        }

        System.out.println(currentV);
    }
}