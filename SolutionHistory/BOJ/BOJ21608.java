import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/21608
	// 21608 상어 초등학교

//    비어있는 칸 중 인접(행 또는 열의 인덱스가 1개만 차이, 둘다는 안됨)
//    동일 만족도가 여러 개이면 인접 칸중 비어있는 칸이 많은 케이스
//    비어있는 칸의 개수마저 동일하다면, 행이 가장 작도록
//    열의 번호가 가장 작도록

	static int[] dr = new int[]{1, 0, -1, 0};
	static int[] dc = new int[]{0, 1, 0, -1};

	static class Score implements Comparable<Score> {
		int favoriteCount;
		int vacantCount;
		int row;
		int column;

		public Score(int favoriteCount, int vacantCount, int row, int column) {
			this.favoriteCount = favoriteCount;
			this.vacantCount = vacantCount;
			this.row = row;
			this.column = column;
		}

		public int compareTo(Score that) {
			if (this.favoriteCount != that.favoriteCount) return that.favoriteCount - this.favoriteCount;
			if (this.vacantCount != that.vacantCount) return that.vacantCount - this.vacantCount;
			if (this.row != that.row) return this.row - that.row;
			return this.column - that.column;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine().strip());

		List<Integer> order = new ArrayList<>();
		int[][] favorites = new int[N * N + 1][4];
		int studentCount = N * N;

		while (studentCount -- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine().strip());
			int currentStudentNumber = Integer.parseInt(st.nextToken());
			order.add(currentStudentNumber);
			for (int column = 0 ; column < 4; column++) {
				favorites[currentStudentNumber][column] = Integer.parseInt(st.nextToken());
			}
		}

		int[][] studentsGrid = new int[N][N];

		int firstStudent = order.get(0);
		studentsGrid[1][1] = firstStudent;

		for (int index = 1; index < N * N; index++) {
			int currentStudent = order.get(index);
			Score currentScore = simulateStudent(N, studentsGrid, favorites[currentStudent]);
			studentsGrid[currentScore.row][currentScore.column] = currentStudent;
		}

		// 2중 for문 돌면서 학생들 만족도 계산
		int satisfyScore = 0;

		for (int r = 0; r < N; r++) {
			for (int c = 0 ; c < N; c++) {
				satisfyScore += calculateFinalScore(studentsGrid, favorites[studentsGrid[r][c]], r, c, N);
			}
		}

		System.out.println(satisfyScore);
	}

	private static Score simulateStudent(int N, int[][] studentsGrid, int[] favorites) {
		PriorityQueue<Score> pq = new PriorityQueue<>();
		for (int r = 0 ; r < N; r ++) {
			for (int c = 0 ; c < N; c++) {
				if (studentsGrid[r][c] != 0) continue;
				// score 계산
				Score currentScore = calculateScore(N, studentsGrid, favorites, r, c);
				pq.add(currentScore);
			}
		}
		return pq.poll();
	}


	private static Score calculateScore(int N, int[][] studentsGrid, int[] favorites, int row, int column) {
		int favoriteCount = 0;
		int vacantCount = 0;
		for (int dir = 0 ; dir < 4; dir ++) {
			int nextR = row + dr[dir];
			int nextC = column + dc[dir];
			if (isOutOfBounds(N, nextR, nextC)) continue;
			if (studentsGrid[nextR][nextC] == 0) {
				vacantCount ++;
			} else {
				for (int i = 0 ; i< 4; i++) {
					if (favorites[i] != studentsGrid[nextR][nextC]) continue;
					favoriteCount++;
					break;
				}
			}
		}
		return new Score(favoriteCount, vacantCount, row, column);
	}

	private static boolean isOutOfBounds(int max, int row, int column) {
		return row < 0 || row >= max || column < 0 || column >= max;
	}

	private static int calculateFinalScore(int[][] studentsGrid, int[] favorites, int row, int column, int N) {
		int favoriteCount = 0;
		for (int dir = 0 ; dir < 4; dir ++) {
			int nextR = row + dr[dir];
			int nextC = column + dc[dir];
			if (isOutOfBounds(N, nextR, nextC)) continue;
			for (int i = 0 ; i< 4; i++) {
				if (favorites[i] != studentsGrid[nextR][nextC]) continue;
				favoriteCount++;
				break;
			}
		}
		switch (favoriteCount) {
			case 4 : return 1000;
			case 3 : return 100;
			case 2 : return 10;
			case 1 : return 1;
			default : return 0;
		}
	}
}