import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/1925
	// 1925 삼각형

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		long[] x = new long[3];
		long[] y = new long[3];

		for (int i = 0; i < 3; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			x[i] = Long.parseLong(st.nextToken());
			y[i] = Long.parseLong(st.nextToken());
		}

		// 1. 세 점이 일직선 위에 있는지 확인 (CCW / 외적 활용)
		// (x2-x1)*(y3-y1) - (y2-y1)*(x3-x1)
		long ccw = (x[1] - x[0]) * (y[2] - x[0]) - (y[1] - y[0]) * (x[2] - x[0]); // 오타 수정: y[2]-y[0] 이어야 함. 아래 수정된 로직 참고.

		// 정확한 기울기 비교 (교차 곱셈): (y2-y1)/(x2-x1) == (y3-y2)/(x3-x2)
		// -> (y2-y1)*(x3-x2) == (y3-y2)*(x2-x1)
		if ((y[1] - y[0]) * (x[2] - x[1]) == (y[2] - y[1]) * (x[1] - x[0])) {
			System.out.println("X");
			return;
		}

		long[] lenSq = new long[3];
		lenSq[0] = getDistSq(x[0], y[0], x[1], y[1]);
		lenSq[1] = getDistSq(x[1], y[1], x[2], y[2]);
		lenSq[2] = getDistSq(x[2], y[2], x[0], y[0]);

		Arrays.sort(lenSq);
		long a = lenSq[0];
		long b = lenSq[1];
		long c = lenSq[2]; // 가장 긴 변

		if (a == c) { // a==b && b==c 와 동일
			System.out.println("JungTriangle");
		} else if (a == b || b == c) { // 이등변삼각형 (정삼각형 아님은 위에서 걸러짐)
			if (c > a + b) System.out.println("Dunkak2Triangle"); // 둔각
			else if (c == a + b) System.out.println("Jikkak2Triangle"); // 직각
			else System.out.println("Yeahkak2Triangle"); // 예각
		} else { // 일반 삼각형 (세 변 다름)
			if (c > a + b) System.out.println("DunkakTriangle");
			else if (c == a + b) System.out.println("JikkakTriangle");
			else System.out.println("YeahkakTriangle");
		}
	}

	private static long getDistSq(long x1, long y1, long x2, long y2) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}
}