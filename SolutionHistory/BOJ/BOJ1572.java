import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/1572
	// 중앙값

	// 중앙값을 구하도록 세그먼트 트리가 형성이 될 수 있나?
	// 모든 수를 다 받고,

	// 앞에서 부터 K개 까지 1 업데이트
	// 전체에서 세그먼트 트리로 중앙값 찾기

	// 찾은 이후 업데이트 : 첫번째 1 제거, K+1 번째 1 추가
	// 다시 세그먼트 트리로 중앙값 찾기

	static int N, K;

	static class Record implements Comparable<Record> {
		int number;
		int seqIdx;

		Record(int number, int seqIdx) {
			this.number = number;
			this.seqIdx = seqIdx;
		}

		public int compareTo(Record that) {
			if (this.number != that.number) return Integer.compare(this.number, that.number);
			return Integer.compare(this.seqIdx, that.seqIdx);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().strip());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 먼저 다 받고, 정렬 및 순서 매핑

		Record[] seq = parseSequence(br);
		Arrays.sort(seq);
		// seq는 이제 sorted index를 찾으면 number, 원래 숫자를 알 수 있음

		int[] seqIdxToSortedIdx = new int[N];
		for (int sortedIdx = 0 ; sortedIdx < N; sortedIdx++ ) {
			Record current = seq[sortedIdx];
			seqIdxToSortedIdx[current.seqIdx] = sortedIdx;
		}

//        System.out.println( Arrays.toString( seqIdxToSortedIdx ) );

		int[] tree = initSegmentTree();

		boolean[] visited = new boolean[N];
		for (int i = 0 ; i < K; i++) {
			visited[seqIdxToSortedIdx[i]] = true;
		}
		fillTree(tree, visited, 1, 0, N - 1);
		int middleIdx = (K + 1) / 2;

		long ans = 0;
		int sortedMiddleIdx = queryIdx(tree, 1, middleIdx, 0, N - 1);
		int middleNumber = seq[sortedMiddleIdx].number;
//        System.out.println("현재 추가되는 숫자 : " + middleNumber);
		ans += middleNumber;
		// 넣을때 : N 번째 쿼리 숫자 -> 이게 정렬된 거의 몇 번쨰 숫자임? 이 필요 (트리 업데이트 해야하나까)
		// 뺄 때 : N 번째 쿼리 숫자를 빼야 -> 이게 정렬된 거의 몇 번째 숫자임? 이 필요 (트리 업데이트 해야하나까)
		for (int i = K ; i < N; i++) {
			int removeSortedIdx = seqIdxToSortedIdx[i - K];
			update(tree, 1, removeSortedIdx, 0, 0, N - 1);
			int insertSortedIdx = seqIdxToSortedIdx[i];
			update(tree, 1, insertSortedIdx, 1, 0, N - 1);

			int currentSortedMiddleIdx = queryIdx(tree, 1, middleIdx, 0, N - 1);
			int currentMiddleNumber = seq[currentSortedMiddleIdx].number;
//            System.out.println("현재 추가되는 숫자 : " + currentMiddleNumber);
			ans += currentMiddleNumber;
		}

		// 중앙값 찾을 때 : (중앙값의 리프 노드의 인덱스를 찾음) -> N 번째에 있는 정렬"된" 숫자가 뭐임?

		System.out.println(ans);

	}

	private static Record[] parseSequence(BufferedReader br) throws IOException {
		Record[] seq = new Record[N];
		for (int i = 0 ; i < N; i++) {
			seq[i] = new Record(
					Integer.parseInt(br.readLine()),
					i
			);
		}
		return seq;
	}

	private static int[] initSegmentTree() {
		int treeSize = 1 << (1 + (int) Math.ceil(Math.log(N) / Math.log(2)));
		return new int[treeSize];
	}

	private static void fillTree(int[] tree, boolean[] visited, int nodeIdx, int start, int end) {
		if (start == end) {
			if (visited[start]) tree[nodeIdx] = 1;
			return;
		}
		fillTree(tree, visited, nodeIdx * 2, start, (start + end) / 2);
		fillTree(tree, visited, nodeIdx * 2 + 1, (start + end) / 2 + 1, end);
		tree[nodeIdx] = tree[nodeIdx * 2] + tree[nodeIdx * 2 + 1];
	}

	private static int queryIdx(int[] tree, int nodeIdx, int targetValue, int start, int end) {
		if (start == end) return start;
		int mid = (start + end) / 2;
		int leftCount = tree[nodeIdx * 2];
		if (targetValue <= leftCount) {
			return queryIdx(tree, nodeIdx * 2, targetValue, start, mid);
		} else {
			return queryIdx(tree, nodeIdx * 2 + 1, targetValue - leftCount, mid + 1, end);
		}
	}

	private static void update(int[] tree, int nodeIdx, int targetIdx, int value, int start, int end) {
		if (start == end) {
			if (start == targetIdx) tree[nodeIdx] = value;
			return;
		}
		if (targetIdx < start || end < targetIdx) return;
		update(tree, nodeIdx * 2, targetIdx, value, start, (start + end) / 2);
		update(tree, nodeIdx * 2 + 1, targetIdx, value, (start + end) / 2 + 1, end);
		tree[nodeIdx] = tree[nodeIdx * 2] + tree[nodeIdx * 2 + 1];
	}

}