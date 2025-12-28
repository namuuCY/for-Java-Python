import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    // https://www.acmicpc.net/problem/13334
    // 13334 철로

    static int N;
    static int d;
    static PriorityQueue<Integer> pq = new PriorityQueue<>();
    static List<Route> routes;

    // 일단 bf로 푸는 경우 :
    // 모든 집, 사무실 을 L 범위에 맞게 필터링해서 최대크기를 찾는 것
    // L 범위는 그러면 어떻게 결정? -> L은 ???
    // 시작점, 끝점을 모두 후보로?
    // 모두 후보로...?

    static class Route implements Comparable<Route> {
        int start;
        int end;

        Route(int v1, int v2) {
            if (v1 < v2) {
                this.start = v1;
                this.end = v2;
            } else {
                this.start = v2;
                this.end = v1;
            }
        }

        public int compareTo(Route that) {
            if (this.end != that.end) {
                return this.end - that.end;
            } else {
                return this.start - that.start;
            }
        }
    }

    static void checkAndPoll(int startPoint) {
        // pq가 비어있지 않으면 체크
        // 있다면 마지막
        while (!pq.isEmpty() ) {
            if (pq.peek() < startPoint) {
                pq.poll();
                continue;
            }
            break;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        int temp = N;

        List<Route> tempList = new ArrayList<>();
        while (temp -- > 0) {
            // 집, 사무실 모두 포함이니까 순서 바꿔도 동일, 오름차순 되도록 정렬 후 저장
            String[] input = br.readLine().strip().split(" ");
            int p1 = Integer.parseInt(input[0]);
            int p2 = Integer.parseInt(input[1]);

            tempList.add(new Route(p1, p2));
        }

        d = Integer.parseInt(br.readLine());

        routes = tempList.stream().sorted().collect(Collectors.toList());

        int ans = 0;

        for (Route r : routes) {
            pq.add(r.start);
            checkAndPoll(r.end - d);
            ans = Math.max(ans, pq.size());
        }

        System.out.println(ans);
    }
}