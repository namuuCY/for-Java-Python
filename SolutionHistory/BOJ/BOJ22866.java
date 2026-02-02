import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/22866
	// 탑 보기

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		int[] h = new int[N + 1];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) {
			h[i] = Integer.parseInt(st.nextToken());
		}

		int[] cnt = new int[N + 1];
		int[] near = new int[N + 1];

		Arrays.fill(near, -1);

		Stack<Integer> stack = new Stack<>();

		for(int i = 1; i <= N; i++) {
			while(!stack.isEmpty() && h[stack.peek()] <= h[i]) {
				stack.pop();
			}

			cnt[i] += stack.size();

			if(!stack.isEmpty()) {
				near[i] = stack.peek();
			}
			stack.push(i);
		}

		stack.clear();

		for(int i = N; i >= 1; i--) {
			while(!stack.isEmpty() && h[stack.peek()] <= h[i]) {
				stack.pop();
			}
			cnt[i] += stack.size();
			if(!stack.isEmpty()) {
				int rightIndex = stack.peek();
				if(near[i] == -1) {
					near[i] = rightIndex;
				}
				else {
					int distLeft = i - near[i];
					int distRight = rightIndex - i;
					if(distRight < distLeft) {
						near[i] = rightIndex;
					}
				}
			}

			stack.push(i);
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i <= N; i++) {
			sb.append(cnt[i]);
			if(cnt[i] > 0) {
				sb.append(" ").append(near[i]);
			}
			sb.append("\n");
		}
		System.out.print(sb);
	}
}