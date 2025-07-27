import java.io.*;
import java.util.*;

public class Main {

    // BOJ 2961 (https://www.acmicpc.net/problem/2961)


    // N 이 10개 => 경우의 수 최대 2^10 = 1024

    static int N;
    static int[][] ing;
    static int minDiff = Integer.MAX_VALUE;



    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        ing = new int[N][2];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            ing[i][0] = Integer.parseInt(st.nextToken()); // 신
            ing[i][1] = Integer.parseInt(st.nextToken()); // 쓴
        }

        for (int i = 1 ; i < (1 << (N)); i++) {
            long totalSour = 1;
            int totalBitter = 0;

            for (int j = 0; j < N; j++) {

                if ((i & (1 << j)) != 0) {
                    totalSour *= ing[j][0];
                    totalBitter += ing[j][1];
                }
            }
            minDiff = Math.min(minDiff, (int) Math.abs(totalSour - totalBitter));

        }
        System.out.println(minDiff);

    }
}
