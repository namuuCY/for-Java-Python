import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/15824)
    // 15824 너 봄에는 캡사이신이 맛있단다

    // 생각 1
    // 맨 처음 숫자, 맨 마지막 숫자를 선택해서 그 사이의 인덱스 차이만큼 2^idx로 계산하면 답은 나올거임
    // 그런데 small 만 가능

    // 생각 2 - gemini한테 물어봄
    // 기여도 테크닉 : 이 수가 최댓값에 얼마를, 최솟값에 얼마를 기여하는가

    static long PRIME = 1_000_000_007;
    static int N;
    static long[] numbers;
    static long[] pows;

//    static int pow(int a, int n) {
//        if (n == 0) return 1;
//        if (n == 1) return a;
//
//        int temp = pow(a, n/ 2);
//
//        if (n % 2 == 1) {
//            return ((temp * temp) % PRIME) * a % PRIME;
//        }
//        return (temp * temp) % PRIME;
//    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        numbers = new long[N];
        pows = new long[N + 1];
        pows[0] = 1;

        for (int i = 0; i < N ; i++) {
            pows[i + 1] = (pows[i] * 2) % PRIME;
        }

        String[] input = br.readLine().strip().split(" ");
        for (int i = 0 ; i < N ; i++) {
            numbers[i] = Integer.parseInt(input[i]);
        }

        Arrays.sort(numbers);

        long currentSum = 0;

        for (int i = 0 ; i < N; i++) {
            long maxContribution = pows[i];
            long minContribution = pows[N - 1 - i];

            long count =  (maxContribution - minContribution + PRIME) % PRIME;

            currentSum = (((numbers[i] % PRIME * count) % PRIME) + currentSum) % PRIME;
        }

        System.out.println(currentSum);


    }
}
