import java.io.*;

public class Main {
    // https://www.acmicpc.net/problem/9252

    static int N;
    static int M;
    static int[][] DP;

    public static void main(String[] args) throws IOException {
        // 두 문자는 최대 1000 글자

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] firstWord = br.readLine().strip().toCharArray();
        char[] secondWord = br.readLine().strip().toCharArray();

        N = firstWord.length;
        M = secondWord.length;

        DP = new int[N + 1][M + 1];

        for (int i = 0 ; i < N; i++) {
            for (int j = 0 ; j < M; j++) {
                if (firstWord[i] == secondWord[j]) {
                    DP[i + 1][j + 1] = DP[i][j] + 1;
                } else {
                    DP[i + 1][j + 1] = Math.max(DP[i][j + 1], DP[i + 1][j]);
                }
            }
        }

        System.out.println(DP[N][M]);

        // DP 테이블 기반 역추적
        if (DP[N][M] == 0) return;

        int i = N;
        int j = M;
        StringBuilder sb = new StringBuilder();

        while (i > 0 && j > 0) {
            // 같으면 대각선 위로
            if (firstWord[i - 1] == secondWord[j - 1]) {
                sb.append(firstWord[i - 1]);
                i -- ;
                j -- ;
            }
            // 값이 같은 DP 테이블로 추적
            else if (DP[i][j] == DP[i - 1][j]) {
                i --;
            }
            else if (DP[i][j] == DP[i][j - 1]) {
                j --;
            }
        }

        System.out.println(sb.reverse());
    }
}