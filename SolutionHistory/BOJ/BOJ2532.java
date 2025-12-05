import java.io.*;
import java.util.*;

public class Main {

    // Source(https://www.acmicpc.net/problem/2532)
    // 2532 먹이사슬

    static class Animal implements Comparable<Animal> {
        int id, l, r;

        public Animal(int id, int l, int r) {
            this.id = id;
            this.l = l;
            this.r = r;
        }

        @Override
        public int compareTo(Animal o) {
            if (this.l != o.l) {
                return this.l - o.l;
            }
            return o.r - this.r;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        List<Animal> animals = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            animals.add(new Animal(id, l, r));
        }


        Collections.sort(animals);

        List<Integer> list = new ArrayList<>();

        Animal prev = null;
        for (Animal cur : animals) {
            if (prev != null && prev.l == cur.l && prev.r == cur.r) {
                continue;
            }
            list.add(-cur.r);
            prev = cur;
        }

        int[] dp = new int[list.size()]; // LIS 길이별 끝값을 저장
        int len = 0;

        for (int val : list) {
            // dp 배열이 비었거나, 마지막 값보다 크거나 같으면 그냥 추가 (이어 붙이기)
            if (len == 0 || dp[len - 1] <= val) {
                dp[len++] = val;
            } else {
                // 들어갈 자리를 찾아서 교체 (Upper Bound 개념 필요)
                // val보다 큰 첫 번째 값을 찾아서 val로 덮어씌움
                int idx = upperBound(dp, 0, len, val);
                dp[idx] = val;
            }
        }

        System.out.println(len);
    }
    static int upperBound(int[] arr, int left, int right, int key) {
        while (left < right) {
            int mid = (left + right) / 2;
            if (arr[mid] <= key) { // 작거나 같으면 오른쪽을 봐야 함 (같은 값 허용하므로)
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}