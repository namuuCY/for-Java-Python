import java.io.*;
import java.util.*;

public class Main {

    // BOJ 11723 (https://www.acmicpc.net/problem/11723)
    // 집합
    // switch - case 를 lambda로 표현하는건 자바 12인가 13때부터
    // int[] 으로 할 경우 20번 연산 해야함 -> 300만번 할 경우 6천만번 연산 필요

    static int count;
//    static int[] bitmask;


//    public static void command(int[] bitmask, String command,  StringTokenizer st) {
//        switch(command) {
//            case "add" : {
//                int number = Integer.parseInt(st.nextToken());
//                bitmask[number - 1] = 1;
//            }
//            break;
//            case "remove" : {
//                int number = Integer.parseInt(st.nextToken());
//                bitmask[number - 1] = 0;
//            } break;
//            case "check" : {
//                int number = Integer.parseInt(st.nextToken());
//                System.out.println(bitmask[number - 1]);
//            }
//            break;
//            case "toggle" : {
//                int number = Integer.parseInt(st.nextToken());
//                bitmask[number - 1] = 1 ^ bitmask[number - 1];
//            }
//            break;
//            case "all" : {
//                for (int j = 0 ; j < 20 ; j++) {
//                    bitmask[j] = 1;
//                }
//                break;
//            }
//            default : {
//                for (int j = 0 ; j < 20 ; j++) {
//                    bitmask[j] = 0;
//                }
//                break;
//            }
//        }
//    }





    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        count = Integer.parseInt(br.readLine());
        int state = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0 ; i < count ; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String command = st.nextToken();


            if ("add".equals(command))  {
                int number = Integer.parseInt(st.nextToken());
                state |= (1<< (number - 1));
            }

            else if ("remove".equals(command)) {
                int number = Integer.parseInt(st.nextToken());
                // idea : 111110111111 과 and 연산
                state &= (~(1 << (number - 1)));
            }
            else if ("check".equals(command)) {
                int number = Integer.parseInt(st.nextToken());
                sb.append((state >> (number - 1)) & 1).append('\n');
            }

            else if ("toggle".equals(command)) {
                int number = Integer.parseInt(st.nextToken());
                state ^= (1 << (number - 1));
            }
            else if ("all".equals(command)) {
                state = 0xfffff;
            }
            else {
                state = 0;
            }
        }


        System.out.println(sb);
    }
}
