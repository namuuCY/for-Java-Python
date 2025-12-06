import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/24050)
    // 24050 정원 (Hard)

    // 뤼카의 정리
    static int p = 2;
    static int[][] pascal = new int[][]{new int[]{1, 0}, new int[]{1, 1}};
    static int N;
    static int[] nList;
    static int M;
    static int[] mList;

    static int lucas(int n, int k) {
        if (n < k) return 0;
        if (k == 0) return 1;

        return lucas(n/2, k/2) * pascal[n % 2][k % 2];
    }



    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        nList = new int[N];
        mList = new int[M];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            nList[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            mList[i] = Integer.parseInt(st.nextToken());
        }

        int ans = 0;

        // N 행 M 열
        //(N+M-2, N-1) x **1**
        //(N+M-3, N-1) x **2**
        //(N+M-4, N-1) x **3**
        //(N+M-5, N-1) x **4**
        //...
        //(N-1, N-1) x (**M**)
        //(N+M-2, M-1) x 1
        //(N+M-3, M-1) x 2
        //(N+M-4, M-1) x 3
        //(N+M-5, M-1) x 4
        //...
        //(M-1, M-1) x (N)

        for (int i = 0 ; i < N ; i++) {
            ans = (ans + (lucas(N + M - 2 - i, M - 1) * nList[i])) % 2;
//            System.out.println("조합 ("+ (N+M-2-i) + ", "+ (M - 1)+ ") = " + lucas(N + M - 2 - i, M - 1));
        }

        for (int i = 0 ; i < M ; i++) {
            ans = (ans + (lucas(N + M - 2 - i, N - 1) * mList[i])) % 2;
        }

        System.out.println(ans);


    }

}