import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/1034
    // 램프

    static int N, M, K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine().strip());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Map<String, Integer> lamp = parseInput(br);
        K = Integer.parseInt(br.readLine());

        int ans = lamp.entrySet().stream()
                .map((entry) -> calculatePossibility(entry, K))
                .max(Integer::compareTo)
                .orElse(0);

        System.out.println(ans);
    }

    private static Map<String, Integer> parseInput(BufferedReader br) throws IOException {
        Map<String, Integer> digitCounts = new HashMap<>();
        for (int i = 0 ; i < N; i++) {
            String originString = br.readLine().strip();
            digitCounts.merge(originString, 1, Integer::sum);
        }
        return digitCounts;
    }

    private static Integer calculatePossibility(Map.Entry<String, Integer> entry, int K) {
        char[] stringToCharArr = entry.getKey().toCharArray();
        // 먼저, 필요한 숫자를 셈
        int needToPossible = 0;
        for (char c : stringToCharArr) {
            if (c == '0') needToPossible++;
        }

        if (needToPossible > K) return 0;
        return (needToPossible - K) % 2 == 0
                ? entry.getValue()
                : 0;
    }




}