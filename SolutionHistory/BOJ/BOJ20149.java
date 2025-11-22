import java.io.*;
import java.util.Objects;


public class Main {

    // Source(https://www.acmicpc.net/problem/20149)
    // 20149 선분교차 3

    static double E = 1e-9;


    static boolean checkEquals(double a, double b) {
        return Math.abs(a - b) < E;
    }

    static boolean sEq(double a, double b) {
        return a - b < 0 || checkEquals(a, b);
    }


    static class Point implements Comparable<Point> {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public int compareTo(Point that) {
            if (!checkEquals(this.x, that.x)) {
                return this.x - that.x < 0 ? -1 : 1;
            }
            return this.y - that.y < 0 ? -1 : 1;
        }
    }

    static class Line {
        Point st;
        Point en;

        Line(double ax, double ay, double bx, double by) {
            Point a = new Point(ax, ay);
            Point b = new Point(bx, by);

            this.st = (a.compareTo(b) <= 0) ? a : b;
            this.en = (a.compareTo(b) <= 0) ? b : a;
        }

        boolean isVertical() {
            return checkEquals(st.x, en.x);
        }

    }
    // CCW 알고리즘
    static int ccw(long x1, long y1, long x2, long y2, long x3, long y3) {
        long val = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);
        if (val > 0) return 1;
        if (val < 0) return -1;
        return 0;
    }


    static int checkIntersection(Line a, Line b) {
        long x1 = (long) a.st.x;
        long y1 = (long) a.st.y;
        long x2 = (long) a.en.x;
        long y2 = (long) a.en.y;
        long x3 = (long) b.st.x;
        long y3 = (long) b.st.y;
        long x4 = (long) b.en.x;
        long y4 = (long) b.en.y;

        int abc = ccw(x1, y1, x2, y2, x3, y3);
        int abd = ccw(x1, y1, x2, y2, x4, y4);
        int cda = ccw(x3, y3, x4, y4, x1, y1);
        int cdb = ccw(x3, y3, x4, y4, x2, y2);

        // [수정된 부분] 모든 CCW가 0이어야 진짜 일직선 상에 있는 것(Overlap 후보)
        if (abc == 0 && abd == 0 && cda == 0 && cdb == 0) {

            // 포개짐 확인을 위해 정렬 (Swap 없이 min/max 이용)
            // Line a와 Line b는 생성자에서 이미 st < en 으로 정렬되어 있음 (User Code Line class 확인됨)
            // 하지만 x, y가 독립적으로 min/max 처리가 필요하므로 아래 로직 유지

            // 1. 완전히 겹치지 않는 경우 (분리됨)
            if (Math.min(x1, x2) > Math.max(x3, x4) || Math.max(x1, x2) < Math.min(x3, x4) ||
                    Math.min(y1, y2) > Math.max(y3, y4) || Math.max(y1, y2) < Math.min(y3, y4)) {
                return 0;
            }

            // 2. 끝점에서 한 점만 닿는 경우 (일직선 상에서 이어달리기 하듯)
            // 예: (1,1)-(2,2) 와 (2,2)-(3,3)


            if ((x2 == x3 && y2 == y3)
                    || (x1 == x4 && y1 == y4)) {
                return 1; // 한 점 교차 (좌표 출력 필요)
            }

            return 2; // 진짜 겹침 (무수히 많은 교차점)
        }

        // [일반 교차 판별]
        // T자, ㄱ자, X자 교차 모두 여기서 걸러짐 (CCW 곱이 0인 경우 포함)
        if (abc * abd <= 0 && cda * cdb <= 0) {
            return 1;
        }

        return 0;
    }


    static Point findPossibleCross(Line a, Line b) {
        boolean aIsVertical = a.isVertical();
        boolean bIsVertical = b.isVertical();

        if (aIsVertical && bIsVertical) {
            // 끝점에서 만나는 경우에만 포인트 내놓아야함.

            if (checkEquals(a.st.x, b.st.x)) {

                if (checkEquals(a.st.y, b.en.y) ) {
                    return new Point(a.st.x, a.st.y);
                }

                if (checkEquals(b.st.y, a.en.y)) {
                    return new Point(b.st.x, b.st.y);
                }
                return null;
            }
            return null;
        }

        if (aIsVertical) {
            double x = a.st.x;
            double y = innerPoint(b, x);
            if (sEq(a.st.y, y) || sEq(a.en.y, y)) {
                return new Point(x, y);
            }
            return null;
        }

        if (bIsVertical) {
            double x = b.st.x;
            double y = innerPoint(a, x);

            if (sEq(b.st.y, y) || sEq(b.en.y, y)) {
                return new Point(x, y);
            }
            return null;
        }

        double boundStart = Math.max(a.st.x, b.st.x);
        double boundEnd = Math.min(a.en.x, b.en.x);

        double startYGap = innerPoint(a, boundStart) - innerPoint(b, boundStart);
        double endYGap = innerPoint(a, boundEnd) - innerPoint(b, boundEnd);


        if (checkEquals(endYGap, 0) && checkEquals(startYGap, 0)) {

            if (checkEquals(boundStart, boundEnd)) {

                if (checkEquals(boundStart, a.st.x)) {
                    return new Point(a.st.x, a.st.y);
                }

                if (checkEquals(boundStart, b.st.x)) {
                    return new Point(b.st.x, b.st.y);
                }
            }
            return null; // 두 점 이상에서 교차할 경우임.
        }

        if (checkEquals(endYGap, 0)) {
            // end에서의 좌표 반환
            double y = innerPoint(b, boundEnd);
            return new Point(boundEnd, y);
        }

        if (checkEquals(startYGap, 0)) {
            double y = innerPoint(b, boundStart);
            return new Point(boundStart, y);
        }

        if ( startYGap > 0 ) {
            if (endYGap < 0) {
                // find point method
                return calculateCrossPoint(a, boundStart, boundEnd, startYGap, endYGap);
            }

            return null;
        }

        if (endYGap > 0) {
            return calculateCrossPoint(a, boundStart, boundEnd, startYGap, endYGap);
        }

        return null;

    }

    static double calculateCrossPointX (
            double boundStart,
            double boundEnd,
            double startYGap,
            double endYGap) {
        double tangent = (startYGap - endYGap) / (boundStart- boundEnd);

        // x, 0 -> boundStart startYGap
        // syg - 0 / bs -x =tangent
        // syg = tan(bs-x)
        // syg/tan - bs = -x
        // x = bs - syg/tan

        return (boundStart -  (startYGap / tangent));
    }

    static Point calculateCrossPoint(
            Line l,
            double boundStart,
            double boundEnd,
            double startYGap,
            double endYGap) {

        double x = calculateCrossPointX(boundStart, boundEnd, startYGap, endYGap);
        double y = innerPoint(l, x);
        return new Point(x,y);
    }


    static double innerPoint(Line l, double x) {
        double tangent = (l.st.y - l.en.y) / (l.st.x - l.en.x);
        double dx = x - l.st.x;
        double dy = tangent * dx;

        return l.st.y + dy;
    }




    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] ainput = br.readLine().strip().split(" ");
        Line a = new Line(
                Double.parseDouble(ainput[0]),
                Double.parseDouble(ainput[1]),
                Double.parseDouble(ainput[2]),
                Double.parseDouble(ainput[3])
        );
        String[] binput = br.readLine().strip().split(" ");

        Line b = new Line(
                Double.parseDouble(binput[0]),
                Double.parseDouble(binput[1]),
                Double.parseDouble(binput[2]),
                Double.parseDouble(binput[3])
        );

        int command = checkIntersection(a, b);

        if (command == 0 ) {
            System.out.println(0);
            return;
        }

        if (command == 2 ) {
            System.out.println(1);
            return;
        }

        if (command == 1) {
            System.out.println(1);
            Point maybe = findPossibleCross(a, b);

            System.out.println(maybe.x + " " + maybe.y);
        }






    }
}
