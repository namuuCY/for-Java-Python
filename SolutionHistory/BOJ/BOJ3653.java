import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/3653
	// 3653 영화 수집


	static int[] tree;
	static int[] pos = new int[100001];

	// 0~ m-1 까지 0이고, m, m+1, ... m+n-1 까지 1
	static void init(int node, int start, int end, int m, int n) {
		if (start == end) {
			if ((m <= start) && (start <= m + n - 1)) {
				tree[node] = 1;
			}
			return;
		}
		init(node * 2 , start, (start + end) / 2, m, n);
		init(node * 2 + 1 , (start + end) / 2 + 1, end, m, n);
		tree[node] = tree[node * 2] + tree[node * 2 + 1];
	}

	static int query(int node, int left, int right, int start, int end) {
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) return tree[node];
		return query(node * 2, left, right, start, (start + end) / 2)
				+ query(node * 2 + 1, left, right, (start + end) / 2 + 1, end);
	}

	static void update(int node, int targetIdx, int targetVal, int start, int end) {
		if (start == end) {
			if (start == targetIdx) {
				tree[node] = targetVal;
			}
			return;
		}
		if (targetIdx < start || end < targetIdx) return;

		update(node *2, targetIdx, targetVal, start,  (start+end) / 2 );
		update(node *2 + 1, targetIdx, targetVal,(start+end) / 2 + 1, end);
		tree[node] = tree[node * 2] + tree[node * 2 + 1];
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int t = Integer.parseInt(br.readLine());

		int height = (int) Math.ceil(Math.log(200002) / Math.log(2)) + 1;
		int size = (1 << (height));

		tree = new int[size];

		StringBuilder sb = new StringBuilder();

		while (t-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());

			Arrays.fill(tree, 0);
			for (int i = 0 ; i < n ; i++) {
				pos[i] = m + i;
			}

			init(1, 0, n + m - 1 , m, n);
			st = new StringTokenizer(br.readLine());
			for (int nextIdx = m - 1; nextIdx >= 0 ; nextIdx--) {
				int target = Integer.parseInt(st.nextToken()) - 1;
				// 0~ m-1 까지 0이고, m, m+1, ... m+n-1 까지 1 에서 시작
				int currentPos = pos[target];

				sb.append(query(1, 0, currentPos - 1, 0, m + n - 1));
				sb.append(" ");
				update(1, currentPos, 0,  0, m + n - 1);
				update(1, nextIdx, 1,  0, m + n - 1);
				pos[target] = nextIdx;
			}
			sb.append("\n");

		}

		bw.write(sb.toString());
		bw.flush();

	}

}