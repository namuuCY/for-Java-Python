import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/19951
	// 태상이의 훈련소 생활

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int groundSize = Integer.parseInt(st.nextToken());
		int querySize = Integer.parseInt(st.nextToken());
		// 1-idx
		int[] heights = new int[groundSize + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1 ; i <= groundSize ; i++) {
			heights[i] = Integer.parseInt(st.nextToken());
		}

		// 각각의 ground에 대해서 쿼리 하나하나씩 작업한다면 시간복잡도 최대 100억. 시간 내 불가
		// prefixSum 도입
		int[] prefixes = new int[groundSize + 2];

		while (querySize -- > 0) {
			st = new StringTokenizer(br.readLine());
			int startIdx = Integer.parseInt(st.nextToken());
			int endIdx = Integer.parseInt(st.nextToken()) + 1;
			int diff = Integer.parseInt(st.nextToken());

			prefixes[startIdx] += diff;
			prefixes[endIdx] -= diff;
		}

		int currentDiff = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < groundSize + 1; i ++) {
			currentDiff += prefixes[i];
			heights[i] += currentDiff;
			sb.append(heights[i]);
			sb.append(" ");
		}

		System.out.println(sb);
	}
}