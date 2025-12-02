import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/1949)
    // 1949 우수마을

    static int N;
    // 트리 구조로 이뤄져있는 마을
    // 우수 마을로 선정된 주민수의 총합을 최대로
    // 인접 하면 우수마을 못함
    // 우수마을로 선정되지 못한 마을은 적어도 하나의 우수마을과 인접
    static int[] pop;
    static List<List<Integer>> edge;
    static int[] good;
    static int[] notGood;

    // tree 이므로 어느 하나를 찍어서 DFS를 시작해도 결국 맨 마지막 마을까지 가게 되어있음.
    //

    static void dfs(int current, int parent) {
        good[current] = pop[current];
        notGood[current] = 0;

        // 어디로 갈지 결정
        for (int nextNode : edge.get(current)) {
            if (parent == nextNode) continue;
            dfs(nextNode, current);
            good[current] += notGood[nextNode];
            notGood[current] += Math.max(good[nextNode], notGood[nextNode]);
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        pop = new int[N];
        good = new int[N];
        notGood = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0 ; i < N ; i ++) {
            pop[i] = Integer.parseInt(st.nextToken());
        }

        edge = new ArrayList<>();
        for (int i = 0; i < N; i++ ) {
            edge.add(new ArrayList<>());
        }

        for (int i = 0; i < N - 1; i ++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()) - 1;
            int e = Integer.parseInt(st.nextToken()) - 1;
            edge.get(s).add(e);
            edge.get(e).add(s);
        }

        dfs(0, -1);


        int ans = Math.max(good[0], notGood[0]);

        System.out.println(ans);
    }
}