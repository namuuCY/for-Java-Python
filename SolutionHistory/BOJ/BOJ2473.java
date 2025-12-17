import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/2473
    // 2473 세 용액


    static int N;
    static long[] numbers;


    static int bisect(int start, int end, long value) {
        int ts = start + 1;
        int te = end - 1;
        // lowerbound;
        while (ts < te) {
            int mid = (ts + te) / 2;
            if (numbers[mid] >= value) {
                te = mid;
            } else {
                ts = mid + 1;
            }
        }
        return ts;

//        int upper;
//        ts = start;
//        te = end;
//        while (ts < te) {
//            int mid = (ts + te) / 2;
//            if (numbers[mid] > value) {
//                te = mid;
//            } else {
//                ts = mid + 1;
//            }
//        }
//        upper = ts;
//
//        if (lower == start) return upper


    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        numbers = new long[N];

        String[] input = br.readLine().strip().split(" ");

        for (int i = 0; i < N ; i++ ) {
            numbers[i] = Integer.parseInt(input[i]);
        }

        Arrays.sort(numbers);

        long minGap = Long.MAX_VALUE;
        long[] answer = new long[3];

        for (int i = 0 ; i < N - 2; i++) {
            for (int j = i + 2 ; j < N; j++) {
                long targetValue = (-1) * (numbers[i] + numbers[j]);
                int possibleIdx = bisect(i, j, targetValue);

                if (i < possibleIdx) {
                    long currentGap = Math.abs(numbers[i] + numbers[possibleIdx] + numbers[j]);
                    if (currentGap < minGap) {
                        minGap = currentGap;
                        answer[0] = numbers[i];
                        answer[1] = numbers[possibleIdx];
                        answer[2] = numbers[j];
                    }
                }

                if (i < possibleIdx - 1) {
                    long currentGap = Math.abs(numbers[i] + numbers[possibleIdx - 1] + numbers[j]);
                    if (currentGap < minGap) {
                        minGap = currentGap;
                        answer[0] = numbers[i];
                        answer[1] = numbers[possibleIdx - 1];
                        answer[2] = numbers[j];
                    }
                }

            }
        }

        System.out.println(answer[0] + " " + answer[1] + " " + answer[2]);

    }

}