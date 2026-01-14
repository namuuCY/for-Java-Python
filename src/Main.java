import java.io.*;
import java.util.StringTokenizer;

public class Main {
    // https://www.acmicpc.net/problem/1168
    // 요세푸스 문제 2

    // - 세그먼트 트리의 리프노드를 전부 다 1로 초기화
    //- 각각의 트리에는 구간별로 총 몇명의 병사가 있는지를 확인
    //- t번째 병사를 찾는 try 에서 일어나는 일
    //- t번째 병사를 찾음 -> 해당 병사의 리프노드를 0으로, 트리를 업데이트
    //- 현재 남아있는 병사는 count로 확인가능
    //- case1) (count 가 k보다 클때는 아래와 같은 방식을 적용)
    //- 현재 t번째 병사의 왼쪽구간, 오른쪽 구간을 나누어서
    //- 오른쪽 구간의 병사 수를 확인, t보다 작다면 이분탐색으로 몇번째에 있는지를 확인
    //- 왼쪽 구간의 병사 수를 확인 k+1 번째 병사를 찾음
    //- case 2) (count 가 k보다 작을때는 아래와 같은 방식을 적용)
    //- count module k 를 통해서 몇 번째 병사를 선택해야 하는지 확인
    //- case 1)의 방식을 응용
    static int N;
    static int K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        int[] segmentTree = initSegmentTree();

        StringBuilder sb = new StringBuilder();
        sb.append("<");
        // 출력시 + 1 해주어야 한다.
        int cursor = 0;
        for (int i = N; i >= 1 ; i--) {
            int targetNumber = (i >= K)
                    ? K
                    : (K % i == 0) ? i : K % i;
            cursor = findSoldierIdxAndUpdateTree(segmentTree, cursor, targetNumber);
            sb.append((cursor + 1));
            if (i != 1) {
                sb.append(", ");
            }
        }
        sb.append(">");

        System.out.println(sb);
    }

    private static int[] initSegmentTree() {
        int treeSize = (1 << ((int) (Math.ceil(Math.log(N) / Math.log(2))) + 1));
        int[] tree = new int[treeSize];
        initInternal(tree, 1, 0, N - 1);
        return tree;
    }

    private static void initInternal(int[] tree, int nodeIdx, int start, int end) {
        if (start == end) {
            tree[nodeIdx] = 1;
            return;
        }
        initInternal(tree, nodeIdx * 2, start, (start + end) / 2);
        initInternal(tree, nodeIdx * 2 + 1, (start + end) / 2 + 1, end);
        tree[nodeIdx] = tree[nodeIdx * 2] + tree[nodeIdx * 2 + 1];
    }

    private static void removeSoldierByUpdateTree(int[] tree, int nodeIdx, int targetIdx, int start, int end) {
        if (start == end) {
            if (start == targetIdx) {
                tree[nodeIdx] = 0;
            }
            return;
        }
        if (end < targetIdx || start > targetIdx) return;
        removeSoldierByUpdateTree(tree, nodeIdx * 2, targetIdx, start, (start + end) / 2);
        removeSoldierByUpdateTree(tree, nodeIdx * 2 + 1, targetIdx,  (start + end) / 2 + 1 , end);
        tree[nodeIdx] = tree[nodeIdx * 2] + tree[nodeIdx * 2 + 1];
    }

    private static int queryIndexing(int[] tree, int targetCount, int nodeIdx, int start, int end) {
        if (start == end) return start;
        int mid = (start + end) / 2;
        // 왼쪽 먼저 계산 후, 넘는다고 판단되면
        int leftSoldiers = tree[nodeIdx * 2];
        if (targetCount <= leftSoldiers) {
            return queryIndexing(tree, targetCount, nodeIdx * 2, start, mid);
        } else {
            return queryIndexing(tree, targetCount - leftSoldiers, nodeIdx * 2 + 1, mid + 1, end);
        }
    }

    private static int queryCount(int[] tree, int nodeIdx, int left, int right, int start, int end) {
        if (right < start || end < left) return 0;
        if (left <= start && end <= right) return tree[nodeIdx];
        int mid = (start + end) / 2;
        return queryCount(tree, nodeIdx * 2, left, right, start, mid)
                + queryCount(tree, nodeIdx * 2 + 1, left, right, mid + 1, end);
    }

    private static int findSoldierIdxAndUpdateTree(int[] tree, int cursor, int targetCount) {
        int rightSegmentSoldiers = queryCount(tree, 1, cursor, N - 1, 0, N - 1);
        int leftSegmentSoldiers = tree[1] - rightSegmentSoldiers; // 왼쪽에 있는 생존 병력
        if (rightSegmentSoldiers >= targetCount) {
            int targetIdx = queryIndexing(tree, leftSegmentSoldiers + targetCount, 1, 0, N - 1);
            removeSoldierByUpdateTree(tree, 1, targetIdx, 0, N-1);
            return targetIdx;
        }

        int targetIdx = queryIndexing(tree, targetCount - rightSegmentSoldiers, 1, 0, N - 1);
        removeSoldierByUpdateTree(tree, 1, targetIdx, 0, N - 1);

        return targetIdx;
    }

}