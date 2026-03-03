import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/16918


	static int R, C, N;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());

		char[][] map = new char[R][C];
		for (int i = 0; i < R; i++) {
			map[i] = br.readLine().toCharArray();
		}

		if (N == 1) {
			printMap(map);
		} else if (N % 2 == 0) {
			printFullBombs();
		} else {
			// 3초 후 상태 (초기 폭탄 폭발)
			char[][] time3Map = explode(map);

			if (N % 4 == 3) {
				printMap(time3Map);
			} else if (N % 4 == 1) {
				// 5초 후 상태 (3초 상태의 폭탄들이 폭발)
				char[][] time5Map = explode(time3Map);
				printMap(time5Map);
			}
		}
	}

	// 주어진 폭탄 상태(oldMap)가 폭발한 후의 새로운 맵을 반환하는 메서드
	static char[][] explode(char[][] oldMap) {
		char[][] newMap = new char[R][C];

		// 일단 모든 칸을 폭탄으로 채움
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				newMap[i][j] = 'O';
			}
		}

		// oldMap에서 폭탄이었던 곳과 그 인접한 곳을 파괴(빈칸으로 만듦)
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (oldMap[i][j] == 'O') {
					newMap[i][j] = '.'; // 자기 자신 파괴

					// 4방향 파괴
					for (int d = 0; d < 4; d++) {
						int nx = i + dx[d];
						int ny = j + dy[d];

						if (nx >= 0 && nx < R && ny >= 0 && ny < C) {
							newMap[nx][ny] = '.';
						}
					}
				}
			}
		}
		return newMap;
	}

	// 맵 출력 메서드
	static void printMap(char[][] map) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < R; i++) {
			sb.append(map[i]).append('\n');
		}
		System.out.print(sb);
	}

	// 꽉 찬 폭탄 맵 출력 메서드
	static void printFullBombs() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				sb.append('O');
			}
			sb.append('\n');
		}
		System.out.print(sb);
	}
}