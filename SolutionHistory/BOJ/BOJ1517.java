import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/1517
    // 1517 버블소트

    // 먼저 정렬하고,

    // 제일 큰 것의 인덱스부터 버블소트 -> update (MAX_VALUE로 하고, MAX VALUE일 경우에는 count 안함)
    // tree 는 인덱스의 차이가 1일때 계산함.
    // tree가 뭔데? 교환이 필요하면 + 임? 단순히?


    static int N;
    static int[][] numbers;
    static long[] tree;
    static long result;


    static void init(int nodeIdx, int start, int end) {
        if (start == end) {
            tree[nodeIdx] = 1;
            return;
        }
        init(nodeIdx * 2 , start, (start + end) / 2);
        init(nodeIdx * 2 + 1, (start + end) / 2 + 1, end);
        tree[nodeIdx] = tree[nodeIdx * 2] + tree[nodeIdx * 2 + 1];
    }

    static long query(int nodeIdx, int left, int right, int start, int end) {
        if (right < start || end < left) return 0;
        if (left <= start && end <= right) return tree[nodeIdx];
        long leftSwap = query(nodeIdx * 2, left, right, start, (start + end) / 2);
        long rightSwap = query(nodeIdx * 2 + 1, left, right,  (start + end) / 2 + 1 , end);
        return leftSwap + rightSwap;
    }

    static void update(int nodeIdx, int targetIdx, int start, int end) {
        if (start == end) {
            if (start == targetIdx) {
                tree[nodeIdx] = 0;
            }
            return;
        }
        if (targetIdx < start || end < targetIdx) return;
        update(nodeIdx * 2, targetIdx, start, (start+end) / 2);
        update(nodeIdx * 2 + 1, targetIdx, (start+end) / 2 + 1, end);
        tree[nodeIdx] = tree[nodeIdx * 2] + tree[nodeIdx * 2 + 1];
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        numbers = new int[N][2];

        int height = ((int) Math.ceil(Math.log(N) / Math.log(2))) + 1;
        int treeSize = (1 << height);
        tree = new long[treeSize];

        String[] origin = br.readLine().strip().split(" ");

        for (int i = 0 ; i < N ; i++) {
            numbers[i][0] = Integer.parseInt(origin[i]);
            numbers[i][1] = i;
        }

        // init 먼저

        init(1, 0, N - 1);

        Arrays.sort(numbers, (a, b) -> {
            if (a[0] != b[0]) return b[0] - a[0];
            return b[1] - a[1];
        });

        for (int i = 0 ; i < N; i++) {
            int originalIdx = numbers[i][1];
            result += query(1, originalIdx + 1, N - 1, 0, N - 1);
            update(1, originalIdx, 0, N - 1);
        }

        System.out.println(result);
    }
}