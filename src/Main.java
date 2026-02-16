import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/5698

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            String line = br.readLine();

            // 입력 종료 조건
            if (line.equals("*")) {
                break;
            }

            StringTokenizer st = new StringTokenizer(line);
            if (!st.hasMoreTokens()) continue;

            // 첫 번째 단어의 첫 글자를 소문자로 변환하여 기준(target)으로 설정
            char target = Character.toLowerCase(st.nextToken().charAt(0));
            boolean isTautogram = true;

            // 나머지 단어들의 첫 글자와 비교
            while (st.hasMoreTokens()) {
                char current = Character.toLowerCase(st.nextToken().charAt(0));

                if (target != current) {
                    isTautogram = false;
                    break; // 하나라도 다르면 더 이상 검사할 필요 없음
                }
            }

            // 결과 저장
            if (isTautogram) {
                sb.append("Y\n");
            } else {
                sb.append("N\n");
            }
        }

        // 한 번에 출력
        System.out.print(sb.toString());
    }
}