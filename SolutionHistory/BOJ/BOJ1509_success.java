import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/1509)
    // BOJ 1509 팰린드롬 분할
    // 문자열 최대 길이 N <= 2500


    static int N; // 문자열 길이
    static char[] origin;
    static boolean[][] P;
    static int[] DP;

    static boolean checkP(int start, int end) {
        if (start == end) return true;

        if (end - start == 1) {
            return origin[start] == origin[end];
        }

        int mid = (start + end) / 2 + 1;

        boolean ans = true;
        for (int i = start ; i < mid ; i++) {
            ans = ans && (origin[i] == origin [end + start - i]);
        }

        return ans;
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        origin = br.readLine().strip().toCharArray();
        N = origin.length;
        P = new boolean[N][N];

        DP = new int[N + 1];


        // 팰린드롬 확인
        for (int i = 0; i < N ; i ++) {
            for (int j = i; j<N ; j++) {
                P[i][j] = checkP(i, j);
            }
        }

        DP[0] = 0; // N번째 글자를 위해서 남겨둠
        DP[1] = 1; // 맨처음글자는 무조건

        // asdfff
        // asdff f 4 + 1
        // asdf ff 4 + 1
        // asd fff 3 + 1
        // ...


        // 2짜리 -> i) 1일때, 0 일때
        // asdf 라면 원래 글자

        for (int i = 2; i < N + 1; i ++) {
            int ans = Integer.MAX_VALUE;
            int j = 0;

            while (j < i) {
                boolean isP = P[j][i - 1];
                int additional = isP ? 1 : i - j + 1;
                ans = Math.min(ans, DP[j] + additional);
                j ++;
            }
            DP[i] = ans;
        }

        System.out.println(DP[N]);
    }
}
