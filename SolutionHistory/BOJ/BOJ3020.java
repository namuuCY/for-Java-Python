import java.io.*;
import java.util.*;

public class Main {
    // N 20만, H 50만 단순히 곱해도 100억임 절대안됨.
    // 무조건 짝수라 아래위 아래위...


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());
        int[] down = new int[H+1];
        int[] top = new int[H+1];


        for (int i = 0; i < N/2 ; i++) {
            int h1 = Integer.parseInt(br.readLine());
            int h2 = Integer.parseInt(br.readLine());
            down[h1] ++;
            top[H - h2 + 1]++;
        }

        for (int i = H - 1; i > 0 ; i--) {
            down[i] += down[i+1];
        }
        for (int i = 1; i <= H; i++) {
            top[i] += top[i - 1];
        }

        int min = N;
        int minCount = 0;

        for (int h = 1; h <= H; h++) {
            int c = down[h] + top[h];
            if (c < min) {
                min = c;
                minCount = 1;
            } else if (c == min) {
                minCount++;
            }
        }

        System.out.println(min + " " + minCount);

    }
}