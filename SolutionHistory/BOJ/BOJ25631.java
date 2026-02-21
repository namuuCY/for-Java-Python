import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/25631

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());

		// 크기별 인형 개수를 저장할 HashMap
		HashMap<Integer, Integer> countMap = new HashMap<>();

		int maxCount = 0;

		for (int i = 0; i < N; i++) {
			int size = Integer.parseInt(st.nextToken());

			// 현재 크기의 인형 개수를 1 증가시킴
			int currentCount = countMap.getOrDefault(size, 0) + 1;
			countMap.put(size, currentCount);

			// 전체 중 가장 많이 등장한 빈도수 업데이트
			if (currentCount > maxCount) {
				maxCount = currentCount;
			}
		}

		// 최소로 남는 인형의 개수는 가장 많이 중복된 인형의 개수와 같음
		System.out.println(maxCount);
	}
}