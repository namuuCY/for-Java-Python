import java.io.*;
import java.util.*;

public class Main {

    // BOJ 1036 (https://www.acmicpc.net/problem/1036)
    // N 이 최대 50개, 수의 길이도 최대 50
    // k : 0~36 까지

    // 36진수니까 생각도 36진수처럼 하면 되나?

    // 전략 : 받는 문자 별로 Z로 바꾸면 추가되는 값 계산
    // 원래 값의 합은 그때 그때 계산(고정 값)

    // ㅅㅂ 디버그가 굉장히 어려운데?

    // sum 메소드에서 cur[i+1]에 더해지는 올림값은
    // cur[i]가 변경되기 전의 값을 기준으로 계산해서 문제발생 정신이 없네;

    // try 1 : 문자는 앞에서 부터 받는데 gap에서 진법 계산도 앞에서 부터 하니까 오류남

    // try 2 : k값을 받는 과정이 없었음

    // try 3 : 연쇄 올림이 일어나는 경우도 있음 ZZZZZZZZZZ 인 경우 + 1만 해도 다음자리, 다음자리
    // 가 추가 된다...

    // try 4 : normalize 작업을 추가함 연쇄올림이 2번이상 있는 경우도 있어서

    // try 5 : gap을 내림차순 정렬하기 전에도 normalize를 제대로 해두어야 함

    // try 6 : normalize를 제때 넣어놓지 않았음. sum을 계산하고 나서 normalize를 넣어놨어야했는데

    static int N;
    static int k;

    static List<Digit> gaps;
    static int[] cur = new int[62];

    static void sum(int[] b) {
        for (int i = 0; i < 61 ; i++ ) {
            int n = cur[i] + b[i];
            cur[i] = n % 36;
            cur[i + 1] += n / 36;
        }
    }

    static void calGap(int idx, char c) {
        int location = charToInt(c);
        int n = gaps.get(location).num[idx] + 35 - location;
        gaps.get(location).num[idx] = n % 36;
        gaps.get(location).num[idx + 1] += n / 36;
    }

    static void calCur(int idx, char c) {
        int intValue = charToInt(c);
        int n = cur[idx] + intValue;
        cur[idx] = n % 36;
        cur[idx + 1] += n / 36;

    }


    static class Digit implements Comparable<Digit> {
        int[] num = new int[62];

        public int compareTo(Digit o) {
            for (int i = 61; i >= 0 ; i--) {
                if (this.num[i] == o.num[i]) continue;
                return this.num[i] - o.num[i];
            }
            return 0;
        }

        public Digit(char[] word) {

            for (int i = 0 ; i < 50 ; i ++) {
                num[i] = charToInt(word[i]);
            }
        }

        public Digit() {

        }

    }

    static int charToInt(char c) {
        if ( ((int) '0' <= (int) c) && ((int) '9' >= (int) c)) {
            return (int) c - (int) '0';
        }
        return 10 + (int) c - (int) 'A';
    }

    static char intToChar(int i) {
        if ((i >= 0) && (i <= 9)) {
            return (char) (i + (int) '0');
        }
        return (char) (i - 10 + (int) 'A');
    }

    static void normalize(int[] num) {
        for (int i = 0; i < num.length - 1; i++) {
            if (num[i] >= 36) {
                num[i+1] += num[i] / 36; // 다음 자리에 올림값 더하기
                num[i] %= 36;          // 현재 자리는 나머지 값만 남기기
            }
        }
    }



    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        gaps = new ArrayList<>();
        for (int j = 0 ; j < 36 ; j++ ) {
            gaps.add(new Digit());
        }

        for (int i = 0 ; i < N ; i ++) {
            char[] word = br.readLine().toCharArray();
            int len = word.length;
            for (int j = len - 1; j >= 0 ; j--) {
                // 맨 뒤부터 계산
                calGap(len - 1 - j, word[j]);
                calCur(len - 1 - j, word[j]);

                // pos가 실제 자릿값(36의 거듭제곱)을 의미하는 인덱스가 됨
//                int pos = len - 1 - j;
//                char c = word[pos];
//                calGap(pos, c);
//                calCur(pos, c);
            }
        }

        k = Integer.parseInt(br.readLine());
        for (Digit gap : gaps) {
            normalize(gap.num);
            normalize(gap.num);
            normalize(gap.num);
//            System.out.println(Arrays.toString(gap.num));
        }

//        System.out.println(gaps);
        gaps.sort(Digit::compareTo);
//        System.out.println(gaps);
        Collections.reverse(gaps);
//        System.out.println(gaps);


        for (int m = 0 ; m < k ; m++ ) {
            int[] gap = gaps.get(m).num;
            sum(gap);
        }
        normalize(cur);
        normalize(cur);
        normalize(cur);

        StringBuilder sb = new StringBuilder();
        int zidx = 0;
        for (int idx = 59; idx >= 0 ; idx--) {
            if (cur[idx] == 0) continue;
            zidx = idx;
            break;
        }

        for (int cidx = zidx; cidx >= 0 ; cidx--) {
            sb.append(intToChar(cur[cidx]));
        }

        System.out.println(sb);

    }
}
