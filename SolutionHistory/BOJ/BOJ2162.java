import java.io.*;
import java.util.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/2162)
    // 2162 선분 그룹

    // 두개의 선분이 만나는지, 아닌지 구현이 1차
    // union-find를 통해서 어떤 직선이 가장 갯수가 많은지 체크 하는게 2차

    // N이 3천개인데 모든 관계에 대해 union 해보는 경우의수 N * N  / 2

    static int N;
    static Line[] lines;

    static int[] parents;
    static int[] ranks;


    static int find(int u) {
        if (! (u == parents[u]) ) {
            parents[u] = find(parents[u]);
        }
        return parents[u];
    }

    static boolean union(int u, int v) {
        int root_u = find(u);
        int root_v = find(v);

        if (root_u == root_v) return false;

        if (ranks[root_u] > ranks[root_v]) {
            parents[root_v] = root_u;
        } else {
            parents[root_u] = root_v;
            if (ranks[root_u] == ranks[root_v]) {
                ranks[root_v] ++ ;
            }
        }
        return true;
    }

    static class Coordinate implements Comparable<Coordinate> {
        int x;
        int y;

        Coordinate(int x, int y) {
            this.x= x;
            this.y = y;
        }

        public int compareTo(Coordinate o) {
            // 어차피 좌표만
            if (this.x != o.x) return this.x - o.x ;
            return this.y - o.y;
        }
    }

    static class Line {
        Coordinate s;
        Coordinate e;

        Line(int x1, int y1, int x2, int y2) {
            Coordinate c1 = new Coordinate(x1, y1);
            Coordinate c2 = new Coordinate(x2, y2);

            this.s = c1.compareTo(c2) < 0 ? c1 : c2;
            this.e = c1.compareTo(c2) < 0 ? c2 : c1;
        }

        boolean isCross(Line l) {

            int ccw1 = ccw(this, l);
            int ccw2 = ccw(l, this);

            if (ccw1 == 0 && ccw2 == 0) {
                return this.e.compareTo(l.s) >= 0 && l.e.compareTo(this.s) >= 0;
            }

            return ccw1 <= 0 && ccw2 <= 0;
        }
    }

    static int ccw(Line l1, Line l2) {
        return crossProduct(l1.s, l1.e, l2.s) * crossProduct(l1.s, l1.e, l2.e);
    }

    static int crossProduct(Coordinate A, Coordinate B, Coordinate C) {
        int a = A.x;
        int b = A.y;
        int c = B.x;
        int d = B.y;
        int e = C.x;
        int f = C.y;

        int ans = (c - a) * (f-d) - (d-b) * (e-c);

        return Integer.compare(ans, 0);
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        lines = new Line[N];

        for (int i =0 ; i < N ; i++) {
            String[] input = br.readLine().strip().split(" ");
            lines[i] = new Line(
                    Integer.parseInt(input[0]),
                    Integer.parseInt(input[1]),
                    Integer.parseInt(input[2]),
                    Integer.parseInt(input[3])
            );
        }


        parents = new int[N];
        for (int i= 1 ; i<N; i++) {
            parents[i] = i;
        }

        ranks = new int[N];

        for (int i= 0; i < N - 1 ; i ++) {
            for (int j = i + 1; j < N; j ++) {
                if (lines[i].isCross(lines[j])) {
                    union(i, j);
                }
            }
        }
        Map<Integer, Integer> rootCount = new HashMap<>();
        for (int k = 0; k < N; k++) {
            int root = find(k);
            if (Objects.isNull(rootCount.get(root))) {
                rootCount.put(root, 1);
                continue;
            }
            rootCount.compute(root, (key, current) -> current + 1);
        }

        int size = rootCount.size();

        int maxValue = rootCount.values().stream().max(Integer::compareTo).orElse(1);
        // 그룹의 수,
        // 가장 크기가 큰 그룹

        System.out.println(size);
        System.out.println(maxValue);
    }
}
