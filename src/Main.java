import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/17127

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] A = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        long maxSum = 0;

        // i, j, k는 각 그룹의 마지막 인덱스를 의미함
        // 첫 번째 그룹 끝: i
        // 두 번째 그룹 끝: j
        // 세 번째 그룹 끝: k
        // 네 번째 그룹은 k+1부터 N-1까지

        for (int i = 0; i <= N - 4; i++) {
            for (int j = i + 1; j <= N - 3; j++) {
                for (int k = j + 1; k <= N - 2; k++) {

                    // 각 그룹의 곱 계산
                    long p1 = getProduct(A, 0, i);
                    long p2 = getProduct(A, i + 1, j);
                    long p3 = getProduct(A, j + 1, k);
                    long p4 = getProduct(A, k + 1, N - 1);

                    maxSum = Math.max(maxSum, p1 + p2 + p3 + p4);
                }
            }
        }

        System.out.println(maxSum);
    }

    // 배열의 특정 구간 [start, end]의 곱을 반환하는 함수
    public static long getProduct(int[] arr, int start, int end) {
        long prod = 1;
        for (int i = start; i <= end; i++) {
            prod *= arr[i];
        }
        return prod;
    }
}