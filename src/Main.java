import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/submit/24417

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int mod = 1_000_000_007;

        // 코드 2 실행 횟수: n - 2
        // n의 최댓값이 2억이므로 10억 7보다 작아 별도의 모듈러 연산이 필요 없습니다.
        int code2Count = n - 2;

        // 코드 1 실행 횟수: n번째 피보나치 수 구하기 (O(N) 알고리즘, O(1) 공간 복잡도)
        int prev2 = 1; // f[1]
        int prev1 = 1; // f[2]
        int current = 1;

        for (int i = 3; i <= n; i++) {
            current = (prev1 + prev2) % mod;
            prev2 = prev1;
            prev1 = current;
        }

        System.out.println(current + " " + code2Count);
    }
}