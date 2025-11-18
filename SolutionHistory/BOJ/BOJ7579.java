import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/7579)
    // BOJ 7579 앱

    // 구간의 합 아니고

    // 그리디?, 투포인터? 매개변수 탐색?
    // 5 60
    //30 10 15 35 40 라 하자
    //3 0 3 5 4

    // 제일 먼저 0 10 남은거 50
    // 3 30 -> 남은거 20
    // 3 15 -> 남은거 5

    // 합이 최소가 되는 방법을 먼저 찾고, 그다음 차선책?

    // 배낭 문제 & DP




    static int N; // 앱 갯수
    static int M;
    static int[] DP; // cost에 따른 메모리 회수 최대치 저장



    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] nm = br.readLine().split(" ");
        N = Integer.parseInt(nm[0]);
        M = Integer.parseInt(nm[1]);

        // 둘째 줄 메모리 바이트 수
        String[] memoryInput = br.readLine().split(" ");
        String[] costInput = br.readLine().split(" ");

        int totalCost = 0 ;


        for (int i = 0; i < N ; i++) {
            totalCost += Integer.parseInt(costInput[i]);
        }

        DP = new int[totalCost + 1];

        for (int i = 0; i < N; i++) {
            int memory = Integer.parseInt(memoryInput[i]);
            int cost = Integer.parseInt(costInput[i]);

            for (int j = totalCost ; j >= cost; j-- ) {
                DP[j] = Math.max(DP[j] , DP[j - cost] + memory);
            }
        }

        for (int i = 0; i < totalCost + 1; i++) {
            if (DP[i] >= M) {
                System.out.println(i);
                break;
            }
        }

    }
}
