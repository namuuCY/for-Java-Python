import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    // Source(https://www.acmicpc.net/problem/19577)
    // 19577 수학은 재밌어
    // n ≤ 1,000,000,000 까지

    static int n;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());

        // n의 소인수를 구한다

        List<Integer> divider = new ArrayList<>();
        Map<Integer, Integer> eulerPhi = new HashMap<>();


        if (n == 1) {
            System.out.println(1);
            return;
        }

        if (n == 2) {
            System.out.println(2);
            return;
        }

        divider.add(1);
        eulerPhi.put(1, 1);

        int temp = n;
        for (int i = 2 ; i * i <= n ; i++) {
            if (temp % i != 0) continue;

            List<Integer> tempList = new ArrayList<>();
            int q = i;
            while (temp % i == 0) {
                tempList.add(q);
                temp /= i;
                q *= i;
            }
            List<Integer> copyList = new ArrayList<>();
            copyList.addAll(divider);

            int pMinus1 = i - 1;
            for (int j = 0 ; j < tempList.size(); j++) {

                eulerPhi.put(tempList.get(j), pMinus1);
                pMinus1 *= i;
            }

            for (int j : copyList) {
                for (int k : tempList) {
                    divider.add(j * k);
                    int ep = eulerPhi.get(j) * eulerPhi.get(k);
                    eulerPhi.put(j * k, ep);
                }
            }
        }

        if (temp > 1 && temp != n) {
            List<Integer> copyList = new ArrayList<>(divider);
            eulerPhi.put(temp, temp - 1);

            for (int j : copyList) {
                divider.add(j * temp);
                int ep = eulerPhi.get(j) * eulerPhi.get(temp);
                eulerPhi.put(j * temp, ep);
            }
        }

        // n의 모든 약수 구하기 (이를 x라 하자)

        List<Integer> ansList = new ArrayList<>();

        eulerPhi.entrySet().stream()
                .forEach((kv) -> {
                    int x = kv.getKey();
                    int epX = kv.getValue();
                    if (epX == (n/x)) {
                        ansList.add(x);
                    }
                });

        // n / x 값 구하기
        List<Integer> sortedList = ansList.stream().sorted().collect(Collectors.toList());

        if (sortedList.isEmpty()) {
            System.out.println(-1);
            return;
        }


        System.out.println(sortedList.get(0));
        // phi(x) 값 그거에 맞춰서 구하기
    }
}
