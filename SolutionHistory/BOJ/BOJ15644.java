import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/15644)
    // 15644 구슬 탈출 3

    static int N; // 세로 크기
    static int M; // 가로 크기
    // dir = 0, 1 이면 x값만 보면 됨
    // dir = 2, 3 이면 y값만 보면 됨

    static int[] dx = new int[]{1, -1, 0, 0};
    static int[] dy = new int[]{0, 0, 1, -1};
    static boolean isPossible = false;
    static int[] hole = new int[2];
    static int bitmask = 0;
    static int ansCount = Integer.MAX_VALUE;


    // 움직이는 동작
    // 10번 이하로 구슬을 빼낼 수 있는가?
    static void bt(int[][] currentBoard, int count, int currentDir, int rx, int ry, int bx, int by, int currentBitmask) {
//        debug(currentBoard);
        // 조기 종료 조건
        if (bx == hole[0] && by == hole[1]) return;
        if (rx == hole[0] && ry == hole[1]) {
            if (ansCount > count) {
                ansCount = count;
                bitmask = currentBitmask;
            }
            isPossible = true;
            return;
        }

        if (count == 10) {
            return;
        }
        // 백트래킹 원상태 -> 다른데 찔러봄 -> 원상태 복귀
        int[][] copied = deepCopy(currentBoard);

        for (int dir = 0; dir < 4; dir ++) {
            if (dir == currentDir) continue;
            // moved는 rx, ry, bx, by의 위치를 return;
            int[] rxrybxby = moved(copied, dir, rx, ry, bx, by);
            // 이전을 빈공간으로
            int originR = copied[rxrybxby[0]][rxrybxby[1]];
            int originB = copied[rxrybxby[2]][rxrybxby[3]];
            copied[rx][ry] = 0;
            copied[bx][by] = 0;
            // 이동할 장소에 update
            copied[rxrybxby[0]][rxrybxby[1]] = 1;
            copied[rxrybxby[2]][rxrybxby[3]] = 2;
            bt(copied, count + 1, dir, rxrybxby[0], rxrybxby[1], rxrybxby[2], rxrybxby[3], (currentBitmask << 2) + dir);
            // 이동할 장소를 원래대로
            copied[rxrybxby[0]][rxrybxby[1]] = originR;
            copied[rxrybxby[2]][rxrybxby[3]] = originB;
            // 이전 위치로
            copied[rx][ry] = 1;
            copied[bx][by] = 2;
        }
    }

    static int[][] deepCopy(int[][] board) {
        int[][] temp = new int[N][M];
        for (int i = 0; i < N ; i++) {
            temp[i] = board[i];
        }
        return temp;
    }

    // 각각의 움직임에 장애물이 있거나 구멍이 있는 경우,

    static int moveLine(int[] currentLine, int dx, int x) {
        int bound = currentLine.length;
        int nextX = x;
        for (int i = 1; i < bound; i++) {
            int nx = x + i * dx;

            if (nx < 0 || nx >= bound) break;
            // -1, 0, 1, 2, 9
            // -1, 1,2 이면 break, 업데이트 안함.
            // 9 이면 업데이트, break;
            // 0 이면 업데이트
            if (currentLine[nx] == -1 || currentLine[nx] == 1 || currentLine[nx] == 2) {
                break;
            }
            if (currentLine[nx] == 9) {
                nextX = nx;
                break;
            }
            if (currentLine[nx] == 0) {
                nextX = nx;
            }
        }
        return nextX;
    }

    // isX : 복사대상이 X arr?
    static int[] copyLine(int[][] board, boolean isX, int idx ) {
        int[] temp;
        if (isX) {
            temp = new int[N];
            for (int i = 0 ; i < N; i++) {
                temp[i] = ((board[i][idx] ==1 ) || (board[i][idx] == 2)) ? 0 : board[i][idx];
            }
        } else {
            temp = new int[M];
            for (int i = 0 ; i < M; i++) {
                temp[i] = ((board[idx][i] ==1 ) || (board[idx][i] == 2)) ? 0 : board[idx][i];
            }
        }
        return temp;
    }


    static void debug(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }
    static int[] moved(int[][] board, int dir, int rx, int ry, int bx, int by) {
        int[] rxrybxby = new int[4];
        if (dir == 0 || dir == 1) {
            // dir = 0, 1 이면 x값만 보면 됨
            rxrybxby[1] = ry;
            rxrybxby[3] = by;
            if (ry == by) {
                // y 값이 같은 경우
                if ((rx > bx && (dir == 0)) || (rx < bx) && (dir == 1)) {
                    // 복사 -> 1,2 이면 0으로 만들어서 복사
                    int[] tempArr = copyLine(board, true, ry);
                    // rx의 움직임을 먼저 봐야 하는 경우
                    int nextRx = moveLine(tempArr, dx[dir], rx);
                    tempArr[nextRx] = (tempArr[nextRx] == 9 ) ? 9 :1;
                    rxrybxby[0] = nextRx;
                    int nextBx = moveLine(tempArr, dx[dir], bx);
                    rxrybxby[2] = nextBx;
//                    debug(tempArr);

                } else {
                    // bx의 움직임을 먼저 봐야 하는 경우
                    int[] tempArr = copyLine(board, true, ry);
                    int nextBx = moveLine(tempArr, dx[dir], bx);
                    tempArr[nextBx] = (tempArr[nextBx] == 9 ) ? 9 :2;
                    rxrybxby[2] = nextBx;
                    int nextRx = moveLine(tempArr, dx[dir], rx);
                    rxrybxby[0] = nextRx;
//                    debug(tempArr);
                }

            } else {
                // 다른 경우에는 따로 따로 해당
                int[] tempRArr = copyLine(board, true, ry);
                int nextRx = moveLine(tempRArr, dx[dir], rx);
                rxrybxby[0] = nextRx;
                int[] tempBArr = copyLine(board, true, by);
                int nextBx = moveLine(tempBArr, dx[dir], bx);
                rxrybxby[2] = nextBx;

            }
        } else {
            // dir = 2, 3 이면 y값만 보면 됨
            rxrybxby[0] = rx;
            rxrybxby[2] = bx;
            if (rx == bx) {
                // x 값이 같은 경우 이동에 주의
                if ((ry > by && (dir == 2)) || (ry < by) && (dir == 3)) {
                    // ry의 움직임을 먼저 봐야 하는 경우
                    int[] tempArr = copyLine(board, false, rx);
                    int nextRy = moveLine(tempArr, dy[dir], ry);
                    tempArr[nextRy] = (tempArr[nextRy] == 9 ) ? 9 : 1;
                    rxrybxby[1] = nextRy;
                    int nextBy = moveLine(tempArr, dy[dir], by);
                    rxrybxby[3] = nextBy;
//                    debug(tempArr);

                } else {
                    // by의 움직임을 먼저 봐야 하는 경우
                    int[] tempArr = copyLine(board, false, rx);
                    int nextBy = moveLine(tempArr, dy[dir], by);
                    rxrybxby[3] = nextBy;
                    tempArr[nextBy] = (tempArr[nextBy] == 9 ) ? 9 :2;
                    int nextRy = moveLine(tempArr, dy[dir], ry);
                    rxrybxby[1] = nextRy;
//                    debug(tempArr);
                }
            } else {
                // 다른 경우에는 따로 따로 해당
                int[] tempRArr = copyLine(board, false, rx);
                int nextRy = moveLine(tempRArr, dy[dir], ry);
                rxrybxby[1] = nextRy;
                int[] tempBArr = copyLine(board, false, bx);
                int nextBy = moveLine(tempBArr, dy[dir], by);
                rxrybxby[3] = nextBy;
            }
        }
        return rxrybxby;
    }

    static void debug(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < N ; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(board[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }


    public static void main(String[] args) throws Exception {
        // . 빈칸
        // # 벽
        // O 구멍
        // R 빨간 구슬
        // B 파란 구슬
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());


        int[][] board = new int[N][M];
        int[] rxrybxby = new int[4];
        for (int i = 0; i < N; i++) {
            char[] input = br.readLine().strip().toCharArray();
            for (int j = 0; j <M ; j++) {
                if (input[j] == '#') {
                    board[i][j] = -1;
                } else if (input[j] == 'O') {
                    hole[0] = i;
                    hole[1] = j;
                    board[i][j] = 9;
                } else if (input[j] == 'R') {
                    rxrybxby[0] = i;
                    rxrybxby[1] = j;
                    board[i][j] = 1;
                } else if (input[j] == 'B') {
                    rxrybxby[2] = i;
                    rxrybxby[3] = j;
                    board[i][j] = 2;
                } else {
                    board[i][j] = 0;
                }
            }
        }

        bt(board, 0, -1, rxrybxby[0], rxrybxby[1], rxrybxby[2], rxrybxby[3], 1);

        System.out.println((isPossible)? ansCount : -1);
        if (isPossible) {
            StringBuilder sb = new StringBuilder();
            for (int i = ansCount - 1 ; i >= 0 ; i--) {
                int curDir = (bitmask >> (2 * i)) & 3;
                // 0 : D 1: U 2 : R, 3:L
                if (curDir == 0) {
                    sb.append("D");
                } else if (curDir == 1) {
                    sb.append("U");
                } else if (curDir == 2) {
                    sb.append("R");
                } else {
                    sb.append("L");
                }
            }
            System.out.println(sb);
        }
    }
}