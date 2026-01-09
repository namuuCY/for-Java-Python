import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/2243
    // 사탕 상자

    // 1 B     : 꺼낼 사탕의 순위 (쿼리)
    // 2 B 양수 : 맛 B 를 양수개 만큼 넣는다
    // 2 B 음수 : 맛 B 를 음수개 만큼 뺀다

    // 맨 처음에는 빈 사탕상자에서 시작한다고 가정하며,
    // 사탕의 총 개수는 2,000,000,000을 넘지 않는다. 또한 없는 사탕을 꺼내는 경우와 같은 잘못된 입력은 주어지지 않는다.
    // 문제는 앞에서 몇번째여야 하는가? 쿼리를 어떻게 넣을건가?
    // 앞에서 N번째를 어떻게 확인하냐? 앞에서 4번째라고 한다면
    // 아 이걸 이분탐색으로 하나?

    static int MAX_CANDY_COUNT = 1_000_002;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

        int queryCount = Integer.parseInt(br.readLine().strip());
        int treeHeight = (int) Math.ceil(Math.log(MAX_CANDY_COUNT) / Math.log(2)) + 1;
        int treeSize = (1 << treeHeight);

        int[] segmentTree = new int[treeSize];

        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        while (queryCount -- > 0) {
            st = new StringTokenizer(br.readLine());
            String command = st.nextToken();

            if ("2".equals(command)) {
                int targetCandyIdx = Integer.parseInt(st.nextToken()) - 1;
                int diffVal = Integer.parseInt(st.nextToken());

                updateTree(segmentTree, 1, targetCandyIdx, diffVal, 0, MAX_CANDY_COUNT);
            } else {
                int targetCandyOrder = Integer.parseInt(st.nextToken());
                int indexOfAnswer = binarySearch(segmentTree, targetCandyOrder);
                updateTree(segmentTree, 1, indexOfAnswer, -1, 0, MAX_CANDY_COUNT);

                sb.append(indexOfAnswer + 1);
                sb.append("\n");
            }
        }

        System.out.println(sb);
    }

    private static void updateTree(int[] tree, int nodeIdx, int targetIdx, int diffVal, int start, int end) {
        if (start == end) {
            if (start == targetIdx) tree[nodeIdx] += diffVal;
            return;
        }
        if (targetIdx < start || end < targetIdx) return;
        updateTree(tree, nodeIdx * 2, targetIdx, diffVal, start, (start + end) / 2 );
        updateTree(tree, nodeIdx * 2 + 1, targetIdx, diffVal, (start + end) / 2 + 1, end);
        tree[nodeIdx] = tree[nodeIdx * 2] + tree[nodeIdx * 2 + 1];
    }

    private static int query(int[] tree, int nodeIdx, int left, int right, int start, int end) {
        if (right < start || end < left) return 0;
        if (left <= start && end <= right) return tree[nodeIdx];
        int leftCount = query(tree, nodeIdx * 2, left, right, start, (start + end) / 2);
        int rightCount = query(tree, nodeIdx * 2 + 1, left, right, (start + end) / 2 + 1, end);
        return leftCount + rightCount;
    }

    private static int binarySearch(int[] tree, int targetOrder) {
        int start = 0;
        int end = MAX_CANDY_COUNT;
        int ans = 0;
        while (start <= end) {
            int midIdx = (start + end) / 2;
            int queriedOrder = query(tree, 1, 0, midIdx, 0, MAX_CANDY_COUNT);
            if (queriedOrder >= targetOrder) {
                ans = midIdx;
                end = midIdx - 1;
            } else {
                start = midIdx + 1;
            }
        }
        return ans;
    }

}