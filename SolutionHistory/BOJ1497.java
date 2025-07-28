import java.io.*;
import java.util.*;

public class Main {

    // BOJ 1497 (https://www.acmicpc.net/problem/1497)
    // N : 10개 -> 경우의 수 2^10 - 1
    // 연산 한번에 최대 10개 => 시간복잡도 O(N)
    static int N; // 기타 개수
    static int M; // 음악 개수
    static long[] numArr;
    static int maxSong;
    static int ans;

    static long parseAndConvert(String ynStr) {
        long count = 0L;
        for (int i = 0; i < M ; i++) {
            char yOrN = ynStr.charAt(i);
            if (yOrN == 'N') continue;
            count += (1L << i);
        }
//        System.out.println(ynStr + "에서 값 : " + count);
        return count;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        numArr = new long[N];
        maxSong = Integer.MIN_VALUE;
        ans = Integer.MAX_VALUE;

        for (int i = 0 ; i < N ; i++) {
            StringTokenizer st1 = new StringTokenizer(br.readLine());
            st1.nextToken();
            numArr[i] = parseAndConvert(st1.nextToken());
        }

        for (int j = 1 ; j < (1 << N) ; j++) {
            long state = 0L;
            int currentMaxSong = 0;
            int currentGuiter = 0;

            // j를 비트마스킹으로 만들고
            for (int k = 0 ; k < N ; k++) {
                if ( (j & (1 << k)) == 0) continue;
                state |= numArr[k];
                currentGuiter += 1;

            }
            // 곡 개수 최대 계산
            for (int a = 0 ; a < M ; a++) {
                if ( (state & (1L << a)) == 0) continue;
                currentMaxSong += 1;

            }

            if (currentMaxSong == 0) {
                continue;
            }

            if (maxSong < currentMaxSong) {
                maxSong = Math.max(maxSong, currentMaxSong);
                ans = currentGuiter;
            } else if (maxSong == currentMaxSong) {
                ans = Math.min(ans, currentGuiter);
            }


        }
        int ansOut = (ans != Integer.MAX_VALUE) ? ans : -1;
        System.out.println(ansOut);
    }
}
