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


    static int biSearch(List<Integer> list, int target) {
        int start = 0;
        int end = list.size() - 1;
        int ans = 0;

        while (start <= end) {
            int mid = (start + end) / 2;

            if (list.get(mid) > target) {
                end = mid - 1;
            } else if (list.get(mid) < target) {
                start = mid + 1;
            } else {
                // 중복된 갯수 찾기
                ans += 1;
                int temp = mid;

                while (--temp >= 0 && list.get(temp) == target) {
                    ans ++;
                }

                int temp2 = mid;

                while (++temp2 < end + 1 && list.get(temp2) == target) {
                    ans ++;
                }
                break;
            }
        }
        return ans;
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

        List<Integer> sum = new ArrayList<>();

        for (int i = 0; i < N ; i++) {
            for (int j = 0; j< N ; j++) {
                sum.add(C[i] + D[j]);
            }
        }

        sum.sort(Integer::compareTo);

        int ans = 0;

        for (int i = 0; i < N ; i++) {
            for (int j = 0; j< N ; j++) {
                int currentSum = A[i] + B[j];
                // 이분탐색으로 몇개인지 확인
                ans += biSearch(sum, (-1) * currentSum);
            }
        }

        System.out.println(ans);
    }
}
