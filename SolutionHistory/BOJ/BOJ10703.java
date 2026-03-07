import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/10703

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int R = Integer.parseInt(st.nextToken());
		int S = Integer.parseInt(st.nextToken());

		char[][] grid = new char[R][S];
		for (int i = 0; i < R; i++) {
			grid[i] = br.readLine().toCharArray();
		}

		// 1. 전체 유성이 떨어질 수 있는 최소 거리(최댓값으로 초기화)
		int minDrop = R;

		for (int c = 0; c < S; c++) {
			int bottomX = -1; // 해당 열의 가장 아래쪽 유성 위치
			int topHash = R;  // 해당 열의 가장 위쪽 땅 위치

			for (int r = 0; r < R; r++) {
				if (grid[r][c] == 'X') {
					bottomX = Math.max(bottomX, r);
				} else if (grid[r][c] == '#') {
					if (topHash == R) { // 땅을 처음 만난 경우 (가장 위쪽 땅)
						topHash = r;
					}
				}
			}

			// 해당 열에 유성이 존재한다면 낙하 거리 계산 및 갱신
			if (bottomX != -1) {
				minDrop = Math.min(minDrop, topHash - bottomX - 1);
			}
		}

		// 2. 유성을 minDrop 만큼 아래로 이동 (아래 행부터 탐색)
		for (int r = R - 1; r >= 0; r--) {
			for (int c = 0; c < S; c++) {
				if (grid[r][c] == 'X') {
					grid[r][c] = '.';               // 원래 있던 자리는 공기로 변경
					grid[r + minDrop][c] = 'X';     // 새로운 위치에 유성 배치
				}
			}
		}

		// 3. 결과 출력 (BufferedWriter를 사용해 출력 속도 최적화)
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		for (int i = 0; i < R; i++) {
			bw.write(grid[i]);
			bw.write('\n');
		}
		bw.flush();
		bw.close();
		br.close();
	}
}
