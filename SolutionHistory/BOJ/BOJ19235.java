import java.io.*;
import java.util.*;

public class Main {


//    https://www.acmicpc.net/problem/19235
    // 19235 모노미노도미노

    static int[][] green = new int[6][4];
    static int[][] blue = new int[4][6];
    static int ans = 0;
    static int count1 = 1;
    static int count2 = 100001;
    static int count3 = 200001;

    static void debug(int type, int r, int c) {


        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        int spr = r+1;
        int spc = c+1;

        for (int i = 0 ; i < 4 ; i ++) {
            for (int j = 0; j < 4; j++) {
                if ((i == r) && (j == c)) {
                    sb.append(type);
                    sb.append(" ");
                    continue;
                }
                if (type == 2 && (i == r) && (j == spc)) {
                    sb.append(type);
                    sb.append(" ");
                    continue;
                }
                if ((type == 3) && (i == spr) && (j == c)) {
                    sb.append(type);
                    sb.append(" ");
                    continue;
                }
                sb.append(0);
                sb.append(" ");
            }

            for (int j = 0; j < 6; j++) {
                sb.append((blue[i][j]== 0)? 0 : blue[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }

        for (int i = 0 ; i < 6 ; i ++) {


            for (int j = 0; j < 4; j++) {
                sb.append((green[i][j] == 0)? 0 : green[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }


        System.out.println(sb);
    }

    static void drop(int type, int x, int y) {
        if (type == 2) {
            // (x,y) (x,y+1) ->...
            greenDrop(new int[]{x}, new int[]{y, y+1}, count2);
            blueDrop(new int[]{x}, new int[]{y, y+1}, count2);
            count2++;
        } else if (type == 3) {
            // (x,y) (x+1, y)
            greenDrop(new int[]{x, x + 1}, new int[]{y}, count3);
            blueDrop(new int[]{x, x + 1}, new int[]{y}, count3);
            count3++;
        } else {
            greenDrop(new int[]{x}, new int[]{y}, count1);
            blueDrop(new int[]{x}, new int[]{y}, count1);
            count1++;
        }
    }

    static void greenDrop(int[] xl, int[] yl, int number) {
        int maxDepth = 6;
        for (int y : yl) {
            int currentDepth = 2;
            for (int x = 2; x < 6 ; x++) {
                if (green[x][y] != 0) {
                    break;
                }
                currentDepth ++;
            }
            maxDepth = Math.min(maxDepth, currentDepth);
        }
        if (xl.length > 1) {
            green[maxDepth -2][yl[0]] = number;
            green[maxDepth -1][yl[0]] = number;
        } else {
            for (int y : yl) {
                green[maxDepth -1][y] = number;
            }
        }
//        System.out.println("drop되고 난 후");
//        debug(0,0,0);
    }

    static void blueDrop(int[] xl, int[] yl, int number) {
        int maxDepth = 6;
        for (int x : xl) {
            int currentDepth = 2;
            for (int y = 2; y < 6 ; y++) {
                if (blue[x][y] != 0) {
                    break;
                }
                currentDepth ++;
            }
            maxDepth = Math.min(maxDepth, currentDepth);
        }
        if (yl.length > 1) {
            blue[xl[0]][maxDepth - 2] = number;
            blue[xl[0]][maxDepth - 1] = number;
        } else {
            for (int x : xl) {
                blue[x][maxDepth - 1] = number;
            }
        }
    }


    static void eraseEnd(int[] gb) {
        int cnt = gb[0];
        if (cnt > 0) {
            for (int i = 5; i >= cnt; i--) {
                for (int j = 0; j < 4; j++) {
                    green[i][j] = green[i - cnt][j];
                }
            }
            // 윗부분 비우기
            for (int i = 0; i < cnt; i++) {
                for (int j = 0; j < 4; j++) {
                    green[i][j] = 0;
                }
            }
        }

        // 2. Blue 처리

        cnt = gb[1];
        if (cnt > 0) {
            for (int j = 5; j >= cnt; j--) {
                for (int i = 0; i < 4; i++) {
                    blue[i][j] = blue[i][j - cnt];
                }
            }
            for (int j = 0; j < cnt; j++) {
                for (int i = 0; i < 4; i++) {
                    blue[i][j] = 0;
                }
            }
        }
    }


//    static void eraseEnd(int[] gb) {
//        for (int r = 5 ; r > 5-gb[0] ; r--) {
//            for (int c = 0; c  < 4; c++) {
//                green[r][c] = 0;
//            }
//        }
//
//        for (int c = 5 ; c > 5-gb[1] ; c--) {
//            for (int r = 0; r  < 4; r++) {
//                blue[r][c] = 0;
//            }
//        }
//    }

    static boolean eraseWithScore() {
        boolean isMore = false;
        for (int i = 0; i < 6 ; i++) {
            for (int j = 0; j < 4; j++) {
                if (green[i][j] == 0) break;
                if (j == 3) {
//                    debug(0, 0, 0);
//                    System.out.println("이상태에서 그린에 " + i + " 행 제거됨");
                    for (int k = 0; k < 4; k++) {
                        green[i][k] = 0;
                    }
                    ans ++;
                    isMore = true;
                }
            }
        }

        for (int c = 0; c < 6 ; c++) {
            for (int r = 0; r < 4; r++) {
                if (blue[r][c] == 0) break;
                if (r == 3) {
                    for (int k = 0; k < 4; k++) {
                        blue[k][c] = 0;
                    }
//                    System.out.println("블루에 " + c + " 열 제거됨");
                    ans ++;
                    isMore = true;
                }
            }
        }

        return isMore;
    }

    static void press() {
        // 각각 r, c 맨 오른쪽부터
        boolean[][] gVisit = new boolean[6][4];
        boolean[][] bVisit = new boolean[4][6];
        for (int r = 4; r >= 0 ; r--) {
            for (int c = 0; c < 4; c++) {
                if (gVisit[r][c]) continue;
                gVisit[r][c] = true;
                if (green[r][c] == 0) continue;
                int temp = green[r][c];
                if (green[r][c] / 100000 == 1) {
                    // type 2
                    // // (x,y) (x,y+1)

                    // 1 1 0 0  상태에서 안움직였을때 왼쪽 1을 체크하고
                    // 오른쪽 1을 체크하면 그거의 오른쪽이 0이라서
                    if ((c + 1 < 4) && green[r][c + 1] == green[r][c]) {
//                        System.out.println(green[r][c + 1] +" 이랑 " + green[r][c]);
                        gVisit[r][c+1] = true;
                        green[r][c] = 0;
                        green[r][c+1] = 0;
                        int gap = 10;
                        for (int i = 0; i < 2; i++) {
                            int dist = 0;
                            while (((r + dist + 1) <= 5) && green[r + dist + 1][c + i] == 0) {
                                dist++;
                            }
                            gap = Math.min(gap, dist);
//                            System.out.println(gap + " 으로 gap 수정");
                        }
                        green[r + gap][c] = temp;
                        green[r + gap][c+1] = temp;
                        continue;
                    }

                    green[r][c] = 0;
                    int dist = 0;
                    while (((r+dist+1) <= 5) && green[r + dist + 1][c] == 0) {
                        dist++;
                    }
                    green[r+dist][c] = temp;

                } else if (green[r][c] / 100000 == 2) {
                    // 2개 챙겨야 할 때

                    if ((r - 1 >= 0) && green[r-1][c] == green[r][c]) {
                        gVisit[r-1][c] = true;
                        green[r][c] = 0;
                        green[r-1][c] = 0;
                        int dist = 0;
                        while (((r+dist+1) <= 5) && green[r + dist + 1][c] == 0) {
                            dist++;
                        }
                        green[r + dist][c] = temp;
                        green[r + dist -1][c] = temp;
                        continue;
                    }

                    green[r][c] = 0;
                    int dist = 0;
                    while (((r+dist+1) <= 5) && green[r + dist + 1][c] == 0) {
                        dist++;
                    }
                    green[r+dist][c] = temp;
                    // 1개만
                } else {
                    green[r][c] = 0;
                    int dist = 0;
                    while (((r+dist+1) <= 5) && green[r + dist + 1][c] == 0) {
                        dist++;
                    }
                    green[r+dist][c] = temp;
                }
            }
        }

        for (int c = 4; c >= 0 ; c--) {
            for (int r = 0; r < 4; r++) {
                if (bVisit[r][c]) continue;
                bVisit[r][c] = true;
                if (blue[r][c] == 0) continue;
                int temp = blue[r][c];
                if (blue[r][c] / 100000 == 2) {
                    // type 3
                    // // (x,y) (x,y+1)

                    if ((r + 1 < 4) && blue[r + 1][c] == blue[r][c]) {
                        bVisit[r+1][c] = true;
                        blue[r][c] = 0;
                        blue[r+1][c] = 0;
                        int gap = 10;
                        for (int i = 0; i < 2; i++) {
                            int dist = 0;
                            while (((c + dist + 1) <= 5) && blue[r + i][c + dist + 1] == 0) {
                                dist++;
                            }
                            gap = Math.min(gap, dist);
                        }
                        blue[r][c + gap] = temp;
                        blue[r+1][c+ gap] = temp;
                        continue;
                    }

                    blue[r][c] = 0;
                    int dist = 0;
                    while (((c+dist+1) <= 5) && blue[r][c+dist+1] == 0) {
                        dist++;
                    }
                    blue[r][c+dist] = temp;

                } else if (blue[r][c] / 100000 == 1) {
                    // 2개 챙겨야 할 때

                    if ((c - 1 >= 0) && blue[r][c - 1] == blue[r][c]) {
                        bVisit[r][c-1] = true;
                        blue[r][c] = 0;
                        blue[r][c-1] = 0;
                        int dist = 0;
                        while (((c+dist+1) <= 5) && blue[r ][c+ dist + 1] == 0) {
                            dist++;
                        }
                        blue[r][c + dist] = temp;
                        blue[r][c + dist -1] = temp;
                        continue;
                    }

                    blue[r][c] = 0;
                    int dist = 0;
                    while (((c+dist+1) <= 5) && blue[r][c + dist + 1] == 0) {
                        dist++;
                    }
                    blue[r][c+dist] = temp;
                    // 1개만
                } else {
                    blue[r][c] = 0;
                    int dist = 0;
                    while (((c+dist+1) <= 5) && blue[r][c + dist + 1] == 0) {
                        dist++;
                    }
                    blue[r][c+dist] = temp;
                }
            }
        }
//        System.out.println("press되고 난 후");
    }

    // 제거해야할 줄 수 알려줌
    // int[] = {green, blue}
    static int[] checkShell() {
        int[] gb = new int[2];

        for (int r = 0 ; r < 2 ; r++) {
            for (int c = 0; c < 4 ; c++) {
                if (green[r][c] == 0) continue;
                gb[0]++;
                break;
            }
        }

        for (int c = 0 ; c < 2 ; c++) {
            for (int r = 0; r < 4 ; r++) {
                if (blue[r][c] == 0) continue;
                gb[1]++;
//                System.out.println(gb[1]);

                break;
            }
        }
        return gb;
    }



    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        while (N -- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int dropR = Integer.parseInt(st.nextToken());
            int dropC = Integer.parseInt(st.nextToken());
            drop(type, dropR, dropC);


            while (true) {
                press();
                if (!eraseWithScore()) break;
            }

            int[] gb = checkShell();
            eraseEnd(gb);

//            debug(type, dropR, dropC);
        }

        System.out.println(ans);

        int block = 0;
        for (int i = 0; i< 6 ;i++) {
            for (int j = 0 ; j < 4 ; j++) {
                block += (green[i][j]!=0) ? 1:0;
                block += (blue[j][i]!=0) ? 1:0;
            }
        }
        System.out.println(block);


    }
}