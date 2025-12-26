import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/1306
	// 1306 달려라 홍준

	static int N;
	static int M;

	static int[] bright;
	static int[] tree;

	static void init(int node, int start, int end) {
		if (start == end) {
			tree[node] = bright[start];
			return;
		}
		init(node * 2, start, (start + end) / 2);
		init(node * 2 + 1, (start + end) / 2 + 1, end);
		tree[node] = Math.max(tree[node * 2] , tree[node * 2 + 1]);
	}

	static int query(int node, int left, int right, int start, int end) {
		if (right < start || end < left) return Integer.MIN_VALUE;
		if (left <= start && end <= right) return tree[node];
		int leftMax = query(node * 2, left, right, start, (start + end) / 2);
		int rightMax = query(node * 2 + 1, left, right, (start + end) / 2 + 1, end);
		return Math.max(leftMax, rightMax);
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		int height = (int) Math.ceil(Math.log(N) / Math.log(2)) + 1;
		int size = (1 << height);

		tree = new int[size];
		bright = new int[N];

		st = new StringTokenizer(br.readLine());

		for (int i = 0 ; i < N; i++) {
			bright[i] = Integer.parseInt(st.nextToken());
		}

		init(1, 0, N-1);

		// M번째 칸에서 뛰기 시작해서 N-M+1번째 칸에서 멈춘다
		//
		// 1 인덱스에서 (1, m, 2m - 1) ~ (n - m + 2 , n-m+1, )
		// 0 인덱스 이므로 (0, m-1, 2m - 2) ~ (n - 1 - (2m-1)= n - 2m , n - 1)
		// x + (2m - 2) = n - 1;
		// x = n - 2m + 1

		StringBuilder sb = new StringBuilder();

		for (int i = 0 ; i <= N - 2*M + 1 ; i++) {
			sb.append(query(1, i, i + (2* M - 2), 0, N - 1));
			sb.append(" ");
		}

		bw.write(sb.toString());

		bw.flush();

	}

}