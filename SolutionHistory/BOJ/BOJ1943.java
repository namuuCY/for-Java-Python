import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/1943)
    // 1943 동전분배

    // N 이 100개 이하
    // 금액의 총합이 10만원 넘지 않음.

    // 100개의 N에 대하여
    // 금액의 총합의 절반을 DP로 하고
    // DP에는 true/false
    static int N;
    static int[][] coins;
    static int total;
    static boolean[] DP; //  N 원을 만들 수 있는가?




    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int trial = 0; trial < 3; trial++) {
            N = Integer.parseInt(br.readLine());
            coins = new int[N][2];

            total = 0;

            for (int i = 0; i < N ; i++) {
                String[] input = br.readLine().strip().split(" ");
                coins[i][0] = Integer.parseInt(input[0]);
                coins[i][1] = Integer.parseInt(input[1]);
                total += coins[i][0] * coins[i][1];
            }

            if (total % 2 == 1) {
                System.out.println(0);
                // ㅂㅅ이다
                continue;
            }

            total /= 2;
            DP = new boolean[total + 1];
            DP[0] = true;

            for (int[] arr : coins) {
                int coinVal = arr[0];
                int coinQty = arr[1];
                int[] count = new int[total + 1];

                for (int j = coinVal ; j < total + 1; j++) {
                    // 코인을 안써도 된다
                    if (DP[j]) {
                        continue;
                    }

                    // 코인을 써야하는 순간중
                    if (DP[j - coinVal]) {
                        // validation
                        if (count[j-coinVal] < coinQty) {
                            DP[j] = true;
                            count[j] = count[j-coinVal] + 1;
                        }
                    }
                }
            }
            System.out.println((DP[total]) ? 1 : 0);

        }


    }
}
