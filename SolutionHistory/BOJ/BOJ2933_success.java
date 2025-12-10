import java.io.*;
import java.util.*;

public class Main {
    static int[][] board;
    static int R;
    static int C;
    static int[] dx = new int[]{-1, 1, 0, 0};
    static int[] dy = new int[]{0, 0, 1, -1};

    // 미네랄 깨기 (기존과 동일)
    static int crash(int height, boolean isFromLeft) {
        if (isFromLeft) {
            for (int i = 0; i < C ; i++) {
                if (board[height][i] == 1) {
                    board[height][i] = 0;
                    return i;
                }
            }
        } else {
            for (int i = C - 1; i >= 0  ; i--) {
                if (board[height][i] == 1) {
                    board[height][i] = 0;
                    return i;
                }
            }
        }
        return -1;
    }

    // BFS로 연결된 클러스터 좌표 찾기 (리스트 반환)
    static ArrayList<int[]> findCluster(int r, int c, boolean[][] visited) {
        ArrayList<int[]> points = new ArrayList<>();
        Queue<int[]> q = new LinkedList<>();

        q.add(new int[]{r, c});
        visited[r][c] = true;
        points.add(new int[]{r, c});

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int d=0; d<4; d++) {
                int nx = cur[0] + dx[d];
                int ny = cur[1] + dy[d];

                if(nx < 1 || nx > R || ny < 0 || ny >= C) continue;
                if(board[nx][ny] == 1 && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    q.add(new int[]{nx, ny});
                    points.add(new int[]{nx, ny});
                }
            }
        }
        return points;
    }

    // 떨어질 높이 계산 및 이동 처리
    static boolean moveCluster(ArrayList<int[]> points) {
        // 1. 맵에서 현재 클러스터를 잠깐 지운다 (자기 자신 충돌 방지)
        for(int[] p : points) {
            board[p[0]][p[1]] = 0;
        }

        // 2. 얼마나 떨어질 수 있는지 계산 (최소값 찾기)
        int minDrop = Integer.MAX_VALUE;

        for(int[] p : points) {
            int r = p[0];
            int c = p[1];
            int drop = 0;

            // 아래로 내려가면서 빈칸(0)이 아닐 때까지(땅 -1 이나 다른 미네랄 1) 탐색
            while(true) {
                // 한 칸 아래 검사
                if (board[r - 1 - drop][c] == 0) {
                    drop++;
                } else {
                    // 0이 아닌 것을 만남 (바닥 -1 또는 다른 미네랄 1)
                    break;
                }
            }
            minDrop = Math.min(minDrop, drop);
        }

        // 3. 맵에 다시 그리기 (내려간 위치에)
        for(int[] p : points) {
            board[p[0] - minDrop][p[1]] = 1;
        }

        // 0칸보다 더 떨어졌다면 이동한 것이므로 true 반환
        return minDrop > 0;
    }

    static void debug(int[][] temp) {
        StringBuilder sb = new StringBuilder();
        for (int i = R ; i >= 1 ; i--) {
            for (int j = 0 ; j< C; j++) {
                sb.append((temp[i][j] == 1)? "x":".");
            }
            sb.append("\n");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        System.out.println(sb);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        board = new int[R + 1][C];
        Arrays.fill(board[0], -1); // 땅은 -1

        for (int i = R ; i >= 1 ; i--) {
            char[] input = br.readLine().strip().toCharArray();
            for (int j = 0 ; j< C; j++) {
                board[i][j] = (input[j] == '.') ? 0 : 1;
            }
        }

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        boolean isFromLeft = true;

        while (N-- > 0) {
            int height = Integer.parseInt(st.nextToken());
            int brokenCol = crash(height, isFromLeft);

            // 미네랄이 깨졌다면 클러스터 확인
            if(brokenCol != -1) {
                boolean[][] visited = new boolean[R+1][C];

                // 깨진 위치의 상하좌우 4방향 확인
                for(int d=0; d<4; d++) {
                    int nx = height + dx[d];
                    int ny = brokenCol + dy[d];

                    if(nx < 1 || nx > R || ny < 0 || ny >= C) continue;
                    if(board[nx][ny] == 1 && !visited[nx][ny]) {
                        ArrayList<int[]> cluster = findCluster(nx, ny, visited);
                        if(moveCluster(cluster)) {
                            break;
                        }
                    }
                }
            }
            isFromLeft = !isFromLeft;
        }
        debug(board);
    }
}