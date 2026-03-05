import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/29723
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		// 과목과 점수를 저장할 Map
		HashMap<String, Integer> subjects = new HashMap<>();
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			String subjectName = st.nextToken();
			int score = Integer.parseInt(st.nextToken());
			subjects.put(subjectName, score);
		}

		int baseScore = 0;
		// 공개된 필수 과목 K개 처리
		for (int i = 0; i < k; i++) {
			String mandatorySubject = br.readLine().trim();
			baseScore += subjects.get(mandatorySubject);
			subjects.remove(mandatorySubject); // 이미 반영된 과목은 후보에서 제외
		}

		// 남은 과목들의 점수만 리스트로 추출
		ArrayList<Integer> remainingScores = new ArrayList<>();
		for (int score : subjects.values()) {
			remainingScores.add(score);
		}

		// 점수를 오름차순으로 정렬
		Collections.sort(remainingScores);

		int minScore = baseScore;
		int maxScore = baseScore;

		int needed = m - k; // 추가로 더 골라야 하는 과목 수

		// 최소 점수: 가장 낮은 점수들을 더함
		for (int i = 0; i < needed; i++) {
			minScore += remainingScores.get(i);
		}

		// 최대 점수: 가장 높은 점수들을 더함
		int size = remainingScores.size();
		for (int i = size - 1; i >= size - needed; i--) {
			maxScore += remainingScores.get(i);
		}

		// 결과 출력
		System.out.println(minScore + " " + maxScore);
	}
}