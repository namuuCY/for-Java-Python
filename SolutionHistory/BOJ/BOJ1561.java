import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/1561
	// 1561 놀이공원

	static int N; // N 은 20억 이하
	static int M; // M 은 1이상 1만 이하.
	static int[] attractions; // 미리 정적 배열을 정해두면, 캐시히트율을 높여 빠른 조회및 연산가능.
	// List<Integer>는 이에 비하면 데이터를 너무 많이 먹는다
	static int minAttractionTime;
	static int ans;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		inputProcess(br);

		parametricSearch();
		System.out.println(ans);
	}


	private static void inputProcess(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		attractions = new int[M + 1];
		st = new StringTokenizer(br.readLine());

		minAttractionTime = Integer.MAX_VALUE;
		for (int i = 1; i <= M; i++) {
			attractions[i] = Integer.parseInt(st.nextToken());
			minAttractionTime = Math.min(minAttractionTime, attractions[i]);
		}
	}

	private static void parametricSearch() {
		long startTime = 0;
		long endTime = (long) (N + 1) * (long) minAttractionTime;

		while (startTime <= endTime) {
			long midTime = (startTime + endTime) / 2;
			int compareResult = entryCompare(midTime);
			if (compareResult == 0) return;
			if (compareResult < 0) {
				endTime = midTime - 1;
			} else {
				startTime = midTime + 1;
			}
		}
	}

	private static int entryCompare(long time) {
		long current = lastEntries(time);

		if (N <= current) {
			return -1;
		}

		for (int i = 1; i <= M; i++) {
			if (time % attractions[i] != 0) continue;
			current ++;
			if (current == N) {
				ans = i;
				return 0;
			}
		}

		return 1;
	}

	private static long lastEntries(long time) {
		long sum = 0;
		for (int i = 1; i <= M; i++) {
			sum += (time / attractions[i]);
			sum += (time % attractions[i] == 0) ? 0 : 1;
		}
		return sum;
	}
	// 여기까지 문제 : 파라메트릭 서치를 명확히 하지 않음.
	// 언제 시간인지를 확인하는 것에만 사용해야함. (지금은 두 개의 작업이 이뤄지고 있음.)
}