import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/2086)
    // BOJ 2086 피보나치 수의 합

    // 힌트가 1 ≤ a ≤ b ≤ 9,000,000,000,000,000,000 라는데
    // 이거 memoization으로 하지 말라는건가?
    // 행렬로 풀라는거네

    // 모듈러 계산시  음수 나오는 것 항상 생각하기





    static long D_NUM = 1_000_000_000;
    static long[][] basic = {{1,1}, {1,0}};



    static long[][] mul(long[][] a, long[][] b) {
        return new long[][]{
                {
                        (a[0][0]*b[0][0] + a[0][1]*b[1][0]) % D_NUM,
                        (a[0][0]*b[0][1] + a[0][1]*b[1][1]) % D_NUM
                },
                {
                        (a[1][0]*b[0][0] + a[1][1]*b[1][0]) % D_NUM,
                        (a[1][0]*b[0][1] + a[1][1]*b[1][1]) % D_NUM
                }
        };
    }

    // 인덱스가 4이상이면 성립할듯?
    static long[][] pow(long[][] a, long pow) {
        List<Integer> q = new ArrayList<>();
        List<long[][]> m = new ArrayList<>();
        // 2^0, 2^1, ... 매트릭스 저장
        long temp = pow;

        while (temp > 0) {
            int rest = (int) temp % 2;
            q.add(rest);
            temp /= 2;
        }

        long[][] ans = {{1, 0}, {0, 1}};

        if (pow == 1) {
            return a;
        }
        m.add(a);
        // 가장 마지막 수 계산하면, 그 이후는 메모이제이션
        for (int i=1; i < q.size(); i++) {
            m.add(mul(m.get(i - 1), m.get(i-1)));
        }

        for (int i=0; i < q.size(); i++) {
            if (q.get(i) == 0) continue;
            ans = mul(m.get(i), ans);
        }

        return ans;
    }

    static long fib(long idx) {
        if (idx == 1 || idx == 2) return 1;
        if (idx == 3) return 2;
        long[][] temp = pow(basic, idx - 1);
        return temp[0][0];
    }

    static long fibSum(long idx) {

        if (idx == 0) return 0;
        // 1  1 = 2 - 1
        // 2  1 1 =  2 = 3 -1
        // 3  1 1 2 = 4 = 5-1
        // 4  1 1 2 3 = 7 = 8 - 1
        return fib(idx + 2) - 1;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        long start = Long.parseLong(st.nextToken());

        long end = Long.parseLong(st.nextToken());

        System.out.println((fibSum(end) - fibSum(start - 1) + D_NUM) % D_NUM);

    }
}

