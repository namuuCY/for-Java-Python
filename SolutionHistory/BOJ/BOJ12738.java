import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/12738)
    // 12738 가장 긴 증가하는 부분 수열 3

    static int N;
    static int[] seq;
    static int[] DP;
    static int[] minVal;

    static int lowerBoundBiSearch(int currentValue) {
        int start = 0;
        int end = N + 1;

        while (start < end) {
            int mid = (start + end) / 2;
            if (minVal[mid] >= currentValue) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        return start;
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        seq = new int[N + 1];
        DP = new int[N + 1];
        minVal = new int[N + 1];

        Arrays.fill(minVal, Integer.MAX_VALUE);
        minVal[0] = Integer.MIN_VALUE;

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N + 1; i ++) {
            if (i == 0) continue;
            seq[i] = Integer.parseInt(st.nextToken());
            int length = lowerBoundBiSearch(seq[i]);
            DP[i] = length;
            if (minVal[length] > seq[i]) {
                minVal[length] = seq[i];
            }
        }

        int ans = 0;

        for (int i = 1; i < N + 1; i++) {
            ans = Math.max(ans, DP[i]);
        }
        System.out.println(ans);
    }
}