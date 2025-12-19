import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/1507
	// 1507 궁금한 민호

	static int N;

	static int[] rank;
	static int[] root;

	static int find(int value) {

		if (value != root[value]) {
			root[value] = find(root[value]);
		}
		return root[value];
	}

	static boolean union(int u, int v) {
		int rootU = find(u);
		int rootV = find(v);

		if (rootU == rootV) return false; // 이미 같은 루트를 가진 경우 -> union임
		int rankU = rank[rootU];
		int rankV = rank[rootV];

		if (rankU > rankV) {
			root[rootV] = rootU;
		} else {
			root[rootU] = rootV;
			if (rankU == rankV) {
				rank[rootV] ++;
			}
		}
		return true;
	}

	PriorityQueue<int[]> pq = new PriorityQueue<>();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		rank = new int[N + 1];
		root = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

		}

		// MST를 찾고

		// 비교해서 더 추가판단.

	}

}