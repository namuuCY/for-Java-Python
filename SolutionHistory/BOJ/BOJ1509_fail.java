import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/1509)
    // BOJ 1509 팰린드롬 분할

    // 문자열 최대 길이 N <= 2500



    static int N; // 문자열 길이
    static char[] origin;
    static int[][] DP;


    // // 문자열 전체가 말 그대로 하나의 팰린드롬일 경우라면?
    static boolean checkP(int start, int end) {
        if (end - start == 1) {

            return origin[start] == origin[end];
        }

        // 병신이 중간값을 이렇게 잡고있엇으니 ㅋㅋ
//        int mid = (end - start) / 2 + 1;
        int mid = (start + end) / 2 + 1;
        boolean ans = true;
        // 0 1 2 3 4 5 6 7
        // 0 6
        // 2mid = i + x
        // x = 2mid - i
        // 4 7 이면 mid 6
        // 11 - 4
        for (int i = start ; i < mid ; i++) {
            boolean temp = (origin[i] == origin[start + end - i]);
            ans = ans && temp;
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[] input = br.readLine().strip().toCharArray();
        N = input.length;
        origin = new char[N];
        DP = new int[N][N];

        for (int i = 0 ; i < N; i++) {
            origin[i] = input[i];
        }

        // gap 말 그대로 차이
        for (int gap = 0; gap < N ; gap ++) {

            // 문자열 전체가 말 그대로 하나의 팰린드롬일 경우라면?
            // -> method 참고
            for (int i = 0; i < N - gap; i ++) {

                if (gap == 0) {
                    DP[i][i] = 1;
//                    System.out.println("단일문자일때 " +i + " && "+ i +" 에서 " + DP[i][i]);
                    continue;
                }

                if (gap == 1) {
                    if (checkP(i, i+gap)) {
                        DP[i][i+gap] = 1;
                        continue;
                    }
                    DP[i][i+gap] = 2;
//                    System.out.println("팰린체크 " +i + " && "+ (i+gap) +" 에서 " + DP[i][i+gap]);
                    continue;
                }

                // 이게 껴있는 순간 시간복잡도가 너무 높아짐.
                // 이미 N*N 이라서
                if (checkP(i, i+gap)) {
                    DP[i][i+gap] = 1;
//                    System.out.println("팰린체크 " +i + " && "+ i+gap +" 에서 " + DP[i][i+gap]);
                    continue;
                }

                int temp = Integer.MAX_VALUE;


                // 0 2 에서
                // [0 0] +  [1 2]  / [0 1] [2 2]
                // 11 23 / 12 33
                for (int j = i ; j < i + gap ; j ++) {
                    temp = Math.min(temp, DP[i][j] + DP [j+1][i + gap]);
//                    System.out.println("인덱스 체크  " +i + " 시작에 중간값은  "+ j +" 에서 " + (DP[i][j] + DP [j+1][i + gap - 1]));

                }
//                System.out.println("다 체크하고나서 보니  " +i + " && "+ (i+gap) +" 에서 " + DP[i][i+gap]);
                DP[i][i+gap] = temp;
            }
        }

        System.out.println(DP[0][N - 1]);
    }
}
