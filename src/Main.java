import java.io.*;
import java.util.*;

public class Main {

    // https://www.acmicpc.net/problem/23978

    static int N;
    static long K;
    static int[] days;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Long.parseLong(st.nextToken());
//        System.out.println(K);

        days = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0 ; i < N; i++) {
            days[i] = Integer.parseInt(st.nextToken());
        }

        long answer = binarySearch();

        System.out.println(answer);

    }
    // 최소의 long X 를 찾고 싶다.
    private static long binarySearch() {
        long left = 1;
        long right = 10_000_000_000L;

        while (left < right) {
            long mid = left + (right - left) / 2;
            if (isPossible(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // a 부터 b 까지의 합 -> (a + b /) 2 * (b - a)
    // 1 2 4
    // 10 억
    private static boolean isPossible(long current) {
        long answer = 0;

        for (int i = 0 ; i < N - 1; i ++) {
            if (days[i + 1] - days[i] == 1) {
                answer += current;
            } else {
                int gap = Math.min(days[i + 1] - days[i], (int) current);
                // 숫자 차이가 current 보다 작으면 문제가 생겼네
                answer += ((current + current - (gap - 1)) * (gap)) / 2;
            }
//            System.out.println("지금 current : " + current +  " 일 때 answer : " + answer);
            if (answer >= K) return true;
        }
//        System.out.println("지금 current : " + current +  " 일 때 answer : " + answer);
        answer += (current + 1) * (current) / 2; // 5억 * 5억 이면

        return answer >= K;
    }

}