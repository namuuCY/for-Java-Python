import java.io.*;
import java.util.*;

public class Main {
    static class Condo implements Comparable<Condo> {
        int d, c;

        Condo(int d, int c) {
            this.d = d;
            this.c = c;
        }

        // 1. 거리(d) 기준 오름차순
        // 2. 거리가 같다면 비용(c) 기준 오름차순
        @Override
        public int compareTo(Condo o) {
            if (this.d == o.d) {
                return this.c - o.c;
            }
            return this.d - o.d;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        List<Condo> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            list.add(new Condo(d, c));
        }

        // 정렬: 거리 가깝고 -> 비용 싼 순서
        Collections.sort(list);

        int count = 0;
        int minCost = Integer.MAX_VALUE;

        for (Condo current : list) {
            // 현재 콘도가 지금까지 나온 콘도들 중 최소 비용보다 더 저렴하다면
            // (거리순으로 왔기 때문에, 나보다 앞선 애들은 거리상 이점이 있거나 같음.
            // 그런데 내가 그들보다 비용까지 싸다면 내가 후보가 됨)
            if (current.c < minCost) {
                count++;
                minCost = current.c; // 최소 비용 갱신
            }
        }

        System.out.println(count);
    }
}