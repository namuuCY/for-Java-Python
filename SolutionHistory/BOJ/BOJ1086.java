import java.io.*;
import java.math.BigInteger;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/1086)
    // 1086 박성원

    // DP[사용한 수의 비트마스킹][현재 mod 나머지 값] -> 새로 추가할 경우에는 뒤에만 붙인다.
    //

    static int n;
    static String[] num;
    // 50자리가 넘어가서 BigInteger를 써야함
//    static long[] num;

    static int[] rest;
    static int k; // 나누는 숫자
    static int[] dRest;
    static int[] numSizeRest; // 숫자의 길이만큼 곱할때 필요한 값

    static long[][] DP; // [bitmask][remainder];

    static long euclid(long a, long b) {
        long r = a % b;
        if (r == 0) {
            return b;
        }
        return euclid(b, r);
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        num = new String[n];
        rest = new int[n];
        numSizeRest = new int[n];
        for (int i=0 ; i< n ; i++ ) {
            num[i] = br.readLine();
        }

        k = Integer.parseInt(br.readLine());

        int size = 0;
        for (int i=0 ; i< n ; i++ ) {
            rest[i] = new BigInteger(num[i]).remainder(BigInteger.valueOf(k)).intValue();

            size += num[i].length();
        }
        // 최대 10 ^ (size - 1) 까지 곱해야하니까

        dRest = new int[size + 2];
        dRest[0] = 1;
        for (int i = 1; i < size + 2; i++) {
            dRest[i] = dRest[i - 1] * 10 % k;
        }

        for (int i=0 ; i< n ; i++ ) {
            int numSize = num[i].length();
            numSizeRest[i] = dRest[numSize];
        }

        DP = new long[(1 << n)][k];
        DP[0][0] = 1;
        // 2개면 11 < 100 -1
        for (int bitmask = 0; bitmask < (1 << n) ; bitmask++) {

            for (int remainder = 0; remainder < k ; remainder++) {
                if (DP[bitmask][remainder] == 0) continue;

                for (int idx = 0; idx < n; idx++) {
                    if ( (bitmask & (1 << idx)) != 0) continue;
                    int nextIdx = bitmask | (1 << idx);
                    // 지금 remainder를 앞으로 땡기고, 추가로 계산
                    int nextRemainder = ((remainder * numSizeRest[idx]) + rest[idx]) % k;

                    DP[nextIdx][nextRemainder] += DP[bitmask][remainder];
                }
            }
        }

        // 분자

        long upper = DP[(1<<n) - 1][0];

        long lower = 1;
        for (int i = 1; i < n + 1; i++) {
            lower *= i;
        }

        // 유클리드 호제법
        long gcd = euclid(upper, lower);
        System.out.println((upper / gcd) + "/" +(lower / gcd));
    }
}
