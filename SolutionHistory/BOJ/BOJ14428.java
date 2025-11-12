import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // Source(https://www.acmicpc.net/problem/14428)
    // BOJ 14428  수열과 쿼리 16

    static int N;
    static int M;
    static int[] origin = new int[200001];
    static int[] tree;


    static void init(int nodeIdx, int start, int end) {
        if (start == end) {
            tree[nodeIdx] = start;
            return;
        }
        init(nodeIdx * 2 , start, (start + end) / 2);
        init(nodeIdx * 2 + 1, (start+end) /2 + 1, end);

        int lIdx = tree[nodeIdx * 2];
        int rIdx = tree[nodeIdx * 2 + 1];

        int ans = origin[lIdx] <= origin[rIdx] ? lIdx : rIdx;
        tree[nodeIdx] = ans;

    }

    static Integer query(int node, int start, int end, int left, int right) {

        // 여기서 문제 발생했음 : 인덱스를 값으로 내보내도록 했는데 인덱스가 리스트보다 크니까 문제 발생
        if (start > right || end < left) {
            return 200000;
        }
        if (left <= start && end <= right) {
            return tree[node];
        }
        int lIdx = query(node * 2, start, (start + end) / 2, left, right);
        int lValue = origin[lIdx];
        int rIdx = query(node * 2 + 1, (start + end) / 2 + 1, end, left, right);
        int rValue = origin[rIdx];
        return lValue <= rValue ? lIdx : rIdx;
    }


    static void update(int node, int target, int value, int left, int right) {
        if (target > right || target < left) {
            return;
        }
        if (left == right) {
            origin[target]  = value;
            tree[node] = target;
            return;
        }
        update(node * 2, target, value, left , (left+right) / 2);
        update(node * 2 + 1, target, value, (left + right) / 2 + 1, right);

        int lIdx = tree[node * 2];
        int rIdx = tree[node * 2 + 1];

        tree[node] = origin[lIdx] <= origin[rIdx] ? lIdx : rIdx;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // index 주의

        N = Integer.parseInt(br.readLine());

        // 아래 방식 왜 안됐는지 조사
        String[] input = br.readLine().split( " ");
//
//        List<Integer> n = Arrays.stream(input)
//                .map(Integer::parseInt)
//                .collect(Collectors.toList());
////         -> 여기에서 NPE 떳음. (이유 : origin 객체 생성이 안되어있었음.)
//        origin.addAll(n);

        M = Integer.parseInt(br.readLine());
        origin[200000] = Integer.MAX_VALUE;
        for (int i = 0 ; i < N ; i++) {
            origin[i] = (Integer.parseInt(input[i]));
        }

        int height = (int) Math.ceil(Math.log(N) / Math.log(2));
        int size = (1 << (height + 1));

        tree = new int[size];

        init(1, 0, N - 1);

        // 입력 index는 1을 빼고, 출력된 index 값에는 1을 더한다.

        StringBuilder sb = new StringBuilder();

        while (M-- > 0) {
            String[] query = br.readLine().split(" ");

            if (query[0].contains("1")) {
                Integer targetIdx = Integer.parseInt(query[1]) - 1;
                update(1, targetIdx, Integer.parseInt(query[2]), 0, N - 1);
            } else {
                Integer s = Integer.parseInt(query[1]) - 1;
                Integer e = Integer.parseInt(query[2]) - 1;
                int ans = query(1, 0, N - 1, s, e) + 1;
//                System.out.println(ans);
                sb.append(ans);
                sb.append('\n');
            }
        }
        bw.write(sb.toString());
        bw.flush();
    }
}
