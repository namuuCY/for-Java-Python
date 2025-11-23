import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/1106)
    // 1106 호텔


    static int c; // 늘려야하는 호텔 고객수
    static int n; // 도시의 개수
    static int[] DP;

    // DP[v] = c  -> v 투자했을때 c가 나온다. c는 1000이상일때 v의 최솟값을 찾기
    // n 은 20보다 작거나 같음
    // n 마다 c / 홍보비용 -> 계산해서 해도 되긴 할듯?
    // 가령 1000 / 8 = 125 -> 1 2 4 8 16 32 60 이렇게 배분해서
    // 적어도 C명 늘이기 위해 <- C명이 딱 안되는 케이스도 존재한다.
    // 최대 10만이긴 한데... 최대 10만임 C가 1000보다 작거나 같고 가장 가성비 떨어지는거 온리가 100 비용당 1명이라

    static List<int[]> hotels = new ArrayList<>(); // value, customer


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] cn = br.readLine().strip().split(" ");

        c = Integer.parseInt(cn[0]);
        n = Integer.parseInt(cn[1]);


        DP = new int[100001];
        // DP[v] = c  -> v 투자했을때 c가 나온다. c는 1000이상일때 v의 최솟값을 찾기

        while(n-- > 0) {
            // value & customer
            String[] valueCustomer = br.readLine().strip().split(" ");
            int value = Integer.parseInt(valueCustomer[0]);
            int customer = Integer.parseInt(valueCustomer[1]);

            int total = 100000 / value; // 물건 갯수라고 생각하자

            int temp = 1;
            while (total > 0) {

                int current = Math.min(temp, total);

                hotels.add(new int[]{current * value, current * customer});
                temp *= 2;
                total -= current;
            }
        }

        for (int[] vc : hotels) {
            int curValue = vc[0];
            int curCustomer = vc[1];
            for (int i = 100000 ; i >= curValue ; i--) {
                DP[i] = Math.max(DP[i], DP[i-curValue] + curCustomer);
            }
        }

        for (int i = 0; i < 100001; i++) {
            if (DP[i] >= c) {
                System.out.println(i);
                break;
            }
        }
    }
}
