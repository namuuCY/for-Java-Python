import java.util.*;

class Solution {
	// task
	// 소요시간 오름차순
	// 작업 요청 시각 오름차순
	// 작업의 번호가 오름차순

	// 코드 구현 시간 15분 45초


	// 대기 큐가 비어있는 경우를 고려하지 못함.
	// 작업이 제 시간에 들어오는 스케줄러가 필요하네

	// 오류발견

	// 41분까지 테스트케이스 하나 못풀었음.


	static class Task implements Comparable<Task> {
		int duration;
		int requestTime;
		int taskIndex;


		Task(int duration, int requestTime, int taskIndex) {
			this.duration = duration;
			this.requestTime = requestTime;
			this.taskIndex = taskIndex;
		}

		public int compareTo(Task that) {
			if (this.duration != that.duration) {
				return Integer.compare(this.duration, that.duration);
			}
			if (this.requestTime != that.requestTime) {
				return Integer.compare(this.requestTime, that.requestTime);
			}
			return Integer.compare(this.taskIndex, that.taskIndex);
		}
	}


	public int solution(int[][] jobs) {
		int currentTime = 0;
		int taskSize = jobs.length;

		PriorityQueue<Task> taskQueue = new PriorityQueue<>();
		List<Integer> taskDoneTimes = new ArrayList<>();
		// s는 작업이 요청되는 시점이며 0 ≤ s ≤ 1,000입니다.
		List<Task>[] scheduler = new List[1001];

		for (int i = 0 ; i <= 1000; i++) {
			scheduler[i] = new ArrayList<>();
		}

		for (int i = 0 ; i < jobs.length; i++ ) {
			Task task = new Task(jobs[i][1], jobs[i][0], i);
			int requestTime = jobs[i][0];
			scheduler[requestTime].add(task);
			// taskQueue.add(task);
		}

		// 맨처음 초일 때의 작업 다 넣기.
		for (int sec = 0 ; sec <= 1000 ; sec++) {
			if (scheduler[sec].isEmpty()) continue;
			for (Task task : scheduler[sec]) {
				taskQueue.add(task);
			}
			currentTime += sec;
			break;
		}

		int taskCount = 0;

		while (taskCount < taskSize) {
			if (taskQueue.isEmpty()) {
				// 작업을 다음 시간까지 넣어야 함.
				for (int sec = currentTime + 1 ; sec <= 1000 ; sec ++) {
					if (scheduler[sec].isEmpty()) continue;
					for (Task task : scheduler[sec]) {
						taskQueue.add(task);
					}
					currentTime = sec;
					break;
				}
			}

			Task task = taskQueue.poll();
			// 스케줄러에서 태스크 추가 : 현재 시간부터 duration 추가된 시간까지의 스케줄러

			for (int cTime = currentTime + 1; cTime <= currentTime + task.duration ; cTime ++) {
				if (cTime <= 1000) {
					taskQueue.addAll(scheduler[cTime]);
				}
			}

			currentTime += task.duration;
			int turnaroundTime = currentTime - task.requestTime;
			taskDoneTimes.add(turnaroundTime);

			taskCount ++;
		}

		int totalTime = 0;
		for (Integer turnaroundTime : taskDoneTimes) {
			totalTime += turnaroundTime;
		}

		int answer = totalTime / taskDoneTimes.size();

		return answer;
	}
}