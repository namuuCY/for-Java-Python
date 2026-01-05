import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    // https://www.acmicpc.net/problem/3987
    // 3987 보이저 1호

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine().strip());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        char[][] twoDimGrid = initGrid(br, N, M);

        st = new StringTokenizer(br.readLine().strip());
        int voyagerRow = Integer.parseInt(st.nextToken()) - 1;
        int voyagerColumn = Integer.parseInt(st.nextToken()) - 1;

        int[] travelTimes = new int[4];

        if (!(twoDimGrid[voyagerRow][voyagerColumn] == 'C')) {
            // todo : 메인 로직
            for (int direction = 0; direction < 4; direction++) {
                travelTimes[direction] = simulateByDirection(voyagerRow, voyagerColumn, N, M, direction, twoDimGrid);
                if (travelTimes[direction] == Integer.MAX_VALUE) break;
            }
        }
        // 아래와 같은 방식으로
//        if ((twoDimGrid[voyagerRow][voyagerColumn] == '\\')) {
//            for (int direction = 0; direction < 4; direction++) {
//                int reflected = reflectDirection(true, direction);
//                travelTimes[direction] = simulateByDirection(voyagerRow, voyagerColumn, N, M, reflected, twoDimGrid);
//                if (travelTimes[direction] == Integer.MAX_VALUE) break;
//            }
//        } else if ((twoDimGrid[voyagerRow][voyagerColumn] == '/')) {
//            for (int direction = 0; direction < 4; direction++) {
//                int reflected = reflectDirection(false, direction);
//                travelTimes[direction] = simulateByDirection(voyagerRow, voyagerColumn, N, M, reflected, twoDimGrid);
//                if (travelTimes[direction] == Integer.MAX_VALUE) break;
//            }
//        } else if ( 블랙홀일때 )
        decideAndWriteOutput(travelTimes);
    }

    private static char[][] initGrid(BufferedReader br, int rowSize, int columnSize) throws IOException {
        char[][] charGrid = new char[rowSize][columnSize];

        for (int row = 0; row < rowSize ; row++) {
            char[] inputCharOfCurrentRow = br.readLine().strip().toCharArray();
            for (int column = 0 ; column < columnSize ; column++) {
                charGrid[row][column] = inputCharOfCurrentRow[column];
            }
        }
        return charGrid;
    }

    private static void decideAndWriteOutput(int[] travelTimes) {
        int maxTime = Arrays.stream(travelTimes).max().orElse(0);
        int maxDirection = -1;

        for (int dir = 0 ; dir < 4; dir++) {
            if (maxTime != travelTimes[dir]) continue;
            maxDirection = dir;
            break;
        }
        System.out.println(convertNumberToDirection(maxDirection));
        System.out.println((maxTime == Integer.MAX_VALUE) ? "Voyager" : maxTime);
    }

    private static char convertNumberToDirection(int dirNumber) {
        switch ( dirNumber ) {
            case 0 :
                return 'U';
            case 1 :
                return 'R';
            case 2 :
                return 'D';
            default :
                return 'L';
        }
    }

    private static int simulateByDirection(
            int voyagerRow, int voyagerColumn,
            int rowSize, int columnSize,
            int direction,
            char[][] grid) {
        int time = 0;
        boolean[][][] visitedPositionDirection = new boolean[4][rowSize][columnSize];

        int currentRow = voyagerRow;
        int currentColumn = voyagerColumn;
        int currentDirection = direction;
        // u r d l
        int[] diffR = new int[]{-1, 0, 1, 0};
        int[] diffC = new int[]{0, 1, 0, -1};

        while (true) {
            // 종료조건은 1) isOutofBounds
            currentRow += diffR[currentDirection];
            currentColumn += diffC[currentDirection];
            time += 1;

            if (isOutOfBounds(currentRow, currentColumn, rowSize, columnSize)) return time;

            if (grid[currentRow][currentColumn] == '\\') {
                if (visitedPositionDirection[currentDirection][currentRow][currentColumn]) {
                    return Integer.MAX_VALUE;
                }
                visitedPositionDirection[currentDirection][currentRow][currentColumn] = true;
                currentDirection = reflectDirection(true, currentDirection);
            } else if (grid[currentRow][currentColumn] == '/') {
                if (visitedPositionDirection[currentDirection][currentRow][currentColumn]) {
                    return Integer.MAX_VALUE;
                }
                visitedPositionDirection[currentDirection][currentRow][currentColumn] = true;
                currentDirection = reflectDirection(false, currentDirection);
            } else if (grid[currentRow][currentColumn] == 'C') {
                return time;
            }
        }
    }

    private static boolean isOutOfBounds(int currentRow, int currentColumn, int rowSize, int columnSize) {
        return currentRow < 0 || currentRow >= rowSize || currentColumn < 0 || currentColumn >= columnSize;
    }

    // \ 의 경우, 1과 2 사이의 스왑, 0과 3사이의 스왑
    // / 의 경우, 0과 1사이의 스왑, 2와 3사이의 스왑
    private static int reflectDirection(boolean isReverseSlash, int currentDirection) {
        if (isReverseSlash) {
            switch (currentDirection) {
                case 1 :
                    return 2;
                case 2 :
                    return 1;
                case 0 :
                    return 3;
                default :
                    return 0;
            }
        } else {
            switch (currentDirection) {
                case 0 :
                    return 1;
                case 1 :
                    return 0;
                case 2 :
                    return 3;
                default :
                    return 2;
            }
        }
    }

}