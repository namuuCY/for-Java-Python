import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // BOJ 1300 (https://www.acmicpc.net/problem/1300)
    // N×N인 배열 + N 이 10^5 보다 작거나 같음 => 10^10 개수
    //
    // => long 써야하고, 시간복잡도는 무조건 O(logN)정도가 되어야 함 => 이분탐색 생각해봐야
    // + 매개변수 탐색인듯? 조건이 너무 까다로운 듯
    // 매개변수는 B[k] = x
    // 에서 k인가? x인가?
    // x 보다 작거나 같은 숫자가 k개 있다는 것을 의미

    // x를 조절하면서 k가 나오도록 조절하자

    // N번 연산을 log2(N^2) 로 한다 => 시간복잡도 충분

    static long N;
    static long k;

    public static void main(String[] args) throws Exception {

        // 1트 실패 : 3,3  집어넣으면 2 나와야 하는데 6 나옴
        // 이유 : 이분탐색 종료 조건 까먹음
        // break를 넣으면 안됐네 지금 생각해보니


//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//        N = Long.parseLong(br.readLine());
//        k = Long.parseLong(br.readLine());
//
//
//        long start = 1;
//        long end = N * N;
//        long mid = (start + end) / 2 ;
//        long idx = 0;
//        // 주의할 점 : N 보다 클 때 잘라야함
//
//        while (idx != k) {
//            idx = 0;
//            for (int i = 1; i < N + 1; i++) {
//                idx += Math.min((mid / i), N);
//            }
//            if (idx > k) {
//                end = mid - 1;
//                mid = (start + end) / 2;
//            } else if (idx < k) {
//                start = mid + 1;
//                mid = (start+ end) / 2;
//            }
//            break;
//
//        }
//        System.out.println(idx);



        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Long.parseLong(br.readLine());
        k = Long.parseLong(br.readLine());


        long start = 1;
        long end = N * N;

        // 주의할 점 : N 보다 클 때 잘라야함

        while (start < end) {
            long mid = (start + end) / 2 ;
            long idx = 0;

            for (int i = 1; i < N + 1; i++) {
                idx += Math.min((mid / i), N);
            }


            if (idx >= k) {
                end = mid;

            } else {
                start = mid + 1;
            }

        }
        System.out.println(end);








    }
}
