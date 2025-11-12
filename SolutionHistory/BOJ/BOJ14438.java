import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/2042)
    // BOJ 2042 구간 합 구하기

    // 세그먼트 트리의 시간 복잡도
    // 생성 시 2N-1개의 노드를 생성하고, 노드당 최대 4번을 방문한다? O(N)
    // 조회 및 변경 쿼리에 O(logN)

    // 입력으로 주어지는 수 갯수
    static int N;

    // 수 변경
    static int M;

    // 조회 쿼리
    static int K;

    // 원본 숫자 -2^63 보다 크같 ...
    // 단, 정답은 -263보다 크거나 같고, 263-1보다 작거나 같은 정수 인데

    static long[] origin;
    static long[] tree;

    static void init(int idx, int start, int end) {
        if (start == end) {
            tree[idx] = origin[start];
            return;
        }
        init(idx * 2, start, (start + end) / 2);
        init(idx * 2 + 1,  ((start + end) / 2)+1 , end);
        tree[idx] = tree[idx * 2] + tree[idx * 2 + 1];
    }

    static long query(int idx, int start, int end, int left, int right) {
        // left right start end 처럼 완전히 동떨어진 범위
        if (left > end || right < start) return 0L;
        // left start end right 처럼 완전히 포홤된 범위
        if (left <= start && right >= end ) return tree[idx];

        long leftSum = query(idx * 2, start, (start+end)/2, left, right );
        long rightSum = query(idx * 2 + 1, ((start+end))/2 + 1,end, left, right );

        return leftSum + rightSum;
    }

    static void update(int nodeIdx, int targetIdx, long val, int start, int end) {
        // targetIdx가 범위 바깥
        if (start > targetIdx || end < targetIdx) return;
        // 리프노드 도착 시
        if (start == end) {
            origin[targetIdx] = val;
            //
            tree[nodeIdx] = val;
            return;
        }
        update(nodeIdx * 2, targetIdx, val, start, (start + end) / 2);
        update(nodeIdx * 2 + 1, targetIdx, val,  (start + end) / 2 + 1 , end);
        tree[nodeIdx] = tree[nodeIdx * 2] + tree[nodeIdx * 2 + 1];
    }



    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        origin = new long[N];

        for (int i = 0; i < N ; i++ ) {
            origin[i] = Long.parseLong(br.readLine());
        }
        // 트리 생성 필요

        int height = (int) Math.ceil((Math.log(N) / Math.log(2)));
        int size = (1 << (height + 1));

        tree = new long[size];

        init(1, 0, N - 1);
        StringBuilder sb = new StringBuilder();


        for (int j = 0; j < M + K; j++) {
            String[] ss = br.readLine().split(" ");

            if (ss[0].contains("1")) {
                // 수정
                Integer targetIdx = Integer.parseInt(ss[1]) - 1;
                Long val = Long.parseLong(ss[2]);
                update(1, targetIdx, val, 0, N - 1);
            } else {
                // 쿼리
                Integer startIdx = Integer.parseInt(ss[1]) - 1;
                Integer endIdx = Integer.parseInt(ss[2]) - 1;
                sb.append(query(1, 0, N - 1, startIdx, endIdx));
                sb.append('\n');
            }
        }

        System.out.println(sb);

    }
}
