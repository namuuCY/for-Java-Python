import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // BOJ 15787 (https://www.acmicpc.net/problem/15787)
    // N개의 기차
    // M개의 명령 -
    // 기차별로 20개의 좌석
    // (명령: int) (기차 idx <= N) (x번째 좌석)

    // 명령 1 : or -   | (1 << N - 1)
    // 명령 2 : and -   & (11110111)
    // 명령 3 : >>
    // 명령 4 : << (2^20 보다 크면 뺀다)

    // 검사할때 값이 같으면 카운트 안함 -> set로 하자

    //  if - 구문 두번 타는 것 : 분기 연산이 2번씩 들어간다
    // switch - case가 이런 경우에는 분기 연산이 한번이므로 더 빠르다
    // (내 방식보다 50ms 정도 빠르더라)

    static int N; // 기차 개수
    static int M; // 명령 개수

    static int[] carts;



    public static void upsertCommand(int command, int cartNum, int seatNum) {
        if (command == 1) {
            carts[cartNum - 1] |= (1 << (seatNum - 1));
            return;
        }
        carts[cartNum - 1] &= (~(1 << (seatNum - 1 )));

    }

    public static void switchCommand(int command, int cartNum) {
        if (command == 3) {
            carts[cartNum-1] <<= 1;
            carts[cartNum-1] &= (~(1 << 20));
            return;
        }
        carts[cartNum-1] >>= 1;

    }


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer NandM = new StringTokenizer(br.readLine());
        N = Integer.parseInt(NandM.nextToken());
        M = Integer.parseInt(NandM.nextToken());

        carts = new int[N];

        for (int i = 0; i < M ; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int cartNum = Integer.parseInt(st.nextToken());
            if (command <= 2) {
                int seatNum = Integer.parseInt(st.nextToken());
                upsertCommand(command, cartNum, seatNum);
                continue;
            }
            switchCommand(command, cartNum);

        }
        Set<Integer> ansSet = Arrays.stream(carts).boxed().collect(Collectors.toSet());


        System.out.println(ansSet.size());


    }
}
