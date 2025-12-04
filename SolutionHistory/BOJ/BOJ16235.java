import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/16235)
    // 16235 나무 재테크

    // r, c r은 위에서부터 c는 왼쪽으로부터
    // r, c는 1-인덱스

    // 가장 처음에 양분은 모든 칸에 5만큼

    // 봄 : 자신의 나이만큼 양분먹고, 나이가 1증가  /
    // 자기 칸에 있는 양분만
    // 하나의 칸에 여러 나무가 있으면 어린 나무부터 양분을 먹는다
    // 자기 나이만큼 못먹으면 죽어버림

    // 여름 : 봄에 죽은 나무가 양분 죽은 나무의 나이를 2로 나눈 값이 나무가 있던 칸에 양분으로 추가됨 소수점 아래는 버림

    // 가을 : 번식 - 나이가 5의 배수여야하고, 인접 8개 칸에 나이가 1인 나무가 생김

    // 가을 : 각 칸에 A[r][c]추가, 입력으로 주어짐

    // k년이 지나고 상도의 땅에 살아있는 나무 개수?



    static int N;
    static int M;
    static int K;
    static int[][] annual;
    static List<Integer>[][] trees;
    static int[][] map;
    static int[] dx = new int[]{1, 0, -1, 0, 1, -1, 1, -1};
    static int[] dy = new int[]{0, 1, 0, -1, 1, -1, -1, 1};

    static void springAndSummer() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 현재 양분
                // 작은 것 부터 순서대로 양분 먹여야함. 모자라면 2로 나누고 -붙이기
                List<Integer> currentTrees = trees[i][j];
                currentTrees.sort(Comparator.comparingInt(a -> a));
                List<Integer> tempTree = new ArrayList<>();
                int deadTree = 0;

                for (int ageOfTree : currentTrees) {
                    if (ageOfTree <= map[i][j]) {
                        tempTree.add(ageOfTree + 1);
                        map[i][j] -= ageOfTree;
                    } else {
                        deadTree += (ageOfTree/ 2);
                    }
                }
                trees[i][j] = tempTree;
                map[i][j] += deadTree;
            }
        }
    }



    static void fall() {
        // 깊은 복사
        List<Integer>[][] temp = new ArrayList[N][N];
        for (int i = 0; i < N; i++) {
            for (int j= 0; j <N; j++) {
                temp[i][j] = trees[i][j];
            }
        }


        for (int i = 0 ; i < N ; i++) {
            for (int j = 0 ; j < N ; j++) {
                // 나이가 5의 배수일때 퍼트린다
                for (int ageOfTree : temp[i][j]) {
                    if (ageOfTree % 5 != 0) continue;
                    for (int dir = 0 ; dir < 8 ; dir ++) {
                        int nr = i + dx[dir];
                        int nc = j + dy[dir];

                        if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                        trees[nr][nc].add(1);
                    }
                }
            }
        }
    }

    static void winter() {
        for (int i = 0; i < N ; i++) {
            for (int j = 0; j <N; j++) {
                map[i][j] += annual[i][j];
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] nmk = br.readLine().strip().split(" ");

        N = Integer.parseInt(nmk[0]);
        M = Integer.parseInt(nmk[1]);
        K = Integer.parseInt(nmk[2]);

        annual = new int[N][N];
        trees = new ArrayList[N][N];
        map = new int[N][N];

        for (int i = 0; i < N ; i++) {
            String[] input = br.readLine().strip().split(" ");
            for (int j = 0; j < N; j++) {
                annual[i][j] = Integer.parseInt(input[j]);
                trees[i][j] = new ArrayList<>();
                map[i][j] = 5;
            }
        }

        for (int i = 0; i < M ; i++) {
            String[] rcAge = br.readLine().strip().split(" ");
            int r = Integer.parseInt(rcAge[0]) - 1;
            int c = Integer.parseInt(rcAge[1]) - 1;
            int age = Integer.parseInt(rcAge[2]);
            trees[r][c].add(age);
        }

        while (K-- > 0) {
            springAndSummer();
            fall();
            winter();
        }
        int ans = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ans += trees[i][j].size();
            }
        }

        System.out.println(ans);
    }
}