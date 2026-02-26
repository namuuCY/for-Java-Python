import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/2358
	// 평행선

	static int n;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
		n = Integer.parseInt(br.readLine());
		Map<Integer, Integer> xDict = new HashMap<>();
		Map<Integer, Integer> yDict = new HashMap<>();
		StringTokenizer st;
		while (n -- > 0) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			Integer xCount = xDict.getOrDefault(x, 0);
			xCount ++;
			xDict.put(x, xCount);
			Integer yCount = yDict.getOrDefault(y, 0);
			yCount ++;
			yDict.put(y, yCount);
		}
		int ans = 0;
		for (Integer count : xDict.values()) {
			if (count > 1) ans ++;
		}
		for (Integer count : yDict.values()) {
			if (count > 1) ans ++;
		}
		System.out.println(ans);
	}

}