import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    // https://www.acmicpc.net/problem/1655
    // 1655 가운데를 말해요

    static int N;
    static int MIN = -1_000_000;
    static int MAX = 1_000_000;
    // lq는 최대힙
    // 아래 람다식이 안됐던 이유 :
    // 초기값이 integer min value라 int overflow가 났던 것
    static PriorityQueue<Integer> lq = new PriorityQueue<>((a, b) -> b - a);
    static PriorityQueue<Integer> uq = new PriorityQueue<>();

    // lq == uq 일 떄
    static void equal(int val) {
        if (val <= uq.peek()) {
            lq.add(val);
            return;
        }
        lq.add(uq.poll());
        uq.add(val);
    }

    // lq + 1 == uq 일 때
    static void nonEqual(int val) {
        if (val >= lq.peek()) {
            uq.add(val);
            return;
        }
        uq.add(lq.poll());
        lq.add(val);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        lq.add(MIN);
        uq.add(MAX);

        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < N; i++ ) {
            if (i != 0) {
                sb.append("\n");
            }
            int val = Integer.parseInt(br.readLine());
            if (i % 2 == 0) {
                equal(val);
            } else {
                nonEqual(val);
            }
            sb.append(lq.peek());
        }
        bw.write(sb.toString());
        bw.flush();
    }
}