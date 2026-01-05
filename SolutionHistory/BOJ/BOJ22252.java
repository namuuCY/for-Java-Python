import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/22252
	// 22252 정보 상인 호석

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int queryCount = Integer.parseInt(br.readLine().strip());
		Map<String, PriorityQueue<Integer>> informations = new HashMap<>();
		long sumOfCosts = 0;

		while (queryCount -- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine().strip());

			String command = st.nextToken().strip();
			String sellerName = st.nextToken().strip(); // 혹시몰라 공백을 제거

			if ("1".equals(command)) {
				int countOfInformation = Integer.parseInt(st.nextToken());
				PriorityQueue<Integer> pq = informations.computeIfAbsent(
						sellerName,
						ignored -> new PriorityQueue<>(Collections.reverseOrder())
				);
				while (countOfInformation -- > 0) {
					pq.add(Integer.parseInt(st.nextToken()));
				}
			} else {
				int demandSize = Integer.parseInt(st.nextToken());

				if (!informations.containsKey(sellerName)) continue;
				PriorityQueue<Integer> pq = informations.get(sellerName);

				int minInformationSize = Math.min(pq.size(), demandSize);
				while (minInformationSize -- > 0) {
					sumOfCosts += pq.poll();
				}
			}
		}

		System.out.println(sumOfCosts);
	}

//    private static void upsertInformation(PriorityQueue<Integer> current, StringTokenizer st) {
//
//    }
//
//    private static void buyInformation(PriorityQueue<Integer> current, StringTokenizer st) {
//
//    }


}