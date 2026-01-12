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


    static class Transaction {
        int currentPrice;
        int transactionCount;

        Transaction(int currentPrice, int transactionCount) {
            this.currentPrice = currentPrice;
            this.transactionCount = transactionCount;
        }

        Transaction compareAndUpsert() {

        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int traderSize = Integer.parseInt(br.readLine());
        int[][] expectedPrice = parseInput(traderSize, br);

        int[][] DP = new int[traderSize][(1 << traderSize)];

    }

    private static int[][] parseInput(int traderSize, BufferedReader br) throws IOException {

        int[][] expectedPrice = new int[traderSize][traderSize];
        for (int row = 0 ; row < traderSize ; row++) {
            char[] currentInputChars = br.readLine().strip().toCharArray();
            for (int column = 0 ; column < traderSize ; column ++) {
                expectedPrice[row][column] = (char) currentInputChars[column] - (char) '0';
            }
        }

        return expectedPrice;
    }

    private static void updateDp(int traderSize, int[][] expectedPrice, int[][] DP) {
        DP[0][1] = 1;

        for (int currentTraderBitmask = 1 ; currentTraderBitmask < (1 << traderSize) ; currentTraderBitmask ++) {
            for (int nextTrader = 1; nextTrader < traderSize ; nextTrader++) {
                // 현재 비트마스크 기반으로 방문하지 않은애들에 대해서 스타트
                if (( (currentTraderBitmask >> nextTrader) & 1 ) == 1) continue;
                // 가격 비교 후 업데이트 X 조건일 경우 생각

                // 업데이트 시에는 무조건 Math.max를 통해서 비교해야함.
                // 만약 같은데, 기존 프라이스보다 낮은 케이스
            }
        }
    }
}