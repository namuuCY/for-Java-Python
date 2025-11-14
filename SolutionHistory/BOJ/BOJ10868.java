import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/10868)
    // BOJ 10868 최솟값

    static int N;
    static int M;
    static int[] origin;
    static int[] tree;

    static int treeSize(int size) {
        int height = (int) Math.ceil( Math.log(size) / Math.log(2) );
        return (1 << height + 1);
    }

    static void init(int nodeIdx, int start, int end) {
        if (start == end) {
            tree[nodeIdx] = origin[start];
            return;
        }
        init(nodeIdx * 2, start, (start + end) / 2);
        init(nodeIdx * 2 + 1, (start + end) / 2 + 1, end);
        tree[nodeIdx] = Math.min(tree[nodeIdx * 2], tree[nodeIdx * 2 + 1]);
    }

    static int query(int nodeIdx, int sIdx, int eIdx, int left, int right) {
        if (right < sIdx || eIdx < left) return Integer.MAX_VALUE;
        if (left <= sIdx && eIdx <= right) return tree[nodeIdx];
        int lMin = query(nodeIdx * 2, sIdx, (sIdx + eIdx) / 2, left, right);
        int rMin = query(nodeIdx * 2 + 1, (sIdx + eIdx) / 2 + 1, eIdx, left, right);

        return Math.min(lMin, rMin);
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 들어오는 인덱스에서 1빼야함을 생각
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        origin = new int[N];

        for (int i = 0 ; i < N ; i++) {
            origin[i] = Integer.parseInt(br.readLine());
        }
        // init
        int treeSize = treeSize(N);
        tree = new int[treeSize];

        init(1, 0, N - 1);

        StringBuilder sb = new StringBuilder();
        while (M-- > 0) {
            // 쿼리 로직
            st = new StringTokenizer(br.readLine());
            Integer startIdx = Integer.parseInt(st.nextToken()) - 1;
            Integer endIdx = Integer.parseInt(st.nextToken()) - 1;

            sb.append( query(1, 0, N - 1, startIdx, endIdx) );
            sb.append("\n");
        }

        bw.write(sb.toString());

        bw.flush();





    }
}
