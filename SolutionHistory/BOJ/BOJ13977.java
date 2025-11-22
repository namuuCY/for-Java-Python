import java.io.*;


public class Main {

    // Source(https://www.acmicpc.net/problem/13977)
    // 13977 이항계수 와 쿼리

    // 자연수 N 정수 K

    // 첫째 줄에
    //\(M\)이 주어진다. (1 ≤
    //\(M\) ≤ 100,000)
    //
    //둘째 줄부터
    //\(M\)개의 줄에
    //\(N\)과
    //\(K\)가 주어진다. (1 ≤
    //\(N\) ≤ 4,000,000, 0 ≤
    //\(K\) ≤
    //\(N\))

    // K 가 0이면 값 1
    // 자연수가 1이면 K값에 관계없이 값은 1

    // 수학 개념
    // 모듈로 역원 - 페르마의 소정리

    // n C k = n! / (n-k)! * (k)! = n .... n-k+1 / k!

    // 6 C 2 = 6 * 5 / 2!

    static long[] factorials;
    static long[] factorialInverses;

    static long PRIME = 1_000_000_007;

    static void init() {

        // 팩토리얼 계산 / DP에 저장
        factorials[0] = 1;
        factorials[1] = 1;
        factorialInverses[0] = 1;

        for (int i = 2; i < 4_000_001 ; i++) {
            factorials[i] = (factorials[i - 1] * i) % PRIME;
        }

        // 모듈로 계산  -> 400만!의 모듈로 먼저 계산 후 차례로 곱하면서 DP 채우기
        factorialInverses[4_000_000] = pow(factorials[4_000_000], PRIME - 2);

        for (int i = 3_999_999 ; i > 0 ; i--) {
            factorialInverses[i] = factorialInverses[i + 1] * (i + 1) % PRIME;
        }

    }

    static long pow(long n, long p) {
        if (p == 0) return 1;
        if (p == 1) return n % PRIME;

        long temp = pow(n, p / 2);

        if (p % 2 == 1) {
            return ((temp * temp) % PRIME) * n % PRIME;
        }
        return (temp * temp) % PRIME;
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


        factorials = new long[4_000_001];
        factorialInverses = new long[4_000_001];

        init();

        int m = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        while (m-- > 0) {
            String[] nk = br.readLine().strip().split(" ");
            int n = Integer.parseInt(nk[0]);
            int k = Integer.parseInt(nk[1]);

            if (n == 1 || k == 0) {
                sb.append(1).append("\n");
                continue;
            }
            long ans = (factorials[n] * factorialInverses[k] % PRIME) * factorialInverses[n-k] % PRIME;
            // 이항계수 계산
            sb.append(ans).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
    }
}
