import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/17502

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        char[] arr = br.readLine().toCharArray();

        int left = 0;
        int right = n - 1;

        // 양 끝에서부터 가운데로 이동하며 검사
        while (left <= right) {
            if (arr[left] == '?' && arr[right] == '?') {
                // 둘 다 ? 이면 임의의 문자(예: 'a')로 채움
                arr[left] = 'a';
                arr[right] = 'a';
            } else if (arr[left] == '?') {
                // 왼쪽만 ? 이면 오른쪽 문자와 똑같이 맞춤
                arr[left] = arr[right];
            } else if (arr[right] == '?') {
                // 오른쪽만 ? 이면 왼쪽 문자와 똑같이 맞춤
                arr[right] = arr[left];
            }

            // 포인터 이동
            left++;
            right--;
        }

        // 완성된 문자열 출력
        System.out.println(new String(arr));
    }
}