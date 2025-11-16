import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/15311)
    // BOJ 15311 약 팔기
    // 최소의 약봉지를 구하는 문제라고 생각하면 너무 어려움
    // 근데 최소의 약봉지 조건이 아니라 적절하게 100만 내의 숫자를 1000개의 연속된 숫자의 합으로 표현하는거라 쉽긴한데
    // 나는 이것도 못떠올리긴 했음.


    public static void main(String[] args) throws Exception {

//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


        StringBuilder sb = new StringBuilder();

        sb.append(2000 + "\n");

        for (int i = 0; i < 1000 ; i++) {
            sb.append(1 + " ");
        }

        for (int i = 0 ; i < 1000; i++) {
            sb.append(1000 + " ");
        }

        bw.write(sb.toString());
        bw.flush();

    }
}
