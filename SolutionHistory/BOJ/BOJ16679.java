import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/16679
    // 16679 Back to the Bones


    // dp[n][k] -> 주사위 n개로 합이 k개가 되는 경우의 수
    static long[][] dp = new long[21][121];
    static List<Dice> dlist = new ArrayList<>();
    static StringBuilder sb = new StringBuilder();



    private static void init() {
        for (int i = 1 ; i <= 6; i++) {
            dp[1][i] = 1;
        }

        for (int dices = 2; dices <= 20 ; dices++) {
            for (int sum = 1; sum <= 120; sum++) {
                for (int number = 1 ; number <= 6; number++ ) {
                    if (sum - number < 0) continue;
                    dp[dices][sum] += dp[dices - 1][sum - number];
                }
            }
        }
    }

    static class Dice implements Comparable<Dice> {
        int dice;
        int idx;

        Dice(int dice, int idx) {
            this.dice = dice;
            this.idx = idx;
        }

        public int compareTo(Dice that) {
            if (that.dice != this.dice) return that.dice - this.dice;
            return this.idx - that.idx;
        }
    }

    static long pow(int n) {
        if (n == 1) return 6;
        return 6 * pow(n - 1);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        init();
        int T = Integer.parseInt(br.readLine());

        while (T -- > 0) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            StringTokenizer st = new StringTokenizer(br.readLine());
            int tempInputSum = 0;
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                int d = Integer.parseInt(st.nextToken());
                dlist.add(new Dice(d, i));
                tempInputSum += d;
            }
            Collections.sort(dlist);
            int rest = K;
            long placeholder = 1;
            long ans = 0;
            List<Integer> diceIdx = new ArrayList<>();


            // 아무것도 뺴지 않은 상태에서 값 체크
            if (tempInputSum >= K) {
                sb.append(pow(N));
                sb.append("\n");
                for (int i = 0; i < N; i++) {
                    sb.append(0);
                    sb.append(" ");
                }
                dlist.clear();
                continue;
            }

            while (!dlist.isEmpty() && dlist.get(0).dice == 6) {
                Dice current = dlist.remove(0);
                rest -= current.dice;
                placeholder *= 6;
            }


            // 6이 다 빠진 상태로 시작
            int size = dlist.size();
            while (size > 0) {
                // 나머지 개수의 주사위만 가지고 rest값을 만들 경우의 수?
                long temp = 0;
                for (int sum = rest ; sum <= 6 * (size) ; sum++) {
                    if (sum - rest < 0 || (sum - rest) >= 6 * (size)) continue;
                    temp += dp[size][sum];
                }
                temp *= placeholder;

                if (ans < temp) {
                    ans = temp;
                    diceIdx.clear();
                    for (Dice d : dlist) {
                        diceIdx.add(d.idx);
                    }
                }
                // 다음을 위한 조건
                if (dlist.isEmpty()) break;
                Dice current = dlist.remove(0);
                size-- ;
                rest -= current.dice;
                placeholder *= 6;
            }

            sb.append(ans);
            sb.append("\n");

            for (int i = 0; i < N; i++) {
                sb.append((diceIdx.contains(i)) ? 1: 0);
                sb.append(" ");
            }
            dlist.clear();
        }
        System.out.println(sb);
    }
}