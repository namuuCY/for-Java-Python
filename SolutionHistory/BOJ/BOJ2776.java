import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/2776


	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int testTrials = Integer.parseInt(br.readLine());
		Set<Integer> numbers = new HashSet<>();
		StringTokenizer st;

		StringBuilder sb = new StringBuilder();
		while (testTrials -- > 0) {
			numbers.clear();

			int N = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine().strip());

			while (N-- > 0) {
				numbers.add(Integer.parseInt(st.nextToken()));
			}

			int M = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine().strip());

			for (int i = 0 ; i < M ; i ++) {
				int targetValue = Integer.parseInt(st.nextToken());
				int currentResult = isContained(numbers, targetValue);
				sb.append(currentResult);
				sb.append("\n");
			}
		}

		bw.write(sb.toString());
		bw.flush();
	}

	private static int isContained(Set<Integer> numbers, int targetValue) {
		return numbers.contains(targetValue) ? 1 : 0;
	}
}