import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/33557
    // 곱셈을 누가 이렇게 해 ㅋㅋ

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // 테스트 케이스 개수
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String A_str = st.nextToken();
            String B_str = st.nextToken();

            // 1. 실제 곱셈 결과 계산
            long A = Long.parseLong(A_str);
            long B = Long.parseLong(B_str);
            long realResult = A * B;

            // 2. 잘못된 곱셈 결과 문자열 생성
            String fakeResult = getFakeMultiplication(A_str, B_str);

            // 3. 비교
            if (String.valueOf(realResult).equals(fakeResult)) {
                sb.append("1\n");
            } else {
                sb.append("0\n");
            }
        }
        System.out.print(sb);
    }

    // 잘못된 곱셈 방식을 구현한 함수
    private static String getFakeMultiplication(String s1, String s2) {
        StringBuilder sb = new StringBuilder();

        int len1 = s1.length();
        int len2 = s2.length();
        int maxLen = Math.max(len1, len2);

        // 뒤에서부터(일의 자리부터) 앞으로 이동하며 처리
        // 결과는 앞에 붙여야 하므로 insert(0, ...) 사용
        for (int i = 0; i < maxLen; i++) {
            int idx1 = len1 - 1 - i;
            int idx2 = len2 - 1 - i;

            // 두 수 모두 해당 자리에 숫자가 있는 경우 -> 곱하기
            if (idx1 >= 0 && idx2 >= 0) {
                int n1 = s1.charAt(idx1) - '0';
                int n2 = s2.charAt(idx2) - '0';
                sb.insert(0, n1 * n2);
            }
            // s1만 있는 경우 -> s1 숫자 그대로
            else if (idx1 >= 0) {
                sb.insert(0, s1.charAt(idx1));
            }
            // s2만 있는 경우 -> s2 숫자 그대로
            else if (idx2 >= 0) {
                sb.insert(0, s2.charAt(idx2));
            }
        }

        return sb.toString();
    }
}