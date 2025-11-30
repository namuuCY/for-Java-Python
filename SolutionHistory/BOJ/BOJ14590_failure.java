import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/14590)
    // 14590 KUBC League (Small)

    static int N;
    static boolean[][] isWinner;
    static int maxLength;
    static List<Integer> maxList = new ArrayList<>();


    static void dfs(int bitmask, List<Integer> current) {


        // 이미 N 이면
        if (current.size() == N) {
            System.out.println(N);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N; i++) {
                sb.append((current.get(i) + 1)).append(" ");
            }
            System.out.println(sb);
            System.exit(0);
        }

        if (bitmask == ((1 << N) - 1) ) {
            if (maxLength < maxList.size()) {
                maxLength = maxList.size();
                maxList = current;
            }
            return;
        }


        for (int i = 1; i < N; i++) {

            List<Integer> copied = new ArrayList<>(current);
            int currentSize = copied.size();
            int currentPlayer = copied.get(currentSize - 1);

            if ((bitmask & (1 << i)) == 0 && isWinner[currentPlayer][i]) {
                copied.add(i);
                dfs((bitmask | (1<<i)), copied);
                copied.remove(currentSize);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        // p번 선수가 q번 선수를 이겼으면 1
        isWinner = new boolean[N][N];
        for (int i = 0 ; i < N ; i++) {
            String[] input = br.readLine().strip().split(" ");
            for (int j = 0; j < N; j++) {
                // i가 이겼으면 1, 졌으면 0
                isWinner[i][j] = input[j].equals("1");
            }
        }


        List<Integer> list = new ArrayList<>();
        list.add(0);
        maxLength = 1;
        maxList = list;
        // 1부터 시작하는 꼬리 길이 만들기
        dfs(1, list);

        System.out.println(maxLength);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            sb.append((maxList.get(i) + 1)).append(" ");
        }
        System.out.println(sb);
    }
}
