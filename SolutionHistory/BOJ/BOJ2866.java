import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
	// https://www.acmicpc.net/problem/2866
	// 2866 문자열 잘라내기

	static int R;
	static int C;

	static char[][] origin;



	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		origin = new char[C][R];

		for (int r = 0 ; r < R; r++) {
			char[] input = br.readLine().strip().toCharArray();
			for (int c = 0 ; c < C; c++) {
				origin[c][r] = input[c];
			}
		}
		Set<String> strings = new HashSet<>();
		int ans = 0;

		for (int r = 1; r < R; r++) {
			strings.clear();
			for (int c = 0 ; c < C; c++) {
				strings.add(new String(Arrays.copyOfRange(origin[c], r, R)));
			}
			if (strings.size() == C) {
				ans ++;
			} else {
				break;
			}
		}

		System.out.println(ans);



	}
}