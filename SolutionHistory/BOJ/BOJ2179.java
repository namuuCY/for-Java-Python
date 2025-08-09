import java.io.*;
import java.util.*;

public class Main {
    // 그런데 이방식쓰면 객체가 200만개까지 나올수있는데 음...

    static class Word {
        String word;
        int index;
        Word(String s, int i) {
            word = s;
            index = i;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<String, Word> count = new HashMap<>(); // 몇개 가지고 있는지
        int maxLength = 0; // 2 이상일때 올림
        int fidx = -1;
        int sidx = -1;
        String S = "";
        String T = "";

        int N = Integer.parseInt(br.readLine());

        for (int i=0; i<N ; i++) {
            String current = br.readLine();
            for (int j = 0; j < current.length(); j++) {
                String sub = current.substring(0, j + 1); // j+1
                if (count.containsKey(sub)) {
                    Word w = count.get(sub);
                    int len = sub.length();
                    if(len > maxLength) {
                        maxLength = len;
                        S = w.word;
                        T = current;
                        fidx = w.index;
                        sidx = i;
                    } else if (len == maxLength && maxLength > 0) {
                        int currentMin = Math.min(fidx, sidx);
                        int newMin = Math.min(w.index, i);
                        if (newMin < currentMin) {
                            S = w.word;
                            T = current;
                            fidx = w.index;
                            sidx = i;
                        }
                    }
                    // 분기처리를 똑바로 합시다.
                } else {
                    count.put(sub, new Word(current, i));
                }
            }
        }
        System.out.println(S);
        System.out.println(T);
    }
}