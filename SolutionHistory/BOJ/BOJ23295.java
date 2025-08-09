import java.io.*;
import java.util.*;

public class Main {

    // BOJ 23295 (https://www.acmicpc.net/problem/23295)
    // N 최대 10만개
    // T 최대 10만시간 (T 시간동안 진행)
    // 시간 만족도 : 참가 인원들의 참여할 수 있는 시간의 합
    // T가 만약 1이고, 끝나는 시간이 최대 10만이면

    // O(N * 10^5) 시간복잡도 O(100억)까지 감

    // 매 슬라이딩 윈도우(T 시간)별로 logN 혹은 상수번 만큼의 연산을 하는게 필요함
    // 이 방식대로라면 그렇다
    // 차분 배열

    // 문제 풀이 해결법 보고 왔음 ㅅㅂ
    // 처음부터 슬라이딩 윈도우를 생각하면서 해결하려니까 머리가 복잡했지
    // 맨처음에는 최대한 누적합을 구할 생각만(시간당)
    // 그 이후에 시간당 누적합 구했으면 맨 마지막에 슬라이딩 윈도우 진행

    static int N;
    static int T;


    static int[] delta = new int[100002];
    static long[] people = new long[100001];


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        for (int i = 0 ; i < N ; i++) {
            int t = Integer.parseInt(br.readLine());

            for (int j = 0 ; j < t ; j++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());

                delta[s] ++;
                delta[e] --;

            }
        }


        int current = 0;

        for (int j = 0; j < 100000 ; j++) {
            current += delta[j];
            people[j] = current;
        }

        // 슬라이딩 윈도우 + 최소일때 시작 시간

        long currentSum = 0;

        // 시간 만족도가 최대인 시간 중 가장 빠른 시간을 출력

        for (int a = 0 ; a < T ; a++) {
            currentSum += people[a];
        }

        long maxSum = currentSum;
        int bestStartTime = 0;

        for (int k = 1; k < 100001 - T ; k++) {
            currentSum = currentSum - people[k - 1] + people[k + T - 1];

            if (currentSum > maxSum) {
                maxSum = currentSum;
                bestStartTime = k;
            }
        }

        System.out.println(bestStartTime + " " + (bestStartTime + T));

    }
}
