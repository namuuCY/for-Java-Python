import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/1725)
    // BOJ 1725 히스토그램




    static int N;
    static int[] arr;
    static int area = 0;
    static Stack<Integer> idxS = new Stack<>();

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        arr = new int[N + 2];

        for (int i = 0; i < N ; i++) {
            arr[i + 1] = Integer.parseInt(br.readLine());
        }

        area = 0;
        idxS.push(0);

        // 2 1 4 5 4 4 4
        //  아 이거 인덱스 필요하다
        // 2 (2)
        //   2 (1)
        //    4 (1 4)
        //      8 (1 4 5)
        //        8 (1)
        //          8 (1 3)
        //            8 (1 3)
        //              8 (1 3)
        //                9 ()

        for (int i = 1 ; i < N + 2 ; i++) {
            while (!idxS.empty()) {
                int top = idxS.peek();

                if (arr[top] <= arr[i]) break;

                idxS.pop();
                area = Math.max(area, arr[top] * (i - idxS.peek() - 1));
            }
            idxS.push(i);
        }

        System.out.println(area);

    }
}
