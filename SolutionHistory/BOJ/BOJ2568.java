import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/2568)
    // 2568 전깃줄 2

    // 잘라야 하는 전깃줄
    // 최대한의 가장 긴 증가하는 부분수열을 구하고
    // 전체에서 그걸 빼자

    static int N;
    static int[] seq;
    static int[] originalIdx;
    static int[] dp;
    static int[] lowVal;

    static int lbound(int currentValue) {
        int start = 0;
        int end = N;

        while (start < end) {
            int mid = (start + end) / 2;

            if (lowVal[mid] >= currentValue) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        return start;
    }

    static int[] trackingExceptionIdx(int length) {

        int countO = length;
        int countX = N - length;

        int[] exception = new int[N - length];

        for (int i = N; i >= 1; i--) {
            if (dp[i] == countO) {
                countO --;
                continue;
            } else {
                exception[countX - 1] = i;
                countX --;
            }
        }

        return exception;
    }

    // 8 2 9 1 4 6 7 10


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        seq = new int[N + 1];
        originalIdx = new int[N + 1];
        dp = new int[N + 1];
        lowVal = new int[N + 1];

        Arrays.fill(lowVal, Integer.MAX_VALUE);
        lowVal[0] = 0;


        // 정렬 먼저
        int[][] rawInput = new int[N][2];

        for (int i = 0 ; i < N ; i++) {
            String[] ab = br.readLine().strip().split(" ");
            rawInput[i][0] = Integer.parseInt(ab[0]);
            rawInput[i][1] = Integer.parseInt(ab[1]);
        }

        Arrays.sort(rawInput, Comparator.comparingInt(arr -> arr[0]));

        for (int i = 0; i < N; i ++) {
            originalIdx[i + 1] = rawInput[i][0];
            seq[i + 1] = rawInput[i][1];

            int length = lbound(seq[i + 1]);
            dp[i + 1] = length;

            if (lowVal[length] > seq[i + 1]) {
                lowVal[length] = seq[i + 1];
            }
        }

        int maxLength = 0;

        for (int i = 1; i < N + 1; i ++) {
            maxLength = Math.max(maxLength, dp[i]);
        }

        int[] idx = trackingExceptionIdx(maxLength);

        StringBuilder sb = new StringBuilder();

        sb.append(N - maxLength);
        sb.append("\n");

        for (int i = 0 ; i < N-maxLength; i++) {
            sb.append(originalIdx[idx[i]]);
            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
    }
}