import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/1202)
    // BOJ 1202 - 보석도둑

    // N : 보석갯수 (30만개)
    // K : 가방갯수 (30만개)

    // 무게 비교 -> value 비교로 나열 (?)

    // 맨 처음 가방이 선택할 보석 선택
    // 그러면 선택 알고리즘이 ..

    // 검색 시간복잡도 logN 이면서 뭐 하나 빼는데도 시간 얼마 안걸리는 자료구조?
    // heap 을 통해서 해결
    // 그러면 맨 윗순서는?
    // 1 65
    // 5 23
    // 2 99

    // 5 23
    // 1 65
    // 2 99

    // 2 48
    // 9 10
    // 7 31
    // 4 35

    // 2 48 무게순으로 작은 순서부터, 그다음에 가격 큰 순서대로
    // 4 35 가방은 작은 순서부터
    // 7 31
    // 9 10

    // 2 4 7

    static class Jewel implements Comparable<Jewel> {
        int weight;
        int value;

        Jewel(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }

        public int compareTo(Jewel o) {
            return this.weight - o.weight;
        }
    }

    static int N;
    static int K;
    static PriorityQueue<Jewel> Q = new PriorityQueue<>();

    // 얘는 Value만을 넣어야 한다
    static PriorityQueue<Integer> availableQ = new PriorityQueue<>(
            (x,y) -> (y - x)
    );

    static long totalValue;


    // Q에서 백팩무게보다 작은 보석들을 다 빼서 우선순위 큐에 집어넣음.
    //  확인용 메서드 - 없거나, 맨앞애가 무게가 가방보다 크거나

    static boolean isAvailable(int weight) {
        Jewel jewel = Q.peek();
        if (Objects.isNull(jewel)) return false;
        return jewel.weight <= weight;
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        int[] backpacks = new int[K];

        for (int i = 0 ; i< N ; i++) {
            StringTokenizer st2 = new StringTokenizer(br.readLine());
            int weight = Integer.parseInt(st2.nextToken());
            int value = Integer.parseInt(st2.nextToken());

            Q.add(new Jewel(weight, value));
        }

        // 보석 줄세우고,
        // 백팩의 무게보다 작은 보석 모두를 우선순위 큐에 집어넣으면 알아서 정렬됨
        //


        for (int j = 0 ; j < K ; j++) {
            int b = Integer.parseInt(br.readLine());
            backpacks[j] = b;
        }

        Arrays.sort(backpacks);


        for (int k = 0 ; k < K ; k++) {
            Integer bweight = backpacks[k];

            // Q에서 백팩무게보다 작은 보석들을 다 빼서 우선순위 큐에 집어넣음.
            //  확인용 메서드 - 없거나, 맨앞애가 무게가 가방보다 크거나
            while (isAvailable(bweight)) {
                Jewel jewel = Q.poll();
                availableQ.add(jewel.value);
            }

            if (availableQ.isEmpty()) continue;
            totalValue += availableQ.poll();

        }

        System.out.println(totalValue);





    }
}
