import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/2212
	// 센서

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		int K = Integer.parseInt(br.readLine());

		if (K >= N) {
			System.out.println(0);
			return;
		}

		int[] sensors = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			sensors[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(sensors);
		int[] diffs = new int[N - 1];
		for (int i = 0; i < N - 1; i++) {
			diffs[i] = sensors[i + 1] - sensors[i];
		}

		Arrays.sort(diffs);

		int result = 0;
		for (int i = 0; i < N - K; i++) {
			result += diffs[i];
		}

		System.out.println(result);
	}
}