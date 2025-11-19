import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // Source(https://www.acmicpc.net/problem/20303)
    // BOJ 20303 할로윈의 양아치

    // K 명 이상의 아이들이 울기 시작하면 안됨 -> K 명 미만이 울때 사탕의 최대 갯수
    // 한명 뺏으면 그 친구들의 사탕도 다 뺏음.

    // N, M, K
    static int N; // 아이들 숫자
    static int M; // 아이들 친구 관계 숫자
    static int K;

    static int[] candies;
    static int[] parents;
    static int[] ranks;//

    static int[] DP;

    static int find(int u) {
        if (! (u == parents[u])) {
            parents[u] = find(parents[u]);
        }
        return parents[u];
    }

    static boolean union(int u, int v) {
        int root_u = find(u);
        int root_v = find(v);

        if (root_u == root_v) return false;

        // rank를 비교해서 rank가 큰 쪽이 root
        if (ranks[root_u] > ranks[root_v]) {
            parents[root_v] = root_u;
        } else {
            parents[root_u] = root_v;
            if (ranks[root_u] == ranks[root_v]) {
                ranks[root_v] ++;
            }
        }
        return true;
    }

    static class DisjointSet {
        int members;
        int candies;

        DisjointSet(int candies) {
            this.members = 1;
            this.candies = candies;
        }

        void update(int candies) {
            this.members ++;
            this.candies += candies;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] nmk = br.readLine().strip().split(" ");
        N = Integer.parseInt(nmk[0]);
        M = Integer.parseInt(nmk[1]);
        K = Integer.parseInt(nmk[2]);

        candies = new int[N];
        parents = new int[N];
        ranks = new int[N];

        String[] candyInput = br.readLine().strip().split(" ");

        for (int i = 0 ; i < N; i ++) {
            candies[i] = Integer.parseInt(candyInput[i]);
            parents[i] = i;
        }

        while (M -- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;

            union(u, v);
        }

        // 분류 작업 끝.


        Map<Integer, DisjointSet> rootDict = new HashMap<>();

        for (int i = 0; i < N; i ++) {
            int curRoot = find(i);
            if (Objects.isNull(rootDict.get(curRoot))) {
                rootDict.put(curRoot, new DisjointSet(candies[i]));
            } else {
                DisjointSet ds = rootDict.get(curRoot);
                ds.update(candies[i]);
                rootDict.put(curRoot, ds);
            }
        }

        // 배낭 문제

        int sum = rootDict.values().stream()
                .map(ds -> ds.members)
                .reduce(Integer::sum)
                .orElse(0);

        DP = new int[sum + 1];

        List<DisjointSet> aggregate = rootDict.values().stream()
                .collect(Collectors.toList());

        for (DisjointSet ds : aggregate) {
            int curMembers = ds.members;
            int curCandies = ds.candies;

            for (int i = sum; i >= curMembers ; i-- ) {
                DP[i] = Math.max(DP[i], DP[i - curMembers] + curCandies);
            }
        }

        int ans = 0;

        for (int i = 0 ; i < K; i++) {
            ans = Math.max(DP[i], ans);
        }

        System.out.println(ans);



    }
}
