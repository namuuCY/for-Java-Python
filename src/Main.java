import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String binaryStr = br.readLine();

        // 1. 문자열 길이가 3의 배수가 되도록 앞에 '0'을 붙여줍니다.
        int len = binaryStr.length();
        if (len % 3 == 1) {
            binaryStr = "00" + binaryStr;
        } else if (len % 3 == 2) {
            binaryStr = "0" + binaryStr;
        }

        StringBuilder sb = new StringBuilder();

        // 2. 3자리씩 끊어서 8진수 숫자로 변환합니다.
        for (int i = 0; i < binaryStr.length(); i += 3) {
            String chunk = binaryStr.substring(i, i + 3);

            // 각 자리의 문자를 숫자로 변환하여 가중치(4, 2, 1)를 곱해 더합니다.
            int octalDigit = (chunk.charAt(0) - '0') * 4
                    + (chunk.charAt(1) - '0') * 2
                    + (chunk.charAt(2) - '0') * 1;

            sb.append(octalDigit);
        }

        // 3. 결과 출력
        System.out.println(sb.toString());
    }
}