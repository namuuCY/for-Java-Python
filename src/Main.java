import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/26163
    // 문제 출제

    static int LENGTH = 5;



    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().strip());

        int[] scores = new int[LENGTH];

        for (int i = 0 ; i < LENGTH; i++) {
            scores[i] = Integer.parseInt(st.nextToken());
        }
        int maxBill = 0;

        for (int first = 0 ; first <= 15 ; first ++) {
            for (int second = 0 ; second <= 15 ; second ++) {
                for (int third = 0 ; third <= 15 ; third ++) {
                    for (int fourth = 0 ; fourth <= 15 ; fourth ++) {
                        for (int fifth = 0 ; fifth <= 15 ; fifth ++) {
                            if (isCountable(first, second, third, fourth, fifth)) {
                                maxBill = Math.max(maxBill,
                                                   calculateBill(first, second, third, fourth, fifth, scores)
                                                   );
                            }
                        }
                    }
                }
            }
        }

        System.out.println(maxBill);
    }

    private static boolean isCountable(int first, int second, int third, int fourth, int fifth) {
        int ruleBasedScore = calculateScoreRule(first, second, third, fourth, fifth);
        if (first + second + third + fourth + fifth <= 3) {
            return ( ruleBasedScore <= 10);
        } else {
            return ruleBasedScore <= 15;
        }
    }

    private static int calculateScoreRule(int first, int second, int third, int fourth, int fifth) {
        return first + 2 * second + 3 * third + 4 * fourth + 5 * fifth;
    }

    private static int calculateBill(int first, int second, int third, int fourth, int fifth, int[] scores) {
        return first * scores[0]
                + second * scores[1]
                + third * scores[2]
                + fourth * scores[3]
                + fifth * scores[4];
    }

}