import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/17387)
    // BOJ 17387 선분교차 2

    // 첫째 줄의 L1의 양끝점
    // 둘째 줄에 L2의 양 끝점

    // 선분이 겹치면 1 아니면 0

    // 시간제한 0.25초

    //

    // y축 평행인 경우 생각을 안했네

    static final double EPSILON = 1e-9;

    static boolean isEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }
    static boolean sEq(double a, double b) {
        return (a < b) || isEqual(a,b);
    }

    static class Line {
        double[] start;
        double[] end;

        Line(double sx, double sy, double ex, double ey) {
            if (sEq(sx, ex)) {
                this.start = new double[]{sx, sy};
                this.end = new double[]{ex, ey};
            } else {
                this.start = new double[]{ex, ey};
                this.end = new double[]{sx, sy};
            }
        }

        int isCross(Line l) {
            // this, l의 범위 비교
            if (!checkBound(l)) return 0;

            // this, l의 공통 start x 최댓값으로
            double sx = Math.max(this.start[0], l.start[0]);
            double ex = Math.min(this.end[0], l.end[0]);
            // this, l의 공통 end x는 최솟값으로

            double[] thisSP = calculateInnerPoint(sx);
            double[] thisEP = calculateInnerPoint(ex);
            double[] lSP = l.calculateInnerPoint(sx);
            double[] lEP = l.calculateInnerPoint(ex);

            // 공통 start x에서의 두개의 y 좌표 뺀 값과 공통 end x에서의 두개의 y좌표 뺀 값 곱해서 음수거나 0 나오면 1 아니면 0
            double d1 = thisSP[1] - lSP[1];
// 공통 end x에서의 y 차이 (d2)
            double d2 = thisEP[1] - lEP[1];

// 곱셈 없이 부호 판별: (d1 <= 0 AND d2 >= 0) OR (d1 >= 0 AND d2 <= 0)
// sEq(a, b)는 a <= b 를 의미함
            boolean case1 = sEq(d1, 0) && sEq(0, d2); // d1은 음수(이하), d2는 양수(이상)
            boolean case2 = sEq(0, d1) && sEq(d2, 0); // d1은 양수(이상), d2는 음수(이하)

            if (case1 || case2) {
                return 1;
            }

            return 0;
        }


        double[] calculateInnerPoint(double x) {
            double tangent = (start[1] - end[1]) / (start[0] - end[0]);
            double diff = tangent * (x - start[0]);

            return new double[]{x, start[1] + diff};
        }



        boolean checkBound(Line l) {

//           System.out.println(this.end[0] < l.start[0]);
//           System.out.println(l.end[0] < this.start[0]);
            if (this.end[0] < l.start[0] || l.end[0] < this.start[0]) return false;
            return true;
        }

        boolean checkBound(double x) {
            return (sEq(this.start[0], x))
                    && (sEq(x, this.end[0]));
        }
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        String[] l1 = br.readLine().strip().split( " ");
        Line line1 = new Line(
                Double.parseDouble(l1[0]),
                Double.parseDouble(l1[1]),
                Double.parseDouble(l1[2]),
                Double.parseDouble(l1[3])
        );


        String[] l2= br.readLine().strip().split( " ");
        Line line2 = new Line(
                Double.parseDouble(l2[0]),
                Double.parseDouble(l2[1]),
                Double.parseDouble(l2[2]),
                Double.parseDouble(l2[3])
        );

        // l1 만 y축 평행 직선
        if (isEqual(Double.parseDouble(l1[0]),Double.parseDouble(l1[2]))
                && ! isEqual(Double.parseDouble(l2[0]), Double.parseDouble(l2[2]))
        ) {
            // Double.parseDouble(l1[0]) 가 line2 사이에있는지도 확인해야함
            if ( !line2.checkBound(Double.parseDouble(l1[0])) ) {
                System.out.println(0);
                return;
            }

            double[] middle = line2.calculateInnerPoint(Double.parseDouble(l1[0]));
            double y = middle[1];

            double start = Math.min(Double.parseDouble(l1[1]), Double.parseDouble(l1[3]));
            double end = Math.max(Double.parseDouble(l1[1]), Double.parseDouble(l1[3]));

            int ans = (sEq(start, y) && sEq(y, end)) ? 1 : 0;
            System.out.println(ans);
            return;
        }
        //l2만 y축 평행 직선
        if (isEqual(Double.parseDouble(l2[0]),Double.parseDouble(l2[2]))
                && ! isEqual(Double.parseDouble(l1[0]), Double.parseDouble(l1[2]))
        ) {

            if ( !line1.checkBound(Double.parseDouble(l2[0])) ) {
                System.out.println(0);
                return;
            }
            double[] middle = line1.calculateInnerPoint(Double.parseDouble(l2[0]));
            double y = middle[1];

            double start = Math.min(Double.parseDouble(l2[1]), Double.parseDouble(l2[3]));
            double end = Math.max(Double.parseDouble(l2[1]), Double.parseDouble(l2[3]));

            int ans = (sEq(start, y) && sEq(y, end)) ? 1 : 0;

            System.out.println(ans);
            return;
        }

        // 둘 다 y 축 평행직선
        if (isEqual(Double.parseDouble(l1[0]),Double.parseDouble(l1[2]))
                && isEqual(Double.parseDouble(l2[0]), Double.parseDouble(l2[2]))
        ) {

            // x축이 같지않다면 0
            if (!isEqual(Double.parseDouble(l2[0]), Double.parseDouble(l1[0]))) {
                System.out.println(0);
                return;
            }

            // x축이 같다면 범위 비교
            double s1 = Math.min(Double.parseDouble(l1[1]), Double.parseDouble(l1[3]));
            double e1 = Math.max(Double.parseDouble(l1[1]), Double.parseDouble(l1[3]));
            double s2 = Math.min(Double.parseDouble(l2[1]), Double.parseDouble(l2[3]));
            double e2 = Math.max(Double.parseDouble(l2[1]), Double.parseDouble(l2[3]));

            if (e1 < s2 || e2 < s1) {
                System.out.println(0);
                return;
            }

            System.out.println(1);
            return;
        }

        System.out.println(line1.isCross(line2));
    }
}
