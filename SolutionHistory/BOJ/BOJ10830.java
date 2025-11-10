import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/10830)
    // BOJ 10830 - 행렬제곱

    // 크기 N x N 인 행렬
    // B 제곱의 값을 구하기
    // 행렬과 곱을 정의

    // B를 이진수로 나타내서 2진수의 곱으로 계산할 것
    //  11
    //   5  1  -> 1
    //   2  1  -> 2
    //   1  0
    //   0  1 -> 8


    static int N;
    static long B;
    static List<Matrix> dp = new ArrayList<>();

    static class Matrix {
        int [][] numbers = new int[N][N];

        public static Matrix identity() {
            int [][] current = new int[N][N];
            for (int i = 0; i < N ; i++) {
                current[i][i] = 1;
            }
            return new Matrix(current);
        }

        Matrix(int[][] numbers) {
            this.numbers = numbers;
        }

        int getFromIdx(int i, int j) {
            return numbers[i][j];
        }

        void update(int i, int j, int val) {
            numbers[i][j] = val;
        }

        Matrix square() {
            int [][] ans = new int[N][N];

            for (int i = 0; i < N ; i++) {
                for (int j = 0; j < N ; j++) {
                    int entry = 0;
                    for (int k = 0; k < N ; k++) {
                        entry += numbers[i][k] * numbers[k][j];
                    }
                    ans[i][j] = entry % 1000;
                }
            }
            return new Matrix(ans);
        }

        Matrix multiply(Matrix m) {
            int [][] ans = new int[N][N];

            for (int i = 0; i < N ; i++) {
                for (int j = 0; j < N ; j++) {
                    int entry = 0;
                    for (int k = 0; k < N ; k++) {
                        entry += numbers[i][k] * m.getFromIdx(k, j);
                    }
                    ans[i][j] = entry % 1000;
                }
            }
            return new Matrix(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        B = Long.parseLong(st.nextToken());

        Matrix m = new Matrix(new int[N][N]);

        for (int i = 0; i < N ; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0 ; j < N ; j++) {
                m.update(i, j, Integer.parseInt(st.nextToken()));
            }
        }
        // 1차 값
        dp.add(m);

        // 이진수 비트마스킹? 나중에 다시 확인해보기
        long current = B;
        List<Long> residues = new ArrayList<>();

        while(current > 0) {
            long c = current;
            residues.add(c % 2);
            current = c / 2;
        }

        // 마지막 제곱수를 구하면 나머지는 다 등록이 된다.

        int size = residues.size();

        for (int k = 0; k < size - 1 ; k++) {
            Matrix currentMatrix = dp.get(k);
            dp.add(currentMatrix.square());
        }

        Matrix ans = Matrix.identity();

        for (int l = 0 ; l < size; l++ ) {
            if (residues.get(l) == 0) continue;
            Matrix newMatrix = ans.multiply(dp.get(l));
            ans = newMatrix;
        }
        StringBuilder sb = new StringBuilder();
        for (int i1 = 0 ; i1 < N ; i1++) {
            for (int j1 = 0 ; j1 < N ; j1++ ) {
                sb.append(ans.getFromIdx(i1, j1));
                sb.append(" ");
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }
}
