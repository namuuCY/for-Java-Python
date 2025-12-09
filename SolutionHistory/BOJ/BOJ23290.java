import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/23290)
    // 23290 마법사 상어와 복제

    static int[][] scent = new int[4][4];
    static List<Fish>[][] curFish = new ArrayList[4][4];

    // 상어가 인덱스로 접근하기 쉬워야함.
    static class Shark {
        int x;
        int y;
        // 사전순? 상은 1, 좌는 2, 하는 3, 우는 4
        int[] dx = new int[]{-1, 0, 1, 0};
        int[] dy = new int[]{0, -1, 0, 1};

        Shark(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void paste(List<Fish>[][] copied) {

            for (int i = 0 ; i < 4 ; i++) {
                for (int j = 0; j < 4; j++) {
                    if (copied[i][j].isEmpty()) continue;
                    curFish[i][j].addAll(copied[i][j]);
                }
            }
        }

        void moveAndUpdateScent() {
            // 이동 판단 by bt
            List<Fish>[][] temp = copy(curFish);
            int[] maxAteBm = new int[]{-1, 0};
            // 기존 냄새 빼고
            bt(this.x, this.y, temp, 0, 0, 0, maxAteBm);

            // bm대로 가면서 먹은거 냄새 업데이트
            int bitmask = maxAteBm[1];
            for (int i = 4; i >= 0 ; i -= 2) {
                int nextMove = (bitmask >> i) & 3;
                this.x = this.x + dx[nextMove];
                this.y = this.y + dy[nextMove];
                if (!curFish[this.x][this.y].isEmpty()) {
                    curFish[this.x][this.y] = new ArrayList<>();
                    scent[this.x][this.y] = 3;
                }
            }
            for (int i = 0 ; i < 4 ; i++) {
                for (int j = 0; j < 4; j++) {
                    if (scent[i][j] == 0) continue;
                    scent[i][j] --;
                }
            }
        }

        void bt(int cx, int cy, List<Fish>[][] currentFish, int count, int ate, int bitmask, int[] maxAteBm) {
            if (count == 3) {
                if (maxAteBm[0] < ate) {
                    maxAteBm[0] = ate;
                    maxAteBm[1] = bitmask;
                }
                return;
            }

            List<Fish>[][] temp = copy(currentFish);
            for (int nextDir = 0 ; nextDir < 4; nextDir++) {

                int nx = cx + dx[nextDir];
                int ny = cy + dy[nextDir];


                if (nx < 0|| nx >= 4|| ny< 0 || ny >= 4) continue;
                List<Fish> copied = new ArrayList<>();
                copied.addAll(temp[nx][ny]);
                temp[nx][ny] = new ArrayList<>();

                // 비트마스크 계산 순서 주의
                bt(nx, ny, temp, count+1, ate+ copied.size(), (bitmask << 2) + nextDir, maxAteBm);

                temp[nx][ny] = copied;
            }
        }
    }

    static class Fish {
        int dir;
        //        1부터 순서대로 ←, ↖, ↑, ↗, →, ↘, ↓, ↙
        int[] dx = new int[]{0, -1, -1, -1, 0, 1, 1, 1};
        int[] dy = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};

        Fish(int dir) {
            this.dir = dir;
        }

        int[] move(int x, int y, Shark shark) {
            // 이동 판단
            int[] xy = new int[]{x,y};
            for (int i = 0; i < 8 ; i ++) {
                int curDir = (dir - i + 8) % 8;
                int nx = x + dx[curDir];
                int ny = y + dy[curDir];
                if (nx < 0 || nx >= 4 || ny <0 || ny>= 4) continue;
                if (scent[nx][ny] > 0) continue;
                if ((nx == shark.x) && (ny == shark.y)) continue;
                xy[0] = nx;
                xy[1] = ny;
                this.dir = curDir;
                break;
            }
            return xy;
        }
    }

    // 상어 위치 항상 표시
    //

    // 4 x 4 격자
    // r x c -> 1 idx
    // 물고기 M 마리 -> 이동방향은 8가지
    // 둘이상 같이 있을 수 있음. 마법사 상어도 포함
    // 1번에서 모든 물고기 복제 시전,
    // 2번 물고기 이동 : 상어 x, 물고기 냄새가 있는칸 x, 이동할 수 있는 칸을 향할때까지 45도 반시계 회전, 이동 못하면 안함
    // 3번 상어는 상하좌우 인접칸 이동(물고기 수가 가장 많은 방법으로, 여러가지면 사전순으로 가장 앞서는 방법), 이동중 물고기 만나면 물고기 제외 및 냄새남김
    // 사전순? 상은 1, 좌는 2, 하는 3, 우는 4
    // 4번 두 번 전 연습에서 생긴 물고기의 냄새가 격자에서 사라진다 (수명 2 -> 수명이 0된 냄새 다 지우는 듯)
    // 5번에서 복제되어 칸에 나옴

    // 상어 이동시에는 백트래킹을 써야하네
    // 복제 시전 (미리 담아두기) -> 물고기 이동 -> 상어 판단 -> 물고기 삭제 -> 기존 냄새 줄이고 -> 삭제된 냄새 업데이트 -> 복제 한 물고기 등장

    // 물고기, 냄새를 어떻게 둘것인가?

    static List<Fish>[][] init() {
        List<Fish>[][] temp = new ArrayList[4][4];
        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 4; j++) {
                temp[i][j] = new ArrayList<>();
            }
        }
        return temp;
    }

    static List<Fish>[][] copy(List<Fish>[][] origin) {
        List<Fish>[][] temp = init();

        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 4; j++) {
                for (Fish f : origin[i][j]) {
                    temp[i][j].add(new Fish(f.dir));

                    //   이런식으로 얕은 복사하니까 문제가 생기네
                    //   temp[i][j].add(f);
                }
            }
        }

        return temp;
    }

    static void debug() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 4; j++) {
                sb.append(curFish[i][j].size());
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

//         필요한 자료구조 초기화
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                curFish[i][j] = new ArrayList<>();
            }
        }



        int M = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken()) - 1;
            curFish[x][y].add(new Fish(dir));
        }

        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken()) - 1;
        int y = Integer.parseInt(st.nextToken()) - 1;

        Shark shark = new Shark(x, y);

        while (S-- > 0) {
            List<Fish>[][] copied = copy(curFish);
            List<Fish>[][] temp = init();

            // 물고기 이동
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    List<Fish> fs = curFish[i][j];
                    for (Fish f : fs) {
//                        System.out.println("이동전 방향 : "+ f.dir);
                        int[] xy = f.move(i,j, shark);
//                        System.out.println("이동 후 방향 : "+ f.dir);
                        temp[xy[0]][xy[1]].add(f);
                    }
                }
            }
            curFish = temp;
//            debug();
            shark.moveAndUpdateScent();
            shark.paste(copied);
//            debug();
        }

        int ans = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ans += curFish[i][j].size();
            }
        }

        System.out.println(ans);
        //
    }
}