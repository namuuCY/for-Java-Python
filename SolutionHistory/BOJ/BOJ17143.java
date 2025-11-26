import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/17143)
    // 17143 낚시왕

    // 격자판 각 칸은 (r, c) r: 행 c: 열

    static int R; // R, C 는 2이상 100이하
    static int C;

    // d가 1인경우가 위, 2인경우가 아래, 3인경우 오른쪽, 4인경우 왼쪽
    // 인덱스는 1씩 깎는다
    static int[] dx = new int[]{-1, 1, 0, 0};
    static int[] dy = new int[]{0, 0, 1, -1};
    // M개의 상어 -> RC보다는 작음

    // 낚시왕은 처음 1열의 한칸 왼쪽에 위치
    // 1초동안 일어나는일
    // 낚시왕 오른쪾으로 한칸 이동
    // 낚시왕 있는 열의 상어중 땅과 가장 가까운 상어를 잡는다
    // 상어가 이동

    // 상어는 주어진 속도로 이동, 속도는 칸/초
    // 상어가 이동하려고하는 칸이 경계넘을 경우? 방향을 반대로 바꿔서 속력을 유지한채로 이동
    // 상어가 이동 마친 후 한 칸에 상어 두마리 이상 있을 경우, 크기가 가장 큰 상어가 나머지 상어를 모두 잡아먹음

    // 낚시왕이 잡은 상어 크기의 합은?

    static int[][][] board;

    static void moveAll() {
        int[][][] temp = new int[R][C][3];

        for (int i = 0; i < R ; i++) {
            for (int j = 0 ; j < C ; j++) {
                if (board[i][j][2] == 0) continue;
                // 상어 이동은 move 메서드로 구현하고,

                // newboard에 삽입할때 삭제 로직
//                System.out.println("바뀌기 전" + i +" "+ j + " " + Arrays.toString(board[i][j]));
                int[] movedRCSDZ = move(i, j, board[i][j]);
//                System.out.println("바뀐후" + Arrays.toString(movedRCSDZ));
                int r = movedRCSDZ[0];
                int c = movedRCSDZ[1];
                int z = movedRCSDZ[4];

                // 분기 시에 업데이트 제대로 되는지 확인해야함
                if (temp[r][c][2] != 0) {
                    if (temp[r][c][2] > z) {
                        continue;
                    } else {
                        temp[r][c] = new int[]{
                                movedRCSDZ[2],
                                movedRCSDZ[3],
                                z
                        };
                    }
                }
                temp[r][c] = new int[]{
                        movedRCSDZ[2],
                        movedRCSDZ[3],
                        z
                };
            }
        }
//        show(temp);
        board = temp; // board 업데이트가 안되는이유?
    }

    static int[] move(int r, int c, int[] shark) {
        int[] rcsdz = new int[5];


        rcsdz[4] = shark[2]; // z 값
        int s = shark[0];
        rcsdz[2] = s;

        int d = shark[1];

        // [3] = {s(속도), d(방향), z 크기는 그대로}
        // 상어 이동시 1) 방향 및 거리 선택(s가 판 길이보다 클 수 있음)

        // s, d 값에 의해서 결정되네

        // 넘어가야 방향이 바뀜

        if (d == 0 || d == 1) {
            // 위, 아래 이동인 경우  -> R 값이 관여
            rcsdz[1] = c;
            int nr = r + dx[d] * s;
            if (nr >= 0 && nr < R) {
                rcsdz[0] = nr;
                rcsdz[3] = d;
            } else {
                int count = s;
                int toEnd = (d == 0) ? r : R-1-r;
                int rest = count - toEnd;
                // 남은 값을 2(R-1)로 나눴을때 남은 값 x 이 R-1 보다 작거나 같으면
                // 방향은 역전
                // (d == 0) 일때는 x
                // (d == 1) 일때는 R-1-x

                // R-1 보다 크면
                // 방향은 그대로
                // (d == 0) 일때는 2(R-1) -x
                // (d == 1) 일떄는 x - (R - 1)
                int x = rest % (2 * R - 2);
                int nextR;
                int nextD;
                if (x <= R - 1) {
                    nextR = (d == 0) ? x : R - 1 - x;
                    nextD = (d == 0) ? 1 : 0;
                } else {
                    nextR = (d == 0) ? 2 * R - 2 - x : x - (R - 1);
                    nextD = (d == 0) ? 0 : 1;
                }
                rcsdz[0] = nextR;
                rcsdz[3] = nextD;
            }
        } else {
            // 왼, 오른쪽인 경우 -> C 값이 관여
            int nc = c + dy[d] * s;
            rcsdz[0] = r;
            if (nc >= 0 && nc < C) {
                rcsdz[1] = nc;
                rcsdz[3] = d;
            } else {
                int count = s;
                int toEnd = (d==3) ? c : C - 1 - c;
                int rest = count - toEnd;
                int x = rest % (2 * C - 2);
                int nextC;
                int nextD;
                if (x <= C - 1) {
                    nextC = (d == 3) ? x : C - 1 - x;
                    nextD = (d == 3) ? 2 : 3;
                } else {
                    nextC = (d == 3) ? 2 * C - 2 - x : x - (C - 1);
                    nextD = (d == 3) ? 3 : 2;
                }
                rcsdz[1] = nextC;
                rcsdz[3] = nextD;
            }
        }
        return rcsdz;
    }

    static int[][][] deepcopy() {
        int[][][] newBoard = new int[R][C][3];

        for (int i = 0; i < R; i++) {
            for (int j = 0 ; j < C ; j++) {
                for (int k =0 ; k < 3; k++) {
                    newBoard[i][j][k] = board[i][j][k];
                }
            }
        }
        return newBoard;
    }

    static void show(int[][][] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < R; i ++ ) {
            for (int j = 0; j < C; j++) {
                sb.append(b[i][j][2] + " ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] rcm = br.readLine().strip().split(" ");

        R = Integer.parseInt(rcm[0]);
        C = Integer.parseInt(rcm[1]);
        int M = Integer.parseInt(rcm[2]);

        board = new int[R][C][3]; // 맨마지막에는 속도, 방향, 크기

        while (M -- > 0) {
            String[] rcsdz = br.readLine().strip().split(" ");
            // r,c 상어 위치
            // s 속력 d 방향 z 크기
            int r = Integer.parseInt(rcsdz[0]) - 1;
            int c = Integer.parseInt(rcsdz[1]) - 1;
            board[r][c] = new int[]{
                    Integer.parseInt(rcsdz[2]),
                    Integer.parseInt(rcsdz[3]) - 1,
                    Integer.parseInt(rcsdz[4])
            };
        }

        int curC = 0;
        // 낚시왕 한 칸 열 이동
        int ans = 0;

        while (curC < C) {
            // 낚시왕 낚시
            for (int r = 0; r < R; r++) {
                if (board[r][curC][2] == 0) continue;
                ans += board[r][curC][2];
//                System.out.println(r + " 행 " + curC + " 열 에서" + board[r][curC][2] + "크기 상어를 잡아먹음");
                board[r][curC] = new int[]{0, 0, 0};

                break;
            }
            // 상어 이동
            moveAll();
//            show(board);

            curC ++;
        }
        // 낚시왕 땅에 가장 가까운 상어를 잡음

        // 상어 이동


        // 낚시왕이 C+1열에 갔을때 게임 종료
        System.out.println(ans);
    }
}
