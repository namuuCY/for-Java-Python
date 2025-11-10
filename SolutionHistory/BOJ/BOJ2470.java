import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/2470)
    // 2470 : 두 용액

    // 맨처음 봤을때 조합인가? 싶었는데
    // N : 10만 -> 이러면 시간복잡도 50억 넘어가서 시간초과
    // 가능한 것 : 투포인터 가능해보임.

    // 숫자 범위가 -10억 ~ 10억 => 이분탐색 생각해야함. ?

    // 다 음수면?
    //  st, ed 오른쪽으로 갈수록 ok 임.
    //  아 이거 설마 이분 탐색?

    // 다 양수면?
    //   -99, -2, -1, 4, 98
    // -6 -5 -4 -3 -2
    // -8 => 값 키워야하면 왼쪽 움직
    // -7 -4 1 2 5

    // -10 -6 -2 1 8

    // -10 8
    // -6 8
    // -6 1
    // -2 1



    // 투포인터로 특성값을 특정할 수가 없다는게 문제... ?


    // 투포인터 가 맨 앞과 맨 뒤부터 시작하면 되는 문제였다.


    static int N;
    static int ansMin;
    static int ansMax;




    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        int[] numbers = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N ; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(numbers);

        int start = 0;
        int end = N - 1;

        int lowestSp = Integer.MAX_VALUE;
        int currentSp;


        while (start != end) {
            currentSp = numbers[start] + numbers[end];
            int currentAbsSp = Math.abs(currentSp);
            if (currentAbsSp < lowestSp) {
                ansMin = start;
                ansMax = end;
            }
            lowestSp = Math.min(currentAbsSp, lowestSp);

            if (currentSp > 0) {
                end -= 1;
            } else if (currentSp < 0) {
                start += 1;
            } else {
                ansMin = start;
                ansMax = end;
                break;
            }
        }

        System.out.println(numbers[ansMin] + " " + numbers[ansMax]);
        // 출력해야 하는 두 용액은 특성값의 오름차순으로 출력
        // 특성값이 0에 가장 가까운 용액을 만들어내는 경우가 두 개 이상일 경우에는 그 중 아무것이나 하나를 출력한다.
        // 특성값이 -1, 1 일 경우에는 아무거나 출력

    }
}
