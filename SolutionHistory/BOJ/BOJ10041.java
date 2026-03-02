import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/10041


	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int W = Integer.parseInt(st.nextToken());
		int H = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());

		// 첫 번째 관광지 위치 입력
		st = new StringTokenizer(br.readLine());
		int currX = Integer.parseInt(st.nextToken());
		int currY = Integer.parseInt(st.nextToken());

		long totalDistance = 0;

		for (int i = 1; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int nextX = Integer.parseInt(st.nextToken());
			int nextY = Integer.parseInt(st.nextToken());

			int dx = nextX - currX;
			int dy = nextY - currY;

			// 두 변화량의 곱이 양수면 부호가 같은 것 (대각선 이동 가능)
			if (dx * dy > 0) {
				// 대각선 이동을 포함한 최단 거리는 두 차이 중 큰 값
				totalDistance += Math.max(Math.abs(dx), Math.abs(dy));
			} else {
				// 부호가 다르거나 어느 하나가 0이면 대각선 이동 불가 (맨해튼 거리)
				totalDistance += Math.abs(dx) + Math.abs(dy);
			}

			// 현재 위치 업데이트
			currX = nextX;
			currY = nextY;
		}

		System.out.println(totalDistance);
	}
}