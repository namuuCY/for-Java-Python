import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/7347
	// 플립과 시프트
	// 전체 길이가 짝수인 경우, 홀수인 경우 분리해서 생각
	// 홀수인 경우, 움직이면 홀수 <-> 짝수 어디든 갈 수 있음.
	// 짝수인 경우, 움직여도 홀수는 여전히 홀수, 짝수는 여전히 짝수

	// 2
	//18 0 0 1 0 1 1 1 1 0 1 0 0 1 0 0 0 0 1
	//14 1 1 0 0 1 1 1 0 0 1 1 0 1 0

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int trialCount = Integer.parseInt(br.readLine());
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		while (trialCount -- > 0) {

			st = new StringTokenizer(br.readLine());
			int length = Integer.parseInt(st.nextToken());

			if (length % 2 == 1) {
				sb.append("YES");
				if (trialCount > 0) sb.append("\n");
				continue;
			}

			boolean[] currentSequence = parseSequence(st, length);
			boolean isPossible = analyzePossibility(currentSequence, length);
			sb.append( isPossible ? "YES" : "NO");
			if (trialCount > 0) {
				sb.append("\n");
			}
		}

		System.out.println(sb);
	}

	private static boolean[] parseSequence(StringTokenizer st, int length) throws IOException {
		boolean[] seq = new boolean[length];

		for (int i = 0 ; i < length ; i++) {
			if (!"1".equals( st.nextToken() )) {
				continue;
			}
			seq[i] = true;
		}

		return seq;
	}

	private static boolean analyzePossibility(boolean[] sequence, int length) {
		int evenCount = 0;
		int oddCount = 0;
		for (int i = 0 ; i < length; i++) {
			if (!sequence[i]) {
				continue;
			}
			if (i % 2 == 0) {
				evenCount ++;
			} else {
				oddCount ++;
			}
		}

		return Math.abs( evenCount - oddCount ) <= 1;
	}
}