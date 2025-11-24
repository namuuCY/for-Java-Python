import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/19236)
    // 19236 청소년 상어

    // 방향 정의

    // 청소년 상어는 00 물고기 먹고 00스타트 (방향은 00방향)

    // 물고기 번호가 작은 물고기부터 순서이동
    // 한 칸 이동
    // 빈칸, 다른물고기 있는 칸만 가능
    // 방향을 찾을때까지 45도 반시계 돌려
    // 이동할수없으면 이동 X
    // 서로의 위치를 바꾸는 방식으로 이동

    // 물고기 이동 끝나면 상어 이동
    // 방향대로
    // 한번에 여러칸 이동 가능
    // 물고기칸이면 그 칸에 있는 물고기 먹고, 그 물고기 방향
    // 지나가는 칸에 있는 물고기는 안먹음
    // 물고기 없는 칸으로는 이동 불가
    // 이동 할 수 있는 칸 없으면 집으로

    // 상어 움직이면 다시 물고기 이동
    // 물고기 움직이면
    // -> 0
    // -^ 1
    // 위 2
    // 왼위 3
    // <- 4
    // 왼아래 5
    // 아래 6
    // 오른아래 7

    // 상어가 먹을 수 있는 물고기 합의 최댓값

    // 입력값 1은 인덱스 0
    static int[] dx = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dy = new int[]{0, -1, -1, -1, 0, 1, 1, 1};

    // i,j,2

    static int ans = 0;


    static void moveFish(int[][][] current) {

        for (int i = 1 ; i < 17 ; i++) {
            boolean findI = false;
            for (int x = 0; x < 4; x++) {
                if (findI) break;
                for (int y = 0; y< 4; y++) {
                    if (findI) break;
                    if (current[x][y][0] != i) continue;
                    // 방향 결정
                    for (int dir = current[x][y][1]; dir < dir + 8 ; dir++ ){
                        int tempDir = dir % 8;
                        int nx = x + dx[tempDir];
                        int ny = y + dy[tempDir];
                        // 범위 내인가
                        if (nx >= 4 || nx <0 || ny >= 4 || ny < 0) continue;
                        if (current[nx][ny][0] == 99) continue;

                        // 물고기 끼리 스왑
                        // 물고기는 방향도 바꾼다
                        int[] tempFish = new int[]{current[nx][ny][0], current[nx][ny][1]};
                        int[] origin = new int[]{current[x][y][0], current[x][y][1]};
                        origin[1] = tempDir;
                        current[nx][ny] = origin;
                        current[x][y] = tempFish;
                        findI = true;
                        break;
                    }
                }
            }
        }
    }


    static List<int[]> countPossibility(int[][][] current, int x, int y) {
        List<int[]> list = new ArrayList<>();

        int dir = current[x][y][1];
        for (int i = 1; i < 4; i++) {
            int nx = x + dx[dir] * i;
            int ny = y + dy[dir] * i;

            if (nx >= 4 || nx < 0 || ny >= 4 || ny < 0)
                continue;
            if (current[nx][ny][0] == -1)
                continue;
            list.add(new int[]{nx, ny});
        }

        return list;
    }


    static int[][][] deepcopy(int[][][] origin) {
        int[][][] temp  =  new int[4][4][2];
        for (int i = 0 ; i < 4; i++) {
            for (int j = 0 ; j < 4; j++) {
                for (int k = 0; k < 2; k++) {
                    temp[i][j][k] = origin[i][j][k];
                }
            }
        }
        return temp;
    }

    static void bt(int x, int y, int[][][] current, int consumed) {
        // 먹고, 물고기 이동

        int[][][] currentBoard = deepcopy(current);
        int temp = currentBoard[x][y][0];
        currentBoard[x][y][0] = 99;

        moveFish(currentBoard);
        List<int[]> possibleMove = countPossibility(currentBoard, x, y);

        // 종료 조건에서 최댓값 갱신
        if (possibleMove.isEmpty()) {
            ans = Math.max(temp + consumed, ans);
            return;
        }


        currentBoard[x][y][0] = -1;

        for (int[] xy : possibleMove) {
            bt(xy[0], xy[1], currentBoard, temp + consumed);
        }

        currentBoard[x][y][0] = 99;


        // 얼마만큼 먹었냐?


        // 물고기 이동
        // 상어는 어디로?

        // 상어 위치 결정

        // 물고기는 롤백하지 않아 상어만 롤백

    }






    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[][][] board = new int[4][4][2];


        for (int i = 0; i < 4 ; i++) {
            String[] input = br.readLine().strip().split(" ");
            // fish 1
            board[i][0][0] = Integer.parseInt(input[0]);
            board[i][0][1] = Integer.parseInt(input[1]) - 1;
            // fish 2
            board[i][1][0] = Integer.parseInt(input[2]);
            board[i][1][1] = Integer.parseInt(input[3]) - 1;
            // fish 3
            board[i][2][0] = Integer.parseInt(input[4]);
            board[i][2][1] = Integer.parseInt(input[5]) - 1;
            // fish 4
            board[i][3][0] = Integer.parseInt(input[6]);
            board[i][3][1] = Integer.parseInt(input[7]) - 1;
        }

        // 상어 등장 상어는 99이라고 하자.
        // 비어있는 것은 -1

        bt(0,0, board, 0);

        // 물고기 이동

        // 상어이동

        // 찾을때까지 백트래킹해야하네
        // 백트래킹 시 주의사항 -> board를 원래대로 돌려놔야한다는 것.
        // 그러지 말고 board를 인자로 넘겨주자

        System.out.println(ans);



        // 디버깅도, 테스트도 힘들다
    }
}
