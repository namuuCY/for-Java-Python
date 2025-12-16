import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/23291
    // 23291 어항 정리
    static int N;
    static int K;
    static int[] fishes;
    static int[][] grid1;
    static int[][] grid2;
    static int gap = Integer.MAX_VALUE;

    // 아래, 오른, 위, 왼 순서
    static int[] dx = new int[]{1, 0, -1, 0};
    static int[] dy = new int[]{0, 1, 0, -1};
    static int[][] idxToGrid;
    static int[][] gridToIdx;
    static int[][] idxToGrid2;
    static int[][] gridToIdx2;

    static Rule rule;

    static class Rule {
        int[] gridCenter;
        int startDir;
        int rollupCount;

        Rule(int[] gridCenter, int startDir, int rollupCount) {
            this.gridCenter = gridCenter;
            this.startDir = startDir;
            this.rollupCount = rollupCount;
        }

        void init() {
            idxToGrid = new int[101][2];
            int totalCount = 1;
            int cx = gridCenter[0];
            int cy = gridCenter[1];
            idxToGrid[0] = new int[]{cx, cy};
            gridToIdx[cx][cy] = 0;
            Queue<Integer> commands = details();
            while (!commands.isEmpty()) {
                int currentDir = commands.poll();
                cx = cx + dx[currentDir];
                cy = cy + dy[currentDir];
                idxToGrid[totalCount] = new int[]{cx, cy};
                gridToIdx[cx][cy] = totalCount;
                totalCount ++;
            }
            while (totalCount <= N - 1) {
                cx = cx + dx[1];
                cy = cy + dy[1];
                idxToGrid[totalCount] = new int[]{cx, cy};
                gridToIdx[cx][cy] = totalCount;
                totalCount ++;
            }

            int r = 4;
            int c = N/4;
            idxToGrid2 = new int[N][2];
            gridToIdx2 = new int[r][c];
            for (int i = 0 ; i < c; i ++) {
                idxToGrid2[3 * c + i] = new int[]{3, i};
                gridToIdx2[3][i] = 3*c + i;

                idxToGrid2[c + i] = new int[]{1, i};
                gridToIdx2[1][i] = c + i;

                idxToGrid2[2 * c + i] = new int[]{0, c - 1 - i};
                gridToIdx2[0][c - 1 -i] = 2 * c + i;

                idxToGrid2[i] = new int[]{2, c - 1 - i};
                gridToIdx2[2][c -1 - i] = i;
            }

        }

        Queue<Integer> details() {
            List<Integer> lcount = lengthCount();
            Queue<Integer> forReturn = new ArrayDeque<>();
            int currentDir = this.startDir;
            for (int l : lcount) {
                for (int i = 0 ; i < l ; i++) {
                    forReturn.add(currentDir);
                }
                currentDir = (currentDir + 1) % 4;
            }
            return forReturn;
        }

        List<Integer> lengthCount() {
            List<Integer> tempList = new ArrayList<>();
            int temp = N;
            int tempRollupCount = 0;
            for (int i = 1; i < 11 ; i++) {
                if (temp - i >= 0 && tempRollupCount < this.rollupCount) {
                    temp -= i;
                    tempList.add(i);
                    tempRollupCount++;
                }
                if (temp - i >= 0 && tempRollupCount < this.rollupCount) {
                    temp -= i;
                    tempList.add(i);
                    tempRollupCount++;
                }
            }
            return tempList;
        }
    }



    static void init(int n, List<int[]> grids, List<int[]> centerAndDirAndRollup) {
        int size = grids.size();
        for (int i = 0; i < size - 1 ; i++) {
            int[] currentGridInfo = grids.get(i);
            if (currentGridInfo[0] <= n && n < grids.get(i + 1)[0]) {
                int rest = n - currentGridInfo[0];
                grid1 = new int[currentGridInfo[1]][currentGridInfo[2] + rest];
                gridToIdx = new int[currentGridInfo[1]][currentGridInfo[2] + rest];
                int[] center = new int[]{centerAndDirAndRollup.get(i)[0], centerAndDirAndRollup.get(i)[1]};
                int dir = centerAndDirAndRollup.get(i)[2];
                rule = new Rule(center, dir, centerAndDirAndRollup.get(i)[3]);
                rule.init();
            }
        }

        // 나머지 -1 채우기 (?)
    }

    static boolean oob(int r, int c, int cx, int cy) {
        return cx < 0 || cx >= r || cy < 0 || cy >= c;
    }

    static void distribute(int r, int c, int[][] grid) {
        int[][] diffs = new int[r][c];

        for (int i = 0 ; i < r ; i++) {
            for (int j = 0 ; j < c ; j++) {
                int cx = i;
                int cy = j;
                for (int dir = 0 ; dir < 4; dir ++) {
                    int nx = cx + dx[dir];
                    int ny = cy + dy[dir];
                    if (oob(r,c,nx,ny)) continue;
                    int cv = grid[cx][cy];
                    int nv = grid[nx][ny];
                    if (cv == 0 || nv == 0) continue;
                    if (cv > nv) {
                        if ((cv - nv) / 5 > 0) {
                            diffs[cx][cy] += (-1) * ((cv - nv) / 5);
                            diffs[nx][ny] += ((cv - nv) / 5);
                        }
                    } else {
                        if ((nv - cv) / 5 > 0) {
                            diffs[nx][ny] += (-1) * ((nv - cv) / 5);
                            diffs[cx][cy] += ((nv - cv) / 5);
                        }
                    }
                }
            }
        }

        for (int i = 0 ; i < r ; i++) {
            for (int j = 0; j < c ; j++) {
                grid[i][j] += (diffs[i][j] / 2);
            }
        }
    }

    static void magic1() {
        int r = grid1.length;
        int c = grid1[0].length;

        // mapper 맞춰서 채워넣기
        int[][] temp = new int[r][c];

        for (int i = 0 ; i < N; i++) {
            temp[idxToGrid[i][0]][idxToGrid[i][1]] = fishes[i];
        }

        distribute(r, c, temp);
        int idx = 0;
        for (int j = 0; j < c; j++) { // 열 우선
            for (int i = r - 1; i >= 0; i--) { // 아래에서 위로
                if (temp[i][j] != 0) { // 빈칸이 아니면
                    fishes[idx++] = temp[i][j];
                }
            }
        }
//        System.out.println("magic1");
//        debug();
    }

    static void magic2() {
        int r = 4;
        int c = N/4;
        int[][] temp = new int[r][c];
        for (int i = 0 ; i < N; i ++) {
            temp[idxToGrid2[i][0]][idxToGrid2[i][1]] = fishes[i];
        }
        distribute(r, c, temp);
        int idx = 0;
        for (int j = 0; j < c; j++) { // 열 우선
            for (int i = r - 1; i >= 0; i--) { // 아래에서 위로
                if (temp[i][j] != 0) { // 빈칸이 아니면
                    fishes[idx++] = temp[i][j];
                }
            }
        }
//        System.out.println("magic2");
//        debug();
    }



    static void debug() {
        System.out.println(Arrays.toString(fishes));
    }
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        fishes = new int[N];
        List<int[]> grids = new ArrayList<>();
        for (int i = 2 ; i < 11 ; i ++) {
            for (int gap = 0; gap < 2; gap++) {
                grids.add(new int[]{i * (i+gap), i+gap, i});
            }
        }
        List<int[]> centerAndDirAndRollup = new ArrayList<>();
        // 아래, 오른, 위, 왼 순서
        for (int i = 0 ; i < 6 ; i ++) {
            centerAndDirAndRollup.add(new int[]{i, i+1, 3, 2+(4*i)});
            centerAndDirAndRollup.add(new int[]{i+1, i+1, 2, 3+(4*i)});
            centerAndDirAndRollup.add(new int[]{i+1, i+1, 1, 4+(4*i)});
            centerAndDirAndRollup.add(new int[]{i+1, i+1, 0, 5+(4*i)});
        }
        init(N, grids, centerAndDirAndRollup);

        // N 에 따라 grid 선택



        // grid 선택 후 기술 1을 위한 2차원 배열 세팅
        // grid center 설정
        // grid1 메서드(매퍼)

        // 기술 2를 위한 2차원 배열 세팅
        grid2 = new int[4][N / 4];
        // grid2 메서드(매퍼)
        String[] input = br.readLine().strip().split(" ");
        for (int i = 0; i < N ; i++) {
            fishes[i] = Integer.parseInt(input[i]);
        }

        int trial = 0;

        int initMax = Integer.MIN_VALUE;
        int initMin = Integer.MAX_VALUE;
        for (int fish : fishes) {
            initMax = Math.max(initMax, fish);
            initMin = Math.min(initMin, fish);
        }

        gap = Math.min(gap, initMax - initMin);
        if (gap <= K) {
            System.out.println(0);
            return;
        }

        while (gap > K) {
            trial++;
            int minFish = Integer.MAX_VALUE;
            for (int f : fishes) minFish = Math.min(minFish, f);
            for (int i = 0; i < N; i++) {
                if (fishes[i] == minFish) fishes[i]++;
            }
            magic1();
            magic2();
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;

            for (int fish : fishes) {
                max = Math.max(max, fish);
                min = Math.min(min, fish);
            }
            gap = Math.min(gap, max - min);

        }
        System.out.println(trial);
    }
}