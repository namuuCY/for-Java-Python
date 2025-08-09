import java.io.*;
import java.util.*;

public class Main {
    // 특정 시작점 1 -> 끝점 N 까지의 최소거리 + 간선 정보가 1이상 => 다익스트라
    // 1 -> N까지 가는 경우의 수가 유일할 경우, -1 출력해야함.

    // 이 경우를 근데 어케 구하지? 이런 경우를 쉽게 찾으면 가지치기 할때 쉬운데

    // 시간복잡도 ElogE * E라 시간내에 가능해보임

    static class Node implements Comparable<Node>{
        int end;
        int cost;

        Node(int e, int c) {
            end = e;
            cost = c;
        }

        void setCost(int c) {
            cost = c;
        }

        public int compareTo(Node n) {
            return this.cost - n.cost;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        List<List<Node>> graph = new ArrayList<>();
        int [][] graphList = new int[E][3];

        for (int g = 0 ; g <= V; g++) {
            graph.add(new ArrayList<>());
        }

        for (int m = 0 ; m < E; m++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph.get(from).add(new Node(to, cost));
            int index = graph.get(from).size() - 1 ;
            graphList[m] = new int[]{from, to, index};
        }
        // 두번째 난관? 어떻게 간선을 뽑아서 바꾸고 연산하고 다시 원래대로 돌릴것인가

        int standard;
        PriorityQueue<Node> P = new PriorityQueue<>();
        P.offer(new Node(1, 0));
        int[] td = new int[V + 1];
        Arrays.fill(td, Integer.MAX_VALUE);
        td[1] = 0;

        while(!P.isEmpty()) {
            Node cur = P.poll();
            int curNode = cur.end;
            int curCost = cur.cost;

            if (curCost > td[curNode]) continue;

            for (Node next : graph.get(curNode)) {
                int nextNode = next.end;
                int nextCost = curCost + next.cost;

                if (nextCost < td[nextNode]) {
                    td[nextNode] = nextCost;
                    P.offer(new Node(nextNode, nextCost));
                }
            }
        }
        standard = td[V];

        int gap = 0;

        for (int i = 0; i < E ; i++) {
            P.clear();
            P.offer(new Node(1, 0));
            int from = graphList[i][0];
            int to = graphList[i][1];

            int[] dist = new int[V + 1];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[1] = 0;

            while (!P.isEmpty()) {
                Node cur = P.poll();
                int curNode = cur.end;
                int curCost = cur.cost;

                if (curCost > dist[curNode]) continue;

                for (Node next : graph.get(curNode)) {
                    int nextNode = next.end;
                    int nextCost = curCost + next.cost;

                    if (curNode == from && nextNode == to) continue;

                    if (nextCost < dist[nextNode]) {
                        dist[nextNode] = nextCost;
                        P.offer(new Node(nextNode, nextCost));
                    }
                }
            }

            if (dist[V] == Integer.MAX_VALUE) {
                System.out.println(-1);
                return;
            }
            gap = Math.max(gap, dist[V]-standard);
        }
        System.out.println(gap);
    }


    // 1트 실패 문제점 : 메모리 초과.
    // => 우선순위 큐 재사용로직

    // 2트 아예 틀림
    // => 최단경로가 여러개있는 경우 틀렸다고 나오네.

    // 가로막는게 최단경로인경우 => gap이 1이상
    // 가로막는게 최단경로가 아닌 경우 => gap이 0
    // 우선순위 큐를 비우지 않아 남아있는 것의 문제로 틀렸을 가능성

    // 3트 틀림 : 메모리초과
    // 모든 간선을 고려하지 말아야겠던데
    // + 현재의 빼고, 다시 집어넣고는 고려하지 말아야할듯.

    // 공간복잡도 : C++기준 int 1.2억개가 512메가
    // => 아마 자바에서도 비슷하게 128메가면 3천만개가량.
    // 지금보니까 E도 5천개라 2.5천만이네 1000개라고 잘못 생각했다

    // 가지치기 1 : 리스트에서 없애지 말고 해당 인덱스에서는 무시하기
    // => 리스트의  중간인덱스 삭제는 비용이 크다
    // 가지치기 2 : 최단경로에서만 간선 차단


}