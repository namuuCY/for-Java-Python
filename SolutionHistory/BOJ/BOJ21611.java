import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/21611)
    // 21611 마법사 상어와 블리자드

    static int N;
    //    static int[][] board;
    static int[] snake;
    static int popCount = 0;

    // 왼쪽, 아래, 오른쪽, 위
    static int[] dx = new int[]{0, 1, 0, -1};
    static int[] dy = new int[]{-1, 0, 1, 0};

    // mapper를 통해서 인덱스 바로 찾을 수 있게
    static int[][] pointToIdx;
    static Map<Integer, int[]> idxToPoint = new HashMap<>();

    static int convertDir(String input) {
        if (input.equals("1")) {
            return 3;
        } else if (input.equals("2")) {
            return 1;
        } else if (input.equals("3")) {
            return 0;
        } else {
            return 2;
        }
    }

    static void bz(int dir, int dist) {
        int cx = ((N + 1) / 2) -1;
        int cy = ((N + 1) / 2) -1;
        for (int i = 1; i <= dist ; i++) {
            int nx = cx + i * dx[dir];
            int ny = cy + i * dy[dir];
            int idx = pointToIdx[nx][ny];
            snake[idx] = 0;
        }
    }

    static void press() {
        int[] temp = new int[N * N - 1];
        int curIdx = 0;
        for (int i = 0; i < N * N - 1; i++) {
            if (snake[i] == 0) continue;
            temp[curIdx] = snake[i];
            curIdx++;
        }
        snake = temp;
//        debug();
    }

//    static void debug() {
//        int[][] board = new int[N][N];
//        for (int i = 0; i < N * N - 1; i++) {
//            int[] xy = idxToPoint.get(i);
//            board[xy[0]][xy[1]] = snake[i];
//        }
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j <N ; j++) {
//                sb.append(board[i][j]);
//                sb.append(" ");
//            }
//            sb.append("\n");
//        }
//        System.out.println(sb);
//    }

    static boolean pop() {
        int[] temp = new int[N * N - 1];
        int currentNumber = -1;
        int count = 0;
        boolean isChanged = false;
        // 이미 압축된거라 0인 케이스는 없음
        for (int i = 0; i < N * N - 1; i++) {
            temp[i] = snake[i];
            if (count == 0 && (snake[i] != 0)) {
                currentNumber = snake[i];
                count++;
            } else {
                if (snake[i] == currentNumber && (i != N * N - 2)) {
                    count++;
                } else {
                    // 여태까지 카운트 세고, 만약 4이상이면 0으로 바꾸기
                    if (snake[i] == currentNumber) {
                        count++;
                    }
                    if (count >= 4) {
                        isChanged = true;
                        popCount += (currentNumber) * count;
                        // snake[i] == currentNumber 라면 i 부터
                        // 아니라면 i-1 부터
                        if (snake[i] == currentNumber) {
                            for (int c = 0; c <= count - 1; c++) {
                                temp[i - c] = 0;
                            }
                        } else {
                            for (int c = 1; c <= count; c++) {
                                temp[i - c] = 0;
                            }
                        }

                    }
                    currentNumber = snake[i] != 0 ? snake[i] : -1;
                    count = snake[i] != 0 ? 1 : 0;
                }
            }
        }

        if (isChanged) {
            snake = temp;
//            debug();
            return true;
        }

//        debug();
        return false;
    }

    static void mutate() {
        int[] temp = new int[N * N - 1];
        int currentNumber = -1;
        int count = 0;
        int tempIdx = 0;
        // 이미 압축된거라 0인 케이스는 없음
        for (int i = 0; i < N * N - 1; i++) {
            if (tempIdx > N * N - 2) break;
            if (count == 0 ) {
                currentNumber = snake[i];
                count++;
            } else {
                if (snake[i] == currentNumber && (i != N * N - 2)) {
                    count++;
                } else {
                    // 하나의 그룹이 두개의 구슬 A / B 로 바뀜 : A는 구슬 개수, B는 숫자
                    if (currentNumber == 0) continue;
                    temp[tempIdx] = count;
                    if (tempIdx + 1 < N* N -1) {
                        temp[tempIdx + 1] = currentNumber;
                    }
                    tempIdx += 2;
                    currentNumber = snake[i];
                    count = 1;
                }
            }
        }
        snake = temp;
    }



    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        pointToIdx = new int[N][N];
        snake = new int[N * N - 1];

        int cx = (N + 1) / 2 - 1;
        int cy = (N + 1) / 2 - 1;
        // 1 1 2 2 3 3 ... 7 7 8 8 8 이런식인데
        // pop?
        // 11 22 33 44 ...
        //
        // N 이면 마지막은 N - 1
        // 1 1 2 2 3 3 4 4 5 5 6 6
        Queue<Integer> Q = new LinkedList<>();

        for (int i = 1 ; i < N; i++) {
            Q.add(i);
            Q.add(i);
        }
        Q.add(N - 1);

        int curDir = 0;
        int curIdx = 0;
        while (!Q.isEmpty()) {
            int totalCount = Q.poll();
            for (int i = 0; i < totalCount; i++) {
                cx = cx + dx[curDir];
                cy = cy + dy[curDir];
                pointToIdx[cx][cy] = curIdx;
                idxToPoint.put(curIdx, new int[]{cx, cy});
                curIdx ++;
            }
            curDir = (curDir + 1) % 4;
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int biz = Integer.parseInt(st.nextToken());
                if (i == ((N + 1) / 2) - 1 && j == ((N + 1) / 2) - 1) continue;
                int idx = pointToIdx[i][j];
                snake[idx] = biz;
            }
        }

        while (M -- > 0) {
            String[] dirDist = br.readLine().strip().split(" ");
            int dir = convertDir(dirDist[0]);
            int distance = Integer.parseInt(dirDist[1]);
            // 블리자드로 구슬 파괴(0)
//            debug();
            bz(dir, distance);
//            debug();

            boolean isPop = true;
            // 더이상 안생길때까지 폭발 (boolean 태그?)
            while (isPop) {
                // 구슬 이동
                press();
                // 폭발 (연속된거 4개 이상)
                isPop = pop();
            }
            // 구슬 변화
            mutate();
//            debug();
        }
        System.out.println(popCount);
    }
}