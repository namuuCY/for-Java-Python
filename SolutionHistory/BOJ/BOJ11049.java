import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/11049)
    // BOJ 11049 행렬 곱셈순서

    static int N;
    static int[][] matrix;
    static int[][] DP;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        matrix = new int[N][2];
        DP = new int[N][N];

        for (int i = 0 ; i < N ; i++) {
            String[] temp = br.readLine().split(" ");
            matrix[i][0] = Integer.parseInt(temp[0]);
            matrix[i][1] = Integer.parseInt(temp[1]);
        }

        // 간격 0~ N -2 사이로 점점 DP 증가시키기

        // 00 11 22 33 44 55 ...
        // 01 12 23 34 45 ..
        // 02 13 24 35 46 ..
        // 0 N-1
        // i -> 두 행렬 인덱스 차이
        for (int i = 0 ; i < N ; i++) {
            // j -> 시작 인덱스
            for (int j = 0 ; j < N - i; j++) {
                // DP[j][j+i] 를 계산

                // k -> 두 인덱스 사이에서 왔다리 갔다리
                if (i == 0) {
                    DP[j][j+i] = 0;
                    continue;
                }

                if (i == 1) {
                    DP[j][j+i] = matrix[j][0] * matrix[j][1] * matrix[j+1][1];
                    continue;
                }

                int ans = Integer.MAX_VALUE;

                // 02 면 // 00 12 // 01 22

                for (int k = 0; k < i ; k++) {
                    int temp = DP[j][j + k] + DP[j + k + 1][j + i] + matrix[j][0] * matrix[j+k][1] * matrix[j+i][1];
                    ans = Math.min(ans, temp);
                }
                DP[j][j + i] = ans;
            }
        }

        System.out.println(DP[0][N - 1]);
    }
}
