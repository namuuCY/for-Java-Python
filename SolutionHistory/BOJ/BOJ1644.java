import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/1644)
    // 1644 : 소수의 연속합

    // 소수 : 소수 1가지는 가지고 있음.
    // 1 : 불가
    // 2, 3: 2, 3
    // 4 : 2 + 2
    // 5 : 5, 2 + 3
    // 6 : 3 + 3 안됨

    // "한 소수는 반드시 한번만 덧셈에 사용 될 수 있다."

    // 그러면 투포인터 맞네


    // 200만 * 1400 ?
    // [  ] 주어진 숫자보다 작으면 뒷칸 늘리기, 주어진 숫자보다 크면 앞칸 줄이기
    // 이 방법은 시간 복잡도 O(N)

    static int N;
    static int lastIdx;
    static int currentSum;
    static int answer = 0;
    static List<Integer> primes = new ArrayList<>();
    static boolean[] isPrime = new boolean[4000002];


    // 아래 로직대로면 1~N까지 소수 개수 세는데 NloglogN 시간 복잡도
    static void initPrimes() {
        Arrays.fill(isPrime, true);

        for (int i = 2; i * i <= N; i++ ) {
            if (!isPrime[i]) continue;
            for (int j = i * i ; j <= N ; j += i) {
                isPrime[j] = false;
            }
        }
        for (int i = 2; i <= N ; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
    }


    // 3일 경우
    // 2
    // 2 3 5 7 11 13 17 19 23 29 31 37 41 // 41 찾기
    // 2 3 5 7 11 13 // 41
    //  3 5 7 11 13 // 39
    //  3 5 7 11 13 17 // 56
    //   5 7 11 13 17 // 53
    //    7 11 13 17 // 48
    //    11 13 17 // 41
    //    11 13 17 19 // 41

    // 현재 합이 작거나 같으면 end 올리고, 크면 start를 올린다



    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        if (N == 1) {
            System.out.println(0);
            return;
        }

        if (N == 2) {
            System.out.println(1);
            return;
        }

        initPrimes();
        lastIdx = primes.size();

        int startIdx = 0;
        int endIdx = 0;
        currentSum = primes.get(0);

        while (startIdx < lastIdx) {

            if (currentSum == N) {
                endIdx += 1;


                answer += 1;

                if (endIdx >= lastIdx) break;

                currentSum += primes.get(endIdx);

            } else if (currentSum < N) {
                endIdx += 1;

                if (endIdx >= lastIdx) break;

                currentSum += primes.get(endIdx);

            } else {
                currentSum -= primes.get(startIdx);
                startIdx += 1;

            }
        }

        System.out.println(answer);
    }
}
