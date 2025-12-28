import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    // https://www.acmicpc.net/problem/14725
    // 14725 개미굴

    // 입력으로 트리를 구성하고, DFS로 출력하면 된다
    // 이름순이라고 했으니 각각의 인접리스트에서 정렬을 이름순으로 해줘야 하고
    // 이름 <-> 인덱스 를 저장하는 맵이 있으면 될듯???... -> 불가능. 이름이 중복 될 수 있음.
    // 입력을 다 받고, 받으면서 각 높이별 리스트 생성
    // 받은 입력 토대로 인접 리스트 완성하기
    // 입력은 일단 이름은 names에 저장, 인접리스트는 당장 완성하는게 아니라
    // 일단 입력을 다 받은 후에


    static int N;
    static Node root;

    static class Node {
        Map<String, Node> children = new TreeMap<>();
    }

    static void dfs(Node current, StringBuilder sb, int count) {
        for (String n : current.children.keySet()) {
            for (int i = 0; i < count ; i++) {
                sb.append("--");
            }
            sb.append(n);
            sb.append("\n");
            dfs(current.children.get(n), sb, count + 1);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());

        root = new Node();

        while (N-- > 0) {
            String[] st = br.readLine().strip().split(" ");
            int temp = Integer.parseInt(st[0]);
            Node current = root;
            for (int i = 1; i <= temp ; i++) {
                String start = st[i];
                current.children.putIfAbsent(start, new Node());
                current = current.children.get(start);
            }
        }

        StringBuilder sb = new StringBuilder();
        dfs(root, sb, 0);

        bw.write(sb.toString());
        bw.flush();
    }
}