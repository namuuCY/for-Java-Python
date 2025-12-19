import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/1507
    // 1507 궁금한 민호

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        int N = Integer.parseInt(br.readLine());
        int[][] dist = new int[N][N];

        boolean[][] isRedundant = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                dist[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {

                    if (i == k || j == k || i == j) continue;

                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        System.out.println(-1);
                        return;
                    }

                    if (dist[i][j] == dist[i][k] + dist[k][j]) {
                        isRedundant[i][j] = true;
                    }
                }
            }
        }

        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (!isRedundant[i][j]) {
                    result += dist[i][j];
                }
            }
        }

        System.out.println(result);
    }

}