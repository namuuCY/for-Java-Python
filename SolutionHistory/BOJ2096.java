import java.io.*;
import java.util.*;

public class Main {

    // BOJ 2096 (https://www.acmicpc.net/problem/2096)

    // N(1 ≤ N ≤ 100,000)

    // 문제점 1 : array 초기화
    // 문제점 2 : 변수 사용 시 문제점
    // 값을 먼저 만들고
    // 대입은 제일 마지막에
    // 참조값으로 대입된거는 기존 참조값이 변해도 새로 대입된 값도 바뀜

    // 문제점 3 : 이게 왜 슬라이딩 알고리즘임?
    // https://www.acmicpc.net/board/view/85465
    // "dp에서 테이블을 재활용하면서 메모리를 아끼는 (흔히 토글링이라고도 불리는) 테크닉을 슬라이딩 윈도우라고 부르기도 합니다."

    static int N;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        // 아래 각각의 ans는 0가 min, 1이 max
        StringTokenizer st = new StringTokenizer(br.readLine());
        int c1 = Integer.parseInt(st.nextToken());
        int c2 = Integer.parseInt(st.nextToken());
        int c3 = Integer.parseInt(st.nextToken());
        int[] ans1 = new int[]{c1, c1};
        int[] ans2 = new int[]{c2, c2};
        int[] ans3 = new int[]{c3, c3};

        for (int i = 1; i < N ; i++) {
            st = new StringTokenizer(br.readLine());
            c1 = Integer.parseInt(st.nextToken());
            c2 = Integer.parseInt(st.nextToken());
            c3 = Integer.parseInt(st.nextToken());

            int[] cur1 = ans1;
            int[] cur2 = ans2;
            int[] cur3 = ans3;


            int min1 = Math.min(cur1[0], cur2[0]) + c1;
            int max1 = Math.max(cur1[1], cur2[1]) + c1;

            int min2 = Math.min(Math.min(cur1[0], cur2[0]), cur3[0]) + c2;
            int max2 = Math.max(Math.max(cur1[1], cur2[1]), cur3[1]) + c2;

            int min3 = Math.min(cur2[0], cur3[0]) + c3;
            int max3 = Math.max(cur2[1], cur3[1]) + c3;

            ans1 = new int[]{min1, max1};
            ans2 = new int[]{min2, max2};
            ans3 = new int[]{min3, max3};

        }

        int max = Math.max(Math.max(ans1[1], ans2[1]), ans3[1]);
        int min = Math.min(Math.min(ans1[0], ans2[0]), ans3[0]);

        System.out.println(max + " " + min);

    }
}
