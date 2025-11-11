import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/2357)
    // BOJ 2357 - 최솟값과 최댓값
    // 세그먼트 트리라는건 알고있기에, 내가 제대로 배웠는지 확인하는 단계

    // 트리 생성 / 쿼리 / 업데이트 중 // 쿼리만 존재

    // 트리는 응용해서, 각 트리의 노드는 최댓값과 최솟값을 저장한다.


    // 트리 생성 노드는 2N - 1 개 이지만

    // 높이가 (log_2 (N) 올림) + 1 으로 한뒤
    // 노드 최대 2^높이 가 되어서 2N -1 개보다 좀 더 넉넉하게 되도록



    static int N;
    static int M;

    static int[] origin;
    static int[][] tree;

//    static class Node {
//        int min;
//        int max;
//
//        Node(int min, int max) {
//            this.min = min;
//            this.max = max;
//        }
//    }

    // 현재 노드 위치, start index, end index 세개를 인자로 받는다
    static void init(int nodeIdx, int start, int end) {
        if (start == end) {
            tree[nodeIdx] = new int[]{origin[start], origin[start]};
            return;
        }
        init(nodeIdx * 2, start, (start + end) / 2 );
        init(nodeIdx * 2 + 1, ((start + end) / 2) + 1, end );

        int currentMax = Math.max(tree[nodeIdx * 2][1], tree[nodeIdx * 2 + 1][1]);
        int currentMin = Math.min(tree[nodeIdx * 2][0], tree[nodeIdx * 2 + 1][0]);

        tree[nodeIdx][0] = currentMin;
        tree[nodeIdx][1] = currentMax;
    }

    static int[] query(int nodeIdx, int start, int end, int left, int right) {
        if (left > end || start > right) {
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        }
        if (left <= start && right >= end) {
            return tree[nodeIdx];
        }
        int[] leftNode = query(nodeIdx * 2, start, (start + end) / 2, left, right);
        int[] rightNode = query(nodeIdx * 2 + 1, (start + end) / 2 + 1, end, left, right);

        int currentMax = Math.max(leftNode[1], rightNode[1]);
        int currentMin = Math.min(leftNode[0], rightNode[0]);

        return new int[]{currentMin, currentMax};
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        origin = new int[N];

        int height = (int) Math.ceil( ( Math.log(N) / Math.log(2) ) );
        int size = 1 << (height + 1);

        tree = new int[size][2];

        for (int i = 0; i < N ; i++) {
            origin[i] = Integer.parseInt(br.readLine());
        }

        init(1, 0, N - 1);

        // 쿼리 시 들어오는 시작, 끝 숫자에서 1씩 뺸 인덱스를 계산할 필요 있다.
        StringBuilder sb = new StringBuilder();

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken()) - 1;
            int right = Integer.parseInt(st.nextToken()) - 1;
            int[] ans = query(1, 0, N-1, left, right);
            sb.append(ans[0]);
            sb.append(" ");
            sb.append(ans[1]);
            sb.append('\n');
        }
        System.out.println(sb);
    }
}
