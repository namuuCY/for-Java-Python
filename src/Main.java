import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

public class Main {
    // https://www.acmicpc.net/problem/34073
    // DORO

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer( br.readLine().strip() );

        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < N; i++) {
            if (i != 0) sb.append(" ");
            sb.append(st.nextToken() + "DORO");
        }

        System.out.println(sb);
    }

}