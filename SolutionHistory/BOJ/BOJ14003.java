import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/14003)
    // 14003 가장 긴 증가하는 부분 수열 5

    static int N;
    static int[] seq;
    static int[] DP;
    static int[] minVal;


    static int lowerBiSearch(int targetValue) {
        int start = 0;
        int end = N + 1;

        while (start < end) {
            int mid = (start + end ) / 2;

            if (minVal[mid] >= targetValue) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }


    static int[] reconstruct(int maxLength) {
        int[] ans = new int[maxLength];
        int count = maxLength;
        for (int i = N; i >= 0 ; i--) {
            if (count == 0) break;
            if (DP[i] == count) {
                ans[count - 1] = seq[i];
                count --;
            }
        }
        return ans;
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        seq = new int[N + 1];
        DP = new int[N + 1];
        minVal = new int[N + 1];
        Arrays.fill(minVal, Integer.MAX_VALUE);
        minVal[0] = Integer.MIN_VALUE;

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N + 1; i++) {
            if (i == 0) {
                DP[0] = 0;
                continue;
            }

            seq[i] = Integer.parseInt(st.nextToken());
            int length = lowerBiSearch(seq[i]);

            DP[i] = length;
            if (minVal[length] > seq[i]) {
                minVal[length] = seq[i];
            }
        }


        int ans = 0;
        for (int i = 1; i < N + 1; i++) {
            ans = Math.max(ans, DP[i]);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(ans);
        sb.append("\n");

        // 10
        // 0 0 1 0 0 24 0 11 14 -6

        // DP 값은 0 1 1 2 1 1 3 1 3 4 1
        // 가장 높은 값 기준으로 가장 빠른 3 찾고
        // 가장 빠른 2 찾고
        // 가장 빠른 1 찾고
        int[] reconstructed = reconstruct(ans);
        for (int i = 0; i < ans; i++) {
            sb.append(reconstructed[i]).append(" ");
        }

        bw.write(sb.toString());
        bw.flush();
    }
}