import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/5676
    // 5676 음주 코딩
    static int N;
    static int K;
    static int[] number;
    static int[] tree;


    static void init(int nodeIdx, int start, int end) {
        if (start == end) {
            tree[nodeIdx] = number[start];
            return;
        }
        init(nodeIdx * 2 , start, (start + end) / 2);
        init(nodeIdx * 2 + 1, (start + end) / 2 + 1, end);
        // todo : 내가 놓친것
        tree[nodeIdx] = tree[nodeIdx *2 ] * tree[nodeIdx * 2 + 1];
    }

    static int query(int nodeIdx, int left, int right, int start, int end) {
        if (right < start || end < left) return 1; // < 영향을 주지 말아야 하므로
        if (left <= start && end <= right) return tree[nodeIdx];
        int leftQuery = query(nodeIdx * 2, left, right, start, (start + end) / 2);
        int rightQuery = query(nodeIdx * 2 + 1, left, right, (start + end) / 2 +1 , end);
        return leftQuery * rightQuery;
    }

    // update를 해야하는 케이스 : updateValue가 start와 end 사이에 있을때
    static void update(int nodeIdx, int targetIdx, int updateValue, int start, int end) {
        if (start == end) {
            if (start == targetIdx) {
                tree[nodeIdx] = updateValue;
            }
            return;
        }
        if (targetIdx < start || end < targetIdx) return;
        update(nodeIdx * 2, targetIdx, updateValue, start, (start + end) / 2);
        update(nodeIdx * 2 + 1, targetIdx, updateValue, (start + end) / 2 + 1, end);
        // 이 부분을 내가 생략해버림
        tree[nodeIdx] = tree[nodeIdx * 2] * tree[nodeIdx * 2 + 1];
    }


    static int convert(int input) {
        if (input > 0) {
            return 1;
        } else if (input < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    static String convertToAns(int input) {
        if (input > 0) {
            return "+";
        } else if (input < 0) {
            return "-";
        } else {
            return "0";
        }
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input;
        StringBuilder sb = new StringBuilder();

        while ((input = br.readLine()) != null) {
            input = input.trim();
            if (input.isEmpty()) break;

            StringTokenizer st = new StringTokenizer(input);
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            number = new int[N];

            int height = ((int) Math.ceil(Math.log(N) / Math.log(2))) + 1;
            int treeSize = (1 << height);
            tree = new int[treeSize];

            st = new StringTokenizer(br.readLine());
            for (int i = 0 ; i < N; i++) {
                int numberInput = Integer.parseInt(st.nextToken());
                number[i] = convert(numberInput);
            }

            init(1, 0, N - 1);
            while (K-- > 0) {
                st = new StringTokenizer(br.readLine());
                String type = st.nextToken();
                int param1 = Integer.parseInt(st.nextToken()) - 1;
                // 변경 명령
                if ("C".equals(type)) {
                    int param2 = Integer.parseInt(st.nextToken());
                    int converted = convert(param2);
                    update(1, param1, converted, 0, N-1);
                } else {
                    // 쿼리 명령
                    int param2 = Integer.parseInt(st.nextToken()) - 1;
                    int q = query(1, param1, param2, 0, N-1);
                    sb.append(convertToAns(q));
                }
            }
            sb.append("\n");
        }
//        sb
        System.out.println(sb);

    }
}