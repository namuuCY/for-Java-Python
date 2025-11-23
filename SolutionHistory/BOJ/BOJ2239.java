import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/2239)
    // 2239 스도쿠

    // horizon, vertical, square
    // 인덱스 있으면 세 개에 대응해서 숫자 확인 [][]
    // 백트래킹 시작...
    // 즉, 81자리의 수가 제일 작은 경우를 출력한다. -> 일단 제



    // 각각의 숫자별 위치가 어떤 범위를 체크해야하는지 설정해야함. -> 수직, 수평은 쉬움.


    // i : 행 고정
    // j : 열 고정

    static int[][] board = new int[9][9];

    static boolean[][] row = new boolean[9][10]; // i 가 고정
    static boolean[][] col = new boolean[9][10]; // j 가 고정

    static boolean[][] box = new boolean[9][10];
    // 0 1 2
    // 3 4 5
    // 6 7 8

    static int findBox(int i, int j) {
        return (i / 3) * 3 + (j / 3);
    }

    static void bt(int i, int j) {
        if (i >= 9) {
            StringBuilder sb = new StringBuilder();
            for (int k = 0 ; k < 9 ; k++) {
                for (int l = 0; l<9 ; l++) {
                    sb.append(board[k][l]);
                }
                sb.append("\n");
            }
            System.out.println(sb);

            System.exit(0);
        }

        int nextI = (j == 8) ? i+1 : i;
        int nextJ = (j == 8) ? 0 : j + 1;

        if (board[i][j] != 0) {
            bt(nextI, nextJ);
            return;
        }

        for (int num = 1; num < 10; num++) {
            if (row[i][num] || col[j][num] || box[findBox(i, j)][num]) continue;

            board[i][j] = num;
            row[i][num] = true;
            col[j][num] = true;
            box[findBox(i, j)][num] = true;

            bt(nextI, nextJ);

            board[i][j] = 0;
            row[i][num] = false;
            col[j][num] = false;
            box[findBox(i, j)][num] = false;
        }

// 아래가 기존 실패한 백트래킹 로직 -> for문 사용 X. 백트래킹의 인덱스로만 해야함.
        // i, j에 따라서 진행
//        for (int tempi = i; tempi < 9 ; tempi++) {
//            for (int tempj = 0; tempj < 9 ; tempj++) {
//                if (tempi == i && tempj < j) continue; // i,j부터 시작
//                if (board[tempi][tempj] != 0) continue;
//
//                for (int num = 1; num < 10; num++) {
//                    if (row[tempi][num] || col[tempj][num] || box[findBox(tempi, tempj)][num]) continue;
//                    board[tempi][tempj] = num;
//                    row[tempi][num] = true;
//                    col[tempj][num] = true;
//                    box[findBox(tempi, tempj)][num] = true;
//                    if (tempj == 8) {
//                        bt(tempi +1 , 0);
//                    } else {
//                        bt(tempi, tempj + 1);
//                    }
//                    board[tempi][tempj] = 0;
//                    row[tempi][num] = false;
//                    col[tempj][num] = false;
//                    box[findBox(tempi, tempj)][num] = false;
//                }
//            }
//        }
    }

    // 없는 숫자는 범위별 linkedList에 정렬해서 순서대로 넣기


    public static void main(String[] args) throws Exception {


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 9; i++) {
            char[] input = br.readLine().strip().toCharArray();
            for (int j = 0; j < 9; j++) {
                int cur = (int) input[j] - (int) '0';
                if (cur > 0) {
                    row[i][cur] = true;
                    col[j][cur] = true;
                    box[findBox(i,j)][cur] = true;
                }
                board[i][j] = cur;
            }
        }

        // 백트래킹
        bt(0,0);


    }
}
