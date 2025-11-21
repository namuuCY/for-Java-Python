import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/7453)
    // 7453 합이 0인 네 정수

    // 각 배열 index i, j, k, l라고 했을 때
    // i, j, k 고정일 경우 -> 이것만 시간복잡도 4000 ^ 3 -> 64 * 10억 -> 640억 C++ 기준 640초 불가능
    // i, j 만을 고정 -> 시간 복잡도 1600만 (투포인터)
    // 이 상태에서 k, l의 시간복잡도를 logN 으로 낮춰야함
    // 투포인터는 시가복잡도 N이라 안되고
    // 이분탐색 필요
    // 더한 값을 모두 계산하고, sort, 하고 고른다
    // 배열의 정수 절댓값 최대 2^28 -> 4개 더하면 절댓값 최대 2^30 -> int 범위 내라 이렇게 써도 안전?

    static int[] A;
    static int[] B;
    static int[] C;
    static int[] D;

    static int N;
    static int size;

    static int lower(int[] list, int target) {
        int start = 0;
        int end = size;

        while (start < end) {
            int mid = (start + end) / 2;
            if (list[mid] >= target) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        return start;
    }

    static int upper(int[] list, int target) {
        int start = 0;
        int end = size;

        while (start < end) {
            int mid = (start + end) / 2;
            if (list[mid] > target) {
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

        A = new int[N];
        B = new int[N];
        C = new int[N];
        D = new int[N];

        for (int i = 0 ; i < N; i++) {
            String[] input = br.readLine().strip().split(" ");
            A[i] = Integer.parseInt(input[0]);
            B[i] = Integer.parseInt(input[1]);
            C[i] = Integer.parseInt(input[2]);
            D[i] = Integer.parseInt(input[3]);
        }

        int[] sum = new int[N*N];
        int idx = 0;
        size = N * N;

        for (int i = 0; i < N ; i++) {
            for (int j = 0; j< N ; j++) {
                sum[idx] = C[i] + D[j];
                idx ++;
            }
        }

        Arrays.sort(sum);

        long ans = 0;

        for (int i = 0; i < N ; i++) {
            for (int j = 0; j< N ; j++) {
                int target = (-1) * (A[i] + B[j]);
                // 이분탐색으로 몇개인지 확인
                ans += upper(sum, target) - lower(sum, target);
            }
        }

        System.out.println(ans);
    }
}
