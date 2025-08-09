import java.io.*;
import java.util.*;

public class Main {

    // BOJ 1213 (https://www.acmicpc.net/problem/1213)
    // 최대 50글자
    // 우선 문자별로 카운팅하고
    // 전체 길이가 짝수이면 => 홀수 하나라도 나오면 안됨
    // 전체 길이가 홀수이면 => 홀수는 하나만 나오고, 맨 마지막에 딱 하나는 위치

    static char[] word;
    static int[] count = new int[26];


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        word = br.readLine().toCharArray();
        int len = word.length;
        char[] ans = new char[len];
        for (char c : word) {
            int cur = (int) c - (int) 'A';
            count[cur]++;
        }

        // 홀수의 개수가 1개 이상인지 확인
        int odd = 0;
        int oddIdx = 0;

        for (int j = 0; j < 26 ; j++) {
            if (count[j] % 2 == 1) {
                odd++;
                oddIdx = j;
            }
        }

        if (odd > 1) {
            System.out.println("I'm Sorry Hansoo");
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0 ; i < 26 ; i ++) {
            for (int j = 0 ; j < count[i]/2 ; j++) {
                sb.append((char) ('A' + i ));
            }
        }

        String front = sb.toString();
        String back = sb.reverse().toString();

        if (odd == 1) {
            char center = (char)(oddIdx + 'A');
            System.out.println(front + center + back);
        } else {
            System.out.println(front + back);
        }

    }
}
