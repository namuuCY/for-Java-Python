import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // BOJ 17114 (https://www.acmicpc.net/problem/17114)
    // 토마토칸 최대 1 ≤ mnopqrstuvw ≤ 10^6
    // 하나당 22개 경우의 수 -> 너무 간당간당한거같은데
    static int[] d1 = new int[] {1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] d2 = new int[] {0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] d3 = new int[] {0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] d4 = new int[] {0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] d5 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] d6 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] d7 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] d8 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0};
    static int[] d9 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0};
    static int[] d10 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,1, -1, 0, 0};
    static int[] d11 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1};

    static int[][][][][][][][][][][] box;
    static int[][][][][][][][][][][] dist;
    static Queue<int[]> Q = new LinkedList<>();



    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int o = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        int u = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());

        box = new int[w][v][u][t][s][r][q][p][o][n][m];
        dist = new int[w][v][u][t][s][r][q][p][o][n][m];

        for (int w1= 0 ; w1< w ; w1++ ) {
            for (int v1= 0 ; v1< v ; v1++ ) {
                for (int u1= 0 ; u1< u ; u1++ ) {
                    for (int t1= 0 ; t1< t ; t1++ ) {
                        for (int s1= 0 ; s1< s ; s1++ ) {
                            for (int r1= 0 ; r1< r ; r1++ ) {
                                for (int q1= 0 ; q1< q ; q1++ ) {
                                    for (int p1= 0 ; p1< p ; p1++ ) {
                                        for (int o1= 0 ; o1< o ; o1++ ) {
                                            for (int n1= 0 ; n1< n ; n1++ ) {
                                                StringTokenizer st1 = new StringTokenizer(br.readLine());
                                                for (int m1= 0 ; m1< m ; m1++ ) {
                                                    int cur = Integer.parseInt(st1.nextToken());

                                                    box[w1][v1][u1][t1][s1][r1][q1][p1][o1][n1][m1] = cur;

                                                    if (cur == 1) {
                                                        Q.offer(new int[]{w1, v1, u1, t1, s1, r1, q1, p1, o1, n1, m1});
                                                        dist[w1][v1][u1][t1][s1][r1][q1][p1][o1][n1][m1] = 1;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        while (!Q.isEmpty()) {
            int[] cur = Q.poll();
            // 확인
            for (int dir = 0 ; dir < 22 ; dir ++) {
                // wv,... 가 아니라 cur[0]~ cur[10]
                int nw = cur[0] + d1[dir];
                int nv = cur[1] + d2[dir];
                int nu = cur[2] + d3[dir];
                int nt = cur[3] + d4[dir];
                int ns = cur[4] + d5[dir];
                int nr = cur[5] + d6[dir];
                int nq = cur[6] + d7[dir];
                int np = cur[7] + d8[dir];
                int no = cur[8] + d9[dir];
                int nn = cur[9] + d10[dir];
                int nm = cur[10] + d11[dir];

                if (nw < 0) continue;
                if (nw >= w) continue;
                if (nv < 0) continue;
                if (nv >= v) continue;
                if (nu < 0) continue;
                if (nu >= u ) continue;
                if (nt < 0) continue;
                if (nt >= t ) continue;
                if (ns < 0) continue;
                if (ns >= s ) continue;
                if (nr < 0) continue;
                if (nr >= r ) continue;
                if (nq < 0) continue;
                if (nq >= q ) continue;
                if (np < 0) continue;
                if (np >= p ) continue;
                if (no < 0) continue;
                if (no >= o ) continue;
                if (nn < 0) continue;
                if (nn >= n) continue;
                if (nm < 0) continue;
                if (nm >= m) continue;

                if (box[nw][nv][nu][nt][ns][nr][nq][np][no][nn][nm] == 0
                        && dist[nw][nv][nu][nt][ns][nr][nq][np][no][nn][nm] == 0
                ) {
                    dist[nw][nv][nu][nt][ns][nr][nq][np][no][nn][nm]
                            = dist[cur[0]][cur[1]][cur[2]][cur[3]][cur[4]][cur[5]][cur[6]][cur[7]][cur[8]][cur[9]][cur[10]] + 1;
                    Q.offer(new int[]{nw, nv, nu, nt, ns, nr, nq, np, no, nn, nm});
                }
            }

        }
        int ans = 0;
        boolean tag = false;

        for (int w1= 0 ; w1< w ; w1++ ) {
            for (int v1= 0 ; v1< v ; v1++ ) {
                for (int u1= 0 ; u1< u ; u1++ ) {
                    for (int t1= 0 ; t1< t ; t1++ ) {
                        for (int s1= 0 ; s1< s ; s1++ ) {
                            for (int r1= 0 ; r1< r ; r1++ ) {
                                for (int q1= 0 ; q1< q ; q1++ ) {
                                    for (int p1= 0 ; p1< p ; p1++ ) {
                                        for (int o1= 0 ; o1< o ; o1++ ) {
                                            for (int n1= 0 ; n1< n ; n1++ ) {
                                                for (int m1= 0 ; m1< m ; m1++ ) {

                                                    int distance = dist[w1][v1][u1][t1][s1][r1][q1][p1][o1][n1][m1];

                                                    if (distance == 0
                                                            && box[w1][v1][u1][t1][s1][r1][q1][p1][o1][n1][m1]!= -1
                                                    ) {
                                                        tag = true;

                                                    } else {
                                                        ans = Math.max(ans, distance);
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(tag? -1 : ans - 1);
    }
}
