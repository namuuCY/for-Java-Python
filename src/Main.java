import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/12021
    // 보물 찾기
    static double EPSILON = 1e-6;

    static class Pair {
        double a;
        double b;


        Pair(double a, double b) {
            this.a = a;
            this.b = b;
        }

        Pair update() {
            double tempA ;
            double tempB ;

            tempA = (this.a + this.b) / 2;
            tempB = 2 * (this.a * this.b) / (this.a + this.b);
            return new Pair(tempA, tempB);
        }

        boolean isClose(Pair prev) {
            return Math.abs(this.a - prev.a) < EPSILON
                    && Math.abs(this.b - prev.b) < EPSILON;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        double a = Double.parseDouble(st.nextToken());
        double b = Double.parseDouble(st.nextToken());
        Pair pair = new Pair(a, b);

        while (true) {
            Pair next = pair.update();
            if (next.isClose(pair)) {
                System.out.printf("%.6f %.6f\n", next.a, next.b);
                return;
            }
            pair = next;
        }
    }
}