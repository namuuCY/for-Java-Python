import java.io.*;

public class Main {
	// https://www.acmicpc.net/problem/23304

	static String S;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		S = br.readLine();

		if (isAkaraka(0, S.length() - 1)) {
			System.out.println("AKARAKA");
		} else {
			System.out.println("IPSELENTI");
		}
	}


	static boolean isAkaraka(int start, int end) {
		int len = end - start + 1;
		if (len == 1) return true;

		if (!isPalindrome(start, end)) return false;

		int nextLen = len / 2;
		return isAkaraka(start, start + nextLen - 1);
	}

	static boolean isPalindrome(int start, int end) {
		while (start < end) {
			if (S.charAt(start) != S.charAt(end)) {
				return false;
			}
			start++;
			end--;
		}
		return true;
	}
}