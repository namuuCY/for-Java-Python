import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // BOJ 2064 (https://www.acmicpc.net/problem/2064)
    // ip주소 n개
    // n개 끼리 sort -> O(nlogn)
    // bit 연산이기 때문에 연산으로 인해 생기는 시간복잡도 거의 무시가능
    //
    static int n;
    static long[] network;
    static long subnetMask = 0L;
    static long minAddr = 0L;

    static long parseAddr(int a1, int a2, int a3, int a4) {
        return ((long) a1 << 24)
                + ((long) a2 << 16)
                + ((long) a3 << 8)
                + ((long) a4);
    }

    static String longToAddr(long addr) {
        StringBuilder sb = new StringBuilder();
        sb.append((addr >> 24));
        sb.append(".");
        sb.append((addr >> 16) & ((1L << 8) - 1));
        sb.append(".");
        sb.append((addr >> 8) & ((1L << 8) - 1));
        sb.append(".");
        sb.append(addr & ((1L << 8) - 1));
        return sb.toString();
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        network = new long[n];

        for (int i = 0; i < n ; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), ".");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            network[i] = parseAddr(a,b,c,d);
        }

        Arrays.sort(network);
        long min = network[0];
        long max = network[n-1];

        // 디버그 해보니까 아래는 정확함
//        System.out.println(longToAddr(min));
//        System.out.println(longToAddr(max));

        for (int j = 31; j >= 0 ; j--) {

            long cur = ~(min ^ max) & (1L << j);
            if (cur == 0) break;
            subnetMask += cur;
            minAddr += min & (1L << j);

        }

        System.out.println(longToAddr(minAddr));
        System.out.println(longToAddr(subnetMask));


    }
}
