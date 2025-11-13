import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/6549)
    // BOJ 6549 히스토그램에서 가장 큰 직사각형

    // init할 때 마다 각각의 최소 높이를 가지는 tree를 init하고, 쿼리는 넓이로

    // 구간 높이의 최솟값 init



    // 풀이 찾아서 풀었음.

    // 1) tree에는 최소 높이를 저장하는 index가 필요
    // 쿼리 할 때 분할 정복이 필요
    // 가장 작은 index
    static void init(int[] origin, int[] tree, int nodeIdx, int start, int end) {
        if (start == end) {
            tree[nodeIdx] = start;
            return;
        }
        init(origin, tree, nodeIdx*2, start, (start + end) / 2);
        init(origin, tree, nodeIdx*2 + 1, (start + end) / 2 + 1, end);
        int lMinVal = origin[tree[nodeIdx * 2]];
        int rMinVal = origin[tree[nodeIdx * 2 + 1]];
        tree[nodeIdx] = lMinVal <= rMinVal ? tree[nodeIdx * 2] : tree[nodeIdx * 2 + 1];
    }


    // 최소 공통 높이의 인덱스만 찾기
    static int query(int[] origin, int[] tree, int nodeIdx, int start, int end, int left, int right) {
        if (end < left || right < start) return -1;
        if (left <= start && end <= right) {
            return tree[nodeIdx];
        }

        int lIdx = query(origin, tree, nodeIdx * 2 , start, (start + end) / 2, left, right);
        int rIdx = query(origin, tree, nodeIdx * 2 + 1, (start + end) / 2 +1 , end, left, right);

        if (lIdx == -1) return rIdx;
        if (rIdx == -1) return lIdx;

        return origin[lIdx] <= origin[rIdx] ? lIdx : rIdx;
    }

    static long cal(int[] origin, int[] tree, int start, int end) {
        int N = origin.length;
        int len = end - start + 1;
        int minHeightIdx = query(origin, tree, 1, 0,N - 1, start, end);

        long currentMaxArea = (long) len * (long) origin[minHeightIdx];

        // 왼쪽의 사각형의 계산 필요할 경우
        if (start < minHeightIdx) {
            long area = cal(origin, tree, start, minHeightIdx - 1);
            currentMaxArea = Math.max(currentMaxArea, area);
        }

        // 오른쪽의 사각형의 계산 필요할 경우
        if (minHeightIdx < end) {
            long area = cal(origin, tree, minHeightIdx + 1, end);
            currentMaxArea = Math.max(currentMaxArea, area);
        }

        return currentMaxArea;
    }



    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringBuilder sb = new StringBuilder();
        while (true) {
            // 0 이 나오면 break

            String[] input = br.readLine().split(" ");
            int size = input.length;
            if (size == 1) break;

            int height = (int) Math.ceil(Math.log(size) / Math.log(2));
            int treeSize = (1 << height + 1);

            int[] origin = new int[size];
            int[] tree = new int[treeSize];

            for (int i = 1 ; i < size ; i++) {
                origin[i - 1] = Integer.parseInt(input[i]);
            }
            init(origin, tree, 1, 0, size - 1);

            long ans = cal(origin, tree, 0, size -1);
            sb.append(ans + "\n");
        }
        bw.write(sb.toString());
        bw.flush();

    }
}
