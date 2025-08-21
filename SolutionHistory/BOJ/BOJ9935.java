import java.io.*;
import java.util.*;

public class Main {

    // BOJ 9935 (https://www.acmicpc.net/problem/9935)

    // 문자열 길이 100만보다 작거나 같다
    // cccccccccc... cc4444444....4444
    // 이런 경우라면 단순히 메서드로 찾아서 하면
    // 시간복잡도 50만 * 499999 / 2 => 시간초과

    // 하나씩 넣으면서 들어올때 해결하기 => O(N)
    // Stack 쓰지말라는 얘기가 있네?
    // => Deque, 구현체는 ArrayDeque, LinkedList 사용

    // 자바에서 Deque를 구현하는 방법은 크게 LinkedList와 ArrayDeque가 있는데,

    // 스택의 size가 엄청 커질 가능성이 있고 size의 변동성이 매우 큰 경우
    // 즉각적인 메모리 공간 확보를 위해선 LinkedList방식이 적절하고,

    // 그렇지 않은 경우는 자체 메모리 소모량이 적고 iterate의 효율이 좋은 ArrayDeque를 사용하면 된다.



    static Deque<Character> deque = new LinkedList<>();
    static char[] exp;
    static char last;
    static int len;

    // Deque는 메서드이름이 직관적이어서 좋다
    // addFirst/Last
    // removeFirst/Last
    // getFirst/Last (remove는 하지 않음)


    // 행위를 정의하자
    // 마지막 글자가 나오면
    static void checkExplodeOrUpdate(char c) {

        if (deque.size() < len - 1) {
            deque.addLast(c);

            return;
        }

        Deque<Character> temp = new LinkedList<>();


        for (int i = len - 2; i >= 0 ; i--) {
            char cur = deque.getLast();

            if (cur != exp[i]) {
                // 다시 다 집어넣기
                int tempLen = temp.size();
                for (int j = 0 ; j < tempLen ; j++) {
                    deque.addLast(temp.removeLast());
                }
                deque.addLast(c);
                // 이거는 필수다
                temp.clear();
                return;
            } else {
                deque.removeLast();
                temp.addLast(cur);

            }
        }
        temp.clear();
    }




    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[] raw = br.readLine().toCharArray();
//        System.out.println(raw);
        exp = br.readLine().toCharArray();
//        System.out.println(exp);
        len = exp.length;
        last = exp[len - 1];


        for (char cur : raw) {
            if (!(cur == last)) {
                deque.addLast(cur);
                continue;
            }
            checkExplodeOrUpdate(cur);
        }


        if (deque.isEmpty()) {
            System.out.println("FRULA");
            return;
        }

        StringBuilder sb = new StringBuilder();
        while (!deque.isEmpty()) {
            sb.append(deque.removeFirst());
        }
        System.out.println(sb);
    }
}


// 1차시도 실패
// 반례를 모르겠어서 보니까 길이가 긴 경우에 뭔가 빠지는 케이스가 있음.

// 2차시도 if - else구분으로 명확히 하기
// 안됨. 뭔가 문제가 있는데

// 3차시도 gemini한테 물어봤더니
//                 for (int j = 0 ; j < temp.size() ; j++) {
//                    deque.addLast(temp.removeLast());
//                }

//                 int tempLen = temp.size();
//                for (int j = 0 ; j < tempLen ; j++) {
//                    deque.addLast(temp.removeLast());
//                }
// 생각해보니 저렇게 메서드 계속 호출하면 안될듯... array는 되겠지만 저거는 안됨


