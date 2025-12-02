import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/12015)
    // 12015 가장 긴 증가하는 부분 수열

    static int N;
    static int[] seq;
    static int[] DP;
    static int[] min;

    static public int lowerBound(int value) {
        int start = 0;
        int end = N + 1;
        while (start < end) {
            int mid = (start + end) / 2;
            if (min[mid] >= value) {
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
        min = new int[N + 1];
        Arrays.fill(min, Integer.MAX_VALUE);

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 1; i < N + 1; i ++) {
            seq[i] = Integer.parseInt(st.nextToken());
        }

        // 기본 원리 : DP[N] = N번째 DP는 N번째를 마지막으로 가지는 가장 긴 증가 부분 수열의 길이
        // seq[i] 값이 seq[N]보다 작을 때, 각각의 D[i]중에서 최대일때를 선택하는 것

        // D[i] = k 인 seq[i]가 최소가 되는 seq[i]를 저장한다면?
        min[0] = 0;
        for (int i = 1; i < N + 1 ; i ++) {
            int val = seq[i];
            int length = lowerBound(val);
            DP[i] = length;
            if (min[length] > seq[i]) {
                min[length] = seq[i];
            }
        }
        int ans = 0;
        for (int i = 1; i < N + 1; i++) {
            ans = Math.max(ans, DP[i]);
        }
        System.out.println(ans);
    }
}