import java.io.*;
import java.util.Arrays;

public class Main {
    // https://www.acmicpc.net/problem/1029
    // 그림 교환

    // 방법 2)
    // DP[마지막 방문자][방문한 사람 비트마스킹] = 최소 가격


    static int MAX_PRICE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int traderSize = Integer.parseInt(br.readLine());
        int[][] expectedPrice = parseInput(traderSize, br);
        int[][] DP = new int[traderSize][(1 << traderSize)];
        for (int i = 0; i < traderSize; i++) {
            Arrays.fill( DP[i], 10);
        }
        dynamicProgramming(traderSize, expectedPrice, DP);

        int ans = 0 ;
        for (int j = 0 ; j < traderSize ; j ++) {
            for (int k = 1 ; k < (1 << traderSize) ; k++) {
                if (DP[j][k] >= 10) continue;
                int currentTraders = Integer.bitCount(k);
                if (ans >= currentTraders) continue;
                ans = currentTraders;
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

    private static void dynamicProgramming(int traderSize, int[][] expectedPrice, int[][] DP) {
        DP[0][1] = 0;

        for (int accumulateBitmask = 1 ; accumulateBitmask < (1 << traderSize) ; accumulateBitmask ++) {
            for (int currentTrader = 0 ; currentTrader < traderSize ; currentTrader ++) {
                if (DP[currentTrader][accumulateBitmask] >= 10) continue;
                int currentPrice = DP[currentTrader][accumulateBitmask];

                for (int nextTrader = 1; nextTrader < traderSize ; nextTrader ++) {
                    if ((accumulateBitmask & (1 << nextTrader)) != 0) continue;

                    int nextPrice = expectedPrice[currentTrader][nextTrader];
                    if (nextPrice >= currentPrice) {
                        int nextMask = accumulateBitmask | (1 << nextTrader);

                        if (DP[nextTrader][nextMask] > nextPrice) {
                            DP[nextTrader][nextMask] = nextPrice;
                        }
                    }
                }
            }
        }
    }
}