import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/15947

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;

        int n = sc.nextInt();

        // 0-based index로 변환
        int c = (n - 1) / 14; // 진행된 사이클 횟수
        int p = (n - 1) % 14; // 현재 사이클 내에서의 단어 위치 (0~13)

        // 위치에 따른 단어 출력
        if (p == 0 || p == 12) {
            System.out.println("baby");
        } else if (p == 1 || p == 13) {
            System.out.println("sukhwan");
        } else if (p == 4) {
            System.out.println("very");
        } else if (p == 5) {
            System.out.println("cute");
        } else if (p == 8) {
            System.out.println("in");
        } else if (p == 9) {
            System.out.println("bed");
        } else if (p == 2 || p == 6 || p == 10) {
            // tururu 자리: 기본 ru 2개 + 사이클 횟수
            printTuRu(c + 2);
        } else if (p == 3 || p == 7 || p == 11) {
            // turu 자리: 기본 ru 1개 + 사이클 횟수
            printTuRu(c + 1);
        }
    }

    // ru의 개수(k)에 따라 알맞은 형식으로 출력하는 메서드
    private static void printTuRu(int k) {
        if (k >= 5) {
            System.out.println("tu+ru*" + k);
        } else {
            StringBuilder sb = new StringBuilder("tu");
            for (int i = 0; i < k; i++) {
                sb.append("ru");
            }
            System.out.println(sb.toString());
        }
    }
}