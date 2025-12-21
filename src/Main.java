import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/14244
    // 14244 트리만들기


    //    n과 m이 주어진다. (3 ≤ n ≤ 50, 2 ≤ m ≤ n-1)




    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 리프노드만큼 오른쪽에 추가
        // 차액 - 1 만큼 아래에 추가
        StringBuilder sb = new StringBuilder();

        for (int leaf = 1; leaf <= m ; leaf++) {
            sb.append(0);
            sb.append(" ");
            sb.append(leaf);
            sb.append("\n");
        }

        int nextNode = 1;

        for (int currentNode = m + 1; currentNode < n; currentNode++) {
            sb.append(nextNode);
            sb.append(" ");
            sb.append(currentNode);
            sb.append("\n");
            nextNode = currentNode;
        }

        System.out.println(sb);

    }

}