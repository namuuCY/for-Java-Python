import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/27940
	// 가지 산사태

	public static void main(String[] args) throws Exception {
		// 빠른 입력을 위한 BufferedReader 사용
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken()); // 농장의 층수
		int M = Integer.parseInt(st.nextToken()); // 비가 오는 횟수
		long K = Long.parseLong(st.nextToken());  // 버틸 수 있는 한계치

		long totalRainAtLevel1 = 0;
		boolean collapsed = false;

		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			int t = Integer.parseInt(st.nextToken()); // 1~t층까지 비가 옴
			int r = Integer.parseInt(st.nextToken()); // 빗물의 양

			// 어떤 층이 무너지려면, 가장 비를 많이 맞는 1층이 가장 먼저 무너집니다.
			// (1층은 t가 무엇이든 항상 비를 맞기 때문)
			totalRainAtLevel1 += r;

			// 아직 결과를 출력하지 않았고, 1층의 누적량이 K를 초과했다면
			if (!collapsed && totalRainAtLevel1 > K) {
				System.out.println(i + " " + 1);
				collapsed = true;
				// 이후의 입력은 결과에 영향을 주지 않지만, 입력을 모두 읽어줘야 함에 유의하거나
				// 바로 종료해도 무방합니다 (여기서는 안전하게 break 대신 flag 사용 후 종료 가능)
				return;
			}
		}

		// 모든 비가 내릴 때까지 무너지지 않은 경우
		if (!collapsed) {
			System.out.println("-1");
		}
	}
}