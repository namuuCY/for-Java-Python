import java.io.*;

public class Main {
	// https://www.acmicpc.net/problem/1029
	// 그림 교환

	// DFS, BFS는 불가능할것 -> N! - 14! 하면 10억초과
	// 그리디 접근 방법? 케이스가 많아서 불가능해보임
	// 플로이드 - 워셜? 얘네는 최단 경로를 출력하므로 불가능해보임
	// 비트마스킹까진 생각났는데... 2^15 = 32000 라 가능해보이고
	// DP[마지막 구매자][여태까지 총 구매자 비트마스킹] 15 * (2 ^ 15) = 50만정도?
	// 밑에서부터 채울거지? 어떻게 채울거야
	// 우선 1부터 채움.

	static int MAX_PRICE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int traderSize = Integer.parseInt(br.readLine());
		int[][] expectedPrice = parseInput(traderSize, br);
		int[][][] DP = new int[MAX_PRICE][traderSize][(1 << traderSize)];
		dynamicProgramming(traderSize, expectedPrice, DP);

		int ans = 0 ;
		for (int i = 0 ; i < MAX_PRICE ; i++) {
			for (int j = 0 ; j < traderSize ; j ++) {
				for (int k = 0 ; k < (1 << traderSize) ; k++) {
					if (ans >= DP[i][j][k]) continue;
					ans = DP[i][j][k];
				}
			}
		}

		System.out.println(ans);
	}

	private static int[][] parseInput(int traderSize, BufferedReader br) throws IOException {
		int ans = 0;

		int[][] expectedPrice = new int[traderSize][traderSize];
		for (int row = 0 ; row < traderSize ; row++) {
			char[] currentInputChars = br.readLine().strip().toCharArray();
			for (int column = 0 ; column < traderSize ; column ++) {
				int currentPrice = (char) currentInputChars[column] - (char) '0';
				expectedPrice[row][column] = currentPrice;
				if (ans < currentPrice) {
					ans = currentPrice;
				}
			}
		}
		MAX_PRICE = ans + 1;
		return expectedPrice;
	}

	private static void dynamicProgramming(int traderSize, int[][] expectedPrice, int[][][] DP) {
		DP[0][0][1] = 1;

		for (int accumulateBitmask = 1 ; accumulateBitmask < (1 << traderSize) ; accumulateBitmask ++) {
			for (int currentTrader = 0; currentTrader < traderSize ; currentTrader ++) {
				if (( (accumulateBitmask >> currentTrader) & 1 ) != 1) continue;

				for (int currentPrice = 0; currentPrice < MAX_PRICE; currentPrice ++) {
					if (DP[currentPrice][currentTrader][accumulateBitmask] == 0) continue;
					int currentLoopCondition = DP[currentPrice][currentTrader][accumulateBitmask];

					for (int nextTrader = 1; nextTrader < traderSize ; nextTrader++) {
						if (( (accumulateBitmask >> nextTrader) & 1 ) == 1) continue;
						int transactionPrice = expectedPrice[currentTrader][nextTrader];
						if (transactionPrice >= currentPrice) {
							int nextBitmask = accumulateBitmask | (1 << nextTrader);
							DP[transactionPrice][nextTrader][nextBitmask] =
									Math.max(
											DP[transactionPrice][nextTrader][nextBitmask],
											currentLoopCondition +1
									);
						}
					}
				}
			}
		}
	}
}