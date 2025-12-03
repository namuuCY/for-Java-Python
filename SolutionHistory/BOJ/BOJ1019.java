import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/1019)
    // 1019 책 페이지

    static int N;
    static int digit;
    static int[][] count;
    static int[] origin;

    static int dec(int currentDigit) {
        int temp = currentDigit;
        int ans = 1;
        while (temp -- > 0) {
            ans *= 10;
        }

        return ans;
    }

    static void calculate(int current, int currentDigit) {
        int realDigit = dec(currentDigit);
//        debug();

//        if (origin[currentDigit] == 0 || current == 0) {
//            if (currentDigit == 0) {
//                count[currentDigit][current] =
//                        ((N / ( realDigit * 10))) * realDigit;
//                return;
//            } else {
//                count[currentDigit][current] =
//                        (N / ( realDigit * 10)) * realDigit
//                                + N % realDigit + 1;
//                return;
//            }
//        }

        if (currentDigit == digit - 1) {
            // 맨앞자리
            if (current == 0) {
                return;
            }
            if (current == origin[currentDigit]) {
                // 당사자 = 해당 숫자
                count[currentDigit][current] = N % realDigit;
            } else if (current < origin[currentDigit]) {
                count[currentDigit][current] = realDigit;
            }
        } else if (origin[currentDigit] == 0 && current == 0) {
            if (currentDigit == 0) {
                count[currentDigit][current] = N / 10;
            } else {
                count[currentDigit][current] =
                        (N / ( realDigit * 10) - 1) * realDigit
                                + N % realDigit
                                + 1;
            }
        } else {
            //
            if (current == origin[currentDigit] && current != 0) {
                count[currentDigit][current] =
                        (N / ( realDigit * 10)) * realDigit
                                + N % realDigit
                                + 1;
            } else if (current < origin[currentDigit] && current != 0) {
                count[currentDigit][current] =
                        ((N / ( realDigit * 10)) + 1) * realDigit;
            } else {
                count[currentDigit][current] =
                        ((N / ( realDigit * 10))) * realDigit;
            }
        }
    }

    static void debug() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < digit; i++) {
            for (int j = 0 ; j < 10 ; j++) {
                sb.append(count[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        origin = new int[12];

        // 먼저, 몇 째 자리 인지 판단하고
        int temp = N;
        digit = 1;
        while (temp > 0) {
            int rest = temp % 10;
            origin[digit - 1] = rest;
            digit ++;
            temp /= 10;
        }

        count = new int[digit][10];
        int[] ans = new int[10];

        for (int curDigit = 0; curDigit < digit ; curDigit++) {
            for (int curNumber = 0; curNumber < 10 ; curNumber++) {
                calculate(curNumber, curDigit);
                ans[curNumber] += count[curDigit][curNumber];
            }
        }

//        debug();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(ans[i]);
            sb.append(" ");
        }

        bw.write(sb.toString());
        bw.flush();

    }
}