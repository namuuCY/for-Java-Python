import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

public class Main {
	// https://www.acmicpc.net/problem/2887
	// 행성 정렬

	// x 좌표에 대해서 정렬하고, 인접 행성끼리의 간선
	// y 좌표에 대해서 정렬하고, ''                 -> 다만 이때는 이미 있을경우, 더 작은 거로
	// z

	//  좌표는 -109보다 크거나 같고, 109보다 작거나 같은 정수 이니까 int로 두어도 서로 빼도 문제 x
	static int N;

	static class Planet {
		int index;
		int x;
		int y;
		int z;

		Planet(int index, int x, int y, int z) {
			this.index = index;
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	static class Warp implements Comparable<Warp> {
		int start;
		int end;
		int cost;

		Warp(int start, int end, int cost) {
			this.start = start;
			this.end = end;
			this.cost = cost;
		}

		public int compareTo(Warp that) {
			return this.cost - that.cost;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		if (N == 1) {
			System.out.println(0);
			return;
		}

		Planet[] planets = parseInput(br);
		Queue<Warp> priorityQueue = sortAndPushToPriorityQueue(planets);

		int[] parents = new int[N];
		int[] ranks = new int[N];
		for (int i = 0 ; i < N; i++) {
			parents[i] = i;
		}
		int edgeCount = 0;
		int totalCost = 0;
		while (!priorityQueue.isEmpty()) {
			Warp warp = priorityQueue.poll();

			boolean isDisjoint = union(parents, ranks, warp.start, warp.end);
			if (isDisjoint) {
				edgeCount ++;
				totalCost += warp.cost;
				if (edgeCount == N - 1) break;
			}
		}

		System.out.println(totalCost);
	}

	private static Planet[] parseInput(BufferedReader br) throws IOException {
		Planet[] planets = new Planet[N];
		StringTokenizer st;
		for (int i = 0 ; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			planets[i] = new Planet(
					i,
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken())
			);
		}

		return planets;
	}

	private static Queue<Warp> sortAndPushToPriorityQueue(Planet[] planets) {
		Queue<Warp> pq = new PriorityQueue<>();

		Arrays.sort( planets, Comparator.comparingInt( p -> p.x ) );
		for (int i = 0 ; i < N -1 ; i++) {
			pq.add(new Warp(
					planets[i].index,
					planets[i + 1].index,
					planets[i + 1].x - planets[i].x
			));
		}
		Arrays.sort( planets, Comparator.comparingInt( p -> p.y ) );
		for (int i = 0 ; i < N -1 ; i++) {
			pq.add(new Warp(
					planets[i].index,
					planets[i + 1].index,
					planets[i + 1].y - planets[i].y
			));
		}
		Arrays.sort( planets, Comparator.comparingInt( p -> p.z ) );
		for (int i = 0 ; i < N - 1 ; i++) {
			pq.add(new Warp(
					planets[i].index,
					planets[i + 1].index,
					planets[i + 1].z - planets[i].z
			));
		}

		return pq;
	}

	private static int find(int[] parents, int u) {
		if (!(u == parents[u])) {
			parents[u] = find(parents, parents[u]);
		}
		return parents[u];
	}

	private static boolean union(int[] parents, int[] ranks, int u, int v) {
		int parentU = find(parents, u);
		int parentV = find(parents, v);
		if (parentU == parentV) return false;

		int rankU = ranks[parentU];
		int rankV = ranks[parentV];
		if (rankU < rankV) {
			parents[parentU] = parentV;
		} else {
			parents[parentV] = parentU;
			if (rankU == rankV) {
				ranks[parentU] ++;
			}
		}

		return true;
	}



}