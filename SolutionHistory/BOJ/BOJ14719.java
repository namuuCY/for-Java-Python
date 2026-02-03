import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/14719
	// 빗물

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int H = Integer.parseInt(st.nextToken());
		int W = Integer.parseInt(st.nextToken());

		int[] blocks = new int[W];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < W; i++) {
			blocks[i] = Integer.parseInt(st.nextToken());
		}

		int totalWater = 0;

		for (int i = 1; i < W - 1; i++) {
			int leftMax = 0;
			int rightMax = 0;

			for (int j = 0; j < i; j++) {
				leftMax = Math.max(leftMax, blocks[j]);
			}

			for (int j = i + 1; j < W; j++) {
				rightMax = Math.max(rightMax, blocks[j]);
			}

			int minWall = Math.min(leftMax, rightMax);

			if (minWall > blocks[i]) {
				totalWater += (minWall - blocks[i]);
			}
		}

		System.out.println(totalWater);
	}
}