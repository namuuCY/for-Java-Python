import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/11402)
    // 11402 이항계수 4

    // 뤼카의 정리
    static int p;
    static int[][] pascal;

    // 소수 m에 대해서 1,2,3,4,... 모듈러 값은 1,2,3,4,,,... m-1, 0 으로 반복됨

    static int l(long n, long k) {
        if (k == 0) return 1;
        if (n < k) return 0;

        return (l(n/p, k/p) * pascal[(int) (n % p)][(int) (k%p)]) % p;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long N = Long.parseLong(st.nextToken());
        long K = Long.parseLong(st.nextToken());
        p = Integer.parseInt(st.nextToken());

        if (N == 1 || K == 0) {
            System.out.println(1);
            return;
        }

        if (K == 1) {
            System.out.println(N % p);
            return;
        }

        pascal = new int[2001][2001];
        for (int i = 0; i <= 2000; i++) {
            pascal[i][0] = 1;
            for (int j = 1; j <= i ; j++) {
                pascal[i][j] = (pascal[i-1][j] + pascal[i-1][j-1]) % p;
            }
        }

        System.out.println(l(N, K));



    }

}