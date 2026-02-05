import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/14381
    // 숫자세는 양

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 첫 번째 줄: 테스트 케이스의 개수 T
        int T = Integer.parseInt(br.readLine());

        for (int i = 1; i <= T; i++) {
            int N = Integer.parseInt(br.readLine());

            // 결과 계산 및 출력 형식 맞추기
            String result = solve(N);
            System.out.println("Case #" + i + ": " + result);
        }
    }

    private static String solve(int n) {
        // N이 0이면 영원히 0만 나오므로 불가능
        if (n == 0) return "INSOMNIA";

        boolean[] visited = new boolean[10]; // 0~9 숫자 등장 여부 체크
        int count = 0; // 발견한 서로 다른 숫자의 개수
        int multiplier = 1;
        long currentVal = 0;

        while (true) {
            currentVal = (long) n * multiplier;
            long temp = currentVal;

            // 현재 숫자의 각 자릿수 확인
            while (temp > 0) {
                int digit = (int) (temp % 10);

                // 처음 보는 숫자라면 체크
                if (!visited[digit]) {
                    visited[digit] = true;
                    count++;
                }
                temp /= 10;
            }

            // 0~9 모든 숫자를 다 봤으면 종료
            if (count == 10) {
                return String.valueOf(currentVal);
            }

            multiplier++;
        }
    }
}