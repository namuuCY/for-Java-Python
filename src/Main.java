import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/4485
    // 4485 녹색 옷 입은 애가 젤다지?

    static class Node implements Comparable<Node> {
        int r;
        int c;
        int cost;

        Node(int r, int c, int cost) {
            this.r = r;
            this.c = c;
            this.cost = cost;
        }

        public int compareTo(Node that) {
            return Integer.compare(this.cost, that.cost);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int trial = 1;

        while (true) {
            int N = Integer.parseInt(br.readLine().trim());
            if (N == 0) break;

            int[][] twoDimGrid = parseInput(N, br);

            // 다익스트라 로직
            Integer minCost = processQuery(twoDimGrid, N);

            System.out.printf("Problem %d: %d", trial, minCost);
            System.out.println();
            trial ++;
        }
    }

    private static int[][] parseInput(int size, BufferedReader br) throws IOException {
        int[][] twoDimGrid = new int[size][size];
        for (int i = 0; i < size; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine().strip());
            for (int j = 0 ; j < size; j++) {
                Integer thiefRupee = Integer.parseInt(st.nextToken());
                twoDimGrid[i][j] = thiefRupee;
            }
        }
        return twoDimGrid;
    }

    private static Integer processQuery(int[][] twoDimGrid, int size) {
        // 다익스트라를 위한 초기화 작업
        PriorityQueue<Node> minCostQueue = new PriorityQueue<>();
        // twoDimGrid를 클래스 새로 선언해도 좀 더 객체지향적으로 하는게 가능해보입니다.
        int baseCost = twoDimGrid[0][0];
        Node initNode = new Node(0, 0, baseCost);
        int[][] currentCostGrid = initDijkstraGrid(size);

        return runDijkstra(minCostQueue, initNode, currentCostGrid, size, twoDimGrid);
    }

    private static int[][] initDijkstraGrid(int size) {
        int[][] initiated = new int[size][size];
        for (int i = 0 ; i < size ; i++) {
            Arrays.fill(initiated[i], Integer.MAX_VALUE);
        }
        return initiated;
    }

    private static int runDijkstra(
            PriorityQueue<Node> minCostQueue,
            Node initNode,
            int[][] currentCostGrid,
            int size,
            int[][] twoDimGrid) {
        minCostQueue.add(initNode);
        currentCostGrid[0][0] = initNode.cost;

        int[] diffR = new int[]{1, 0, -1, 0};
        int[] diffC = new int[]{0, 1, 0, -1};

        while (!minCostQueue.isEmpty()) {
            Node currentNode = minCostQueue.poll();

            if (currentCostGrid[currentNode.r][currentNode.c] < currentNode.cost) continue;
            if (currentNode.r == size -1
            && currentNode.c == size -1) {
                return currentCostGrid[currentNode.r][currentNode.c];
            }

            for (int dir = 0; dir < 4; dir ++) {
                int nextRow = currentNode.r + diffR[dir];
                int nextColumn = currentNode.c + diffC[dir];
                if ( isOutOfBounds( size, nextRow, nextColumn)) continue;
                if (currentCostGrid[nextRow][nextColumn]
                        > currentCostGrid[currentNode.r][currentNode.c]
                        + twoDimGrid[nextRow][nextColumn]
                ) {
                    currentCostGrid[nextRow][nextColumn]
                            = currentCostGrid[currentNode.r][currentNode.c]
                            + twoDimGrid[nextRow][nextColumn];
                    minCostQueue.add(
                            new Node(
                                    nextRow, nextColumn,
                                    currentCostGrid[nextRow][nextColumn]
                            )
                    );
                }
            }
        }
        return currentCostGrid[size - 1][size - 1];
    }

    private static boolean isOutOfBounds(int size, int row, int column) {
        return row < 0 || row >= size || column < 0 || column >= size;
    }


}