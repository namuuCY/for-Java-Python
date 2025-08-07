import java.io.*;
import java.util.*;

public class Main {

    // BOJ 1036 (https://www.acmicpc.net/problem/1036)

    static int N;
    static int k;

    static int[] currentSum = new int[60];
    static int[][] gaps = new int[36][60];

    static void calculateCurrent(char[] word) {
        // 맨 뒤에서 부터 읽어야 한다
        int len = word.length;
        for (int i = 0; i < len ; i++) {
            int currentLocation = len - 1 - i;
            int converted = convert(word[i]);
            calculateGap(currentLocation, converted);
            calculateSum(currentLocation, converted);
        }
    }

    static void calculateGap(int location, int currentInt) {
        gaps[currentInt][location] += 35 - currentInt;
    }

    static void calculateSum(int location, int currentInt) {
        currentSum[location] += currentInt;
    }

    static int convert(char c) {
        if (c >= '0' && c <= '9') {
            return ((int) c - (int) '0');
        } else {
            return ((int) c - (int) 'A') + 10;
        }
    }

    static char convert(int i) {
        if (i < 10) {
            return (char) (i + (int) '0');
        } else {
            return (char) (i - 10 + (int) 'A') ;
        }
    }

    static void normalize(int[] num) {
        int len = num.length;
        for (int i = 0; i < len - 1; i++) {
            int current = num[i];
            num[i + 1] += current / 36;
            num[i] = current % 36;
        }
    }

    static void addGap(int[] gap) {
        for (int i = 0; i < 60 ; i++) {
            currentSum[i] += gap[i];
        }
    }





    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N ; i++) {
            char[] word = br.readLine().toCharArray();
            calculateCurrent(word);
        }
        // Gap normalize 및 정렬
        k = Integer.parseInt(br.readLine());

        for (int i = 0 ; i < 36 ; i++) {
            normalize(gaps[i]);
        }

        Arrays.sort(
                gaps,
                (a, b) -> {
                    for (int i = 59 ; i >= 0; i--) {
                        if (a[i] == b[i]) continue;
                        return a[i] - b[i];
                    }
                    return a[0] - b[0];
                }
        );

        for (int i = 0 ; i < k ; i++) {

            addGap(gaps[35 - i]);
        }

        normalize(currentSum);

        StringBuilder sb = new StringBuilder();
        int nonZeroIdx = 0;
        for (int i = 0; i < 60 ; i++) {
            int cur = 59 - i;
            if (currentSum[cur] != 0) {
                nonZeroIdx = cur;
                break;
            }
        }

        for (int i = nonZeroIdx ; i >= 0 ; i-- ) {
            sb.append(convert(currentSum[i]));
        }
        System.out.println(sb);

    }
}
