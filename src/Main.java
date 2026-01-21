import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/14658
    // 하늘에서 별똥별이 빗발친다

    // 최대한 많은 별똥별 튕겨나가는 개수
    // 위치 좌표 주어질때, x, y 값 기준으로 해서 그 범위 안에 있으면?
    // L이 10만이면 10만안에 그 숫자가 있는지 확인하는 것도 오래걸림.

    // 에를 들어 꼭지점 빼고 변두리에만 다 있을 경우라던가 존재하니까 꼭지점이 되어야 한다는 판단 X

    // 100 개의 좌표에 대해서 x 차이 y차이를 기록해보면?
    // 모든 나올 수 있는 사각형 만들고 (변 길이가 L 초과로 차이나면 제외?)
    // x, y 후보는 모든 점 ?

    static int N, M, L, K;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine().strip());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        int[][] meteors = parseInput(br);

        int maxMeteors = 0;

        for (int i = 0 ; i < K; i++) {
            for (int j = 0 ; j < K; j++) {
                int currentX = meteors[i][0];
                int currentY = meteors[j][1];

                int currentMeteors = countMeteors(currentX, currentY, meteors);
                maxMeteors = Math.max(currentMeteors, maxMeteors);
                if (maxMeteors == K) {
                    System.out.println(0);
                    return;
                }
            }
        }

        System.out.println(K - maxMeteors);
    }

    private static int[][] parseInput(BufferedReader br) throws IOException {
        int[][] vertexes = new int[K][2];
        StringTokenizer st;
        for (int i = 0 ; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            vertexes[i][0] = Integer.parseInt(st.nextToken());
            vertexes[i][1] = Integer.parseInt(st.nextToken());
        }
        return vertexes;
    }

    private static int countMeteors(int vertexX, int vertexY, int[][] meteors) {
        int currentMeteors = 0;

        for (int i = 0 ; i < K; i++) {
            int meteorX = meteors[i][0];
            int meteorY = meteors[i][1];

            if (vertexX > meteorX) continue;
            if (meteorX > vertexX + L) continue;
            if (vertexY > meteorY) continue;
            if (meteorY > vertexY + L) continue;

            currentMeteors ++;
        }

        return currentMeteors;
    }
}
