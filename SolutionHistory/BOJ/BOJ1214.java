import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/1214
	// 쿨한 물건 구매

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// D, P, Q를 받아낼 것
		StringTokenizer st = new StringTokenizer(br.readLine().strip());

		long originTargetPrice = Long.parseLong(st.nextToken());
		long billA = Long.parseLong(st.nextToken());
		long billB = Long.parseLong(st.nextToken());

		// P, Q에 대해 최대공약수를 구할 것 - 유클리드 호제법
		long gcdVal = euclidean(billA, billB);

		// P, Q를 최대공약수 g 에 대해 다시 표현할 것
		long unitA = billA / gcdVal;
		long unitB = billB / gcdVal;

		long reducedTarget = (long) Math.ceil((double) originTargetPrice / gcdVal);

		// 표현이 불가능한 가장 큰 수의 프로베니우스
		long frobeniusLimit = calculateMaxInexpressible(unitA, unitB);

		long bestReducedAnswer = 0;

		// 케이스 분리
		if (reducedTarget > frobeniusLimit) {
			bestReducedAnswer = reducedTarget * gcdVal;
		} else {
			if (unitA > unitB) {
				long temp = unitA;
				unitA = unitB;
				unitB = temp;
			}

			bestReducedAnswer = Long.MAX_VALUE;

			long limit = Math.min((reducedTarget/unitB + 1), unitA);

			for (long i = reducedTarget / unitB + 1 ; i >= 0 ; i--) {
				long currentSum = i * unitB;

				long remainder = reducedTarget - currentSum;
				if (remainder > 0) {
					long smallerCount = (long) Math.ceil((double) remainder / unitA);
					currentSum += smallerCount * unitA;
				}

				if (currentSum < bestReducedAnswer) bestReducedAnswer = currentSum;
				if (bestReducedAnswer == reducedTarget) break;
				limit --;
				if (limit < 0) break;
			}

			bestReducedAnswer = bestReducedAnswer * gcdVal;
		}
		// wantToBuyPrice가 maxInexpressible 보다 크다면

		// maxInexpressible 보다 작거나 같다면
		// min(p,q)에 대해서 ceil로 값을 구함

		System.out.println(bestReducedAnswer);
	}

	private static long euclidean(long a, long b) {
		if (b == 0) return a;
		return euclidean(b, a % b);
	}

	private static long calculateMaxInexpressible(long a, long b) {
		return (a * b - a - b);
	}
}