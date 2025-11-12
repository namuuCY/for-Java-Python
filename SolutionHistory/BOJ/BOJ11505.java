import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/11505)
    // BOJ 11505  구간 곱 구하기

    static int N;
    static int M; // 수 변경
    static int K; // 구간의 곱
    static final long PRIME = 1_000_000_007L;

    // 각각의 수가 백만까지 가능해서 곱하면 int범위 초과임
    // long으로 받고 곱셈시 1 000 000 007로 나누자

    static long[] origin;
    static long[] tree;


    static void init(int nodeIdx, int start, int end) {
        if (start == end) {
            tree[nodeIdx] = origin[start];
            return;
        }
        init(nodeIdx * 2, start, (start + end) / 2);
        init(nodeIdx * 2 + 1, (start + end) / 2 + 1 , end);
        tree[nodeIdx] = (tree[nodeIdx * 2] * tree[nodeIdx * 2 + 1]) % PRIME;
    }

    static long query(int nodeIdx, int start, int end, int left, int right) {
        if (end < left || right < start) return 1;
        if (left <= start && end <= right) return (int) tree[nodeIdx];

        long lMultiple = query(nodeIdx * 2, start, (start + end) / 2, left, right);
        long rMultiple = query(nodeIdx * 2 + 1,  (start + end) / 2 + 1, end, left, right);

        return (lMultiple * rMultiple) % PRIME;

    }

    static void update(int nodeIdx, int targetIdx, int value, int start, int end) {


        if (targetIdx < start || targetIdx > end) return;
        if (start == end) {
            origin[targetIdx] = value;
            tree[nodeIdx] = value;
            return;
        }
        // update 시 처리 안했음.
        update(nodeIdx * 2, targetIdx, value, start, (start + end) / 2 );
        update(nodeIdx * 2 + 1, targetIdx, value, (start + end) / 2 + 1, end);
        tree[nodeIdx] = tree[nodeIdx * 2] * tree[nodeIdx * 2 + 1] % PRIME;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        origin = new long[N];
        for (int i = 0 ; i < N ; i++) {
            origin[i] = Long.parseLong(br.readLine());
        }

        int height = (int) Math.ceil(Math.log(N) / Math.log(2));
        int size = (1 << (height + 1));
        tree = new long[size];

        init(1, 0, N - 1);

        StringBuilder sb= new StringBuilder();

        for (int j = 0 ; j < M + K ; j++) {
            String[] query = br.readLine().split(" ");
            if (query[0].contains("1")) {
                Integer targetIdx = Integer.parseInt(query[1]) - 1;
                update(1, targetIdx, Integer.parseInt(query[2]), 0, N - 1);
            } else {
                Integer leftIdx = Integer.parseInt(query[1]) - 1;
                Integer rightIdx = Integer.parseInt(query[2]) - 1;
                sb.append(query(1, 0, N-1, leftIdx, rightIdx));
                sb.append('\n');
            }
        }
        bw.write(sb.toString());
        bw.flush();
    }
}
