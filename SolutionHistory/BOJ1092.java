import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        List<Integer> clist = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0 ; i < N ; i ++) {
            clist.add(Integer.parseInt(st.nextToken())) ;
        }
        clist.sort(Collections.reverseOrder());

        int M = Integer.parseInt(br.readLine());
        List<Integer> blist = new LinkedList<>();
        StringTokenizer st1 = new StringTokenizer(br.readLine());
        for (int j = 0; j < M ; j++) {
            blist.add(Integer.valueOf(st1.nextToken()));
        }
        blist.sort(Collections.reverseOrder());

        if (clist.get(0) < blist.get(0)) {
            System.out.println("-1");
            return;
        }

        int time = 0;

        while (!blist.isEmpty()) {
            for (int c : clist) {
                if (!blist.isEmpty() && c < blist.get(blist.size() - 1)) {
                    continue;
                }
                for (Iterator<Integer> iter = blist.iterator(); iter.hasNext(); ) {
                    int b = iter.next();
                    if (c >= b) {
                        iter.remove();
                        break;
                    }
                }
            }
            time++;
        }
        System.out.println(time);
    }
}