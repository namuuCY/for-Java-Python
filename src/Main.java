import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/33938

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 예외 처리: 0원을 만드는 데 필요한 동전은 항상 0개입니다.
        if (M == 0) {
            System.out.println(0);
            return;
        }

        // 동전이 없는데 0원 아닌 금액을 만들어야 하는 경우
        if (N == 0) {
            System.out.println(-1);
            return;
        }

        int[] p = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            p[i] = Integer.parseInt(st.nextToken());
        }

        int minCount = Integer.MAX_VALUE;

        if (N == 1) {
            // 동전이 1개일 때: p[0] * i = M 인 i 찾기
            for (int i = 0; i <= 2000; i++) {
                if (p[0] * i == M) {
                    minCount = Math.min(minCount, i);
                }
            }
        } else if (N == 2) {
            // 동전이 2개일 때: p[0] * i + p[1] * j = M 인 최소 i + j 찾기
            for (int i = 0; i <= 2000; i++) {
                for (int j = 0; j <= 2000; j++) {
                    if (p[0] * i + p[1] * j == M) {
                        minCount = Math.min(minCount, i + j);
                    }
                }
            }
        }

        // 결과 출력
        if (minCount == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(minCount);
        }
    }
}