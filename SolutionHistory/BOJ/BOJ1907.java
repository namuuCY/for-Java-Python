import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/1907

	static class Molecule {
		int c = 0, h = 0, o = 0;

		public Molecule(String formula) {
			int len = formula.length();
			for (int i = 0; i < len; i++) {
				char atom = formula.charAt(i);
				int count = 1;

				// 다음 문자가 숫자라면 count 업데이트
				if (i + 1 < len && Character.isDigit(formula.charAt(i + 1))) {
					count = formula.charAt(i + 1) - '0';
					i++; // 숫자 부분 건너뜀
				}

				if (atom == 'C') c += count;
				else if (atom == 'H') h += count;
				else if (atom == 'O') o += count;
			}
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String line = sc.next();

		// +, = 기준으로 분자 분리
		String[] parts = line.split("[+=]");
		Molecule m1 = new Molecule(parts[0]);
		Molecule m2 = new Molecule(parts[1]);
		Molecule m3 = new Molecule(parts[2]);

		// 1부터 10까지 완전 탐색 (사전순 출력을 위해 1부터 시작)
		for (int x1 = 1; x1 <= 10; x1++) {
			for (int x2 = 1; x2 <= 10; x2++) {
				for (int x3 = 1; x3 <= 10; x3++) {
					if (isBalanced(m1, m2, m3, x1, x2, x3)) {
						System.out.println(x1 + " " + x2 + " " + x3);
						return; // 가장 먼저 찾은 것이 사전순 최소
					}
				}
			}
		}
	}

	private static boolean isBalanced(Molecule m1, Molecule m2, Molecule m3, int x1, int x2, int x3) {
		// C, H, O 각각의 총 합이 같은지 확인
		boolean cMatch = (x1 * m1.c + x2 * m2.c) == (x3 * m3.c);
		boolean hMatch = (x1 * m1.h + x2 * m2.h) == (x3 * m3.h);
		boolean oMatch = (x1 * m1.o + x2 * m2.o) == (x3 * m3.o);

		return cMatch && hMatch && oMatch;
	}
}