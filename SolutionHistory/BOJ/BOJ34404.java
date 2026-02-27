import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/34404
	// 파티 홍보

	static int N;

	static class Center {
		int x;
		int y;

		// Center의 값은 중앙값의 2배값임.
		Center(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

		N = Integer.parseInt(br.readLine());
		List<Center> centers = new ArrayList<>();

		StringTokenizer st;
		for (int i = 0 ; i < N; i ++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());
			// 정수 2 나누기 하면 안될거 같다.
			// 직선의 방정식에 2배 해서 맞는지를 확인하는게...?
			// 2 y = 2 a x + 2 b
			centers.add(new Center(x+p, y+q));
		}

		st = new StringTokenizer(br.readLine());
		long a = Integer.parseInt(st.nextToken());
		long b = Integer.parseInt(st.nextToken());

		int count = 0;

		for (Center c : centers) {
			if ((long) c.y >= a * c.x + 2 * b) count ++;
		}

		System.out.println(count);
	}

}