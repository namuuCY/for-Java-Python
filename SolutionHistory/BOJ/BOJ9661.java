import java.io.*;
import java.util.*;

public class Main {
	// https://www.acmicpc.net/problem/9661
	// 9661 돌 게임 7

	// 누군가 한명이 남은돌이 5의 배수가 되도록 만들 수 있다면,
	// 그 사람이 승리함
	// 맨처음 존재하는 수에 대해서 5N이 되는지 확인
	// 만약 못하면 반대편 사람이 승리함.

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long n = Long.parseLong(br.readLine());

		if ((n % 5) == 1 ||(n % 5) == 3 ||(n % 5) == 4 ) {
			System.out.println("SK");
		} else {
			System.out.println("CY");
		}

		// mod 5 기준
		// 4^x 는 mod 1 아니면 4
		// 0 : sk 어떻게 하든 cy가 5의 배수로 만들기 가능 cy 승
		// 1, 4 : sk 무조건 5의 배수로 만들기 가능 sk 승
		// 2 : sk 1 빼간다면 cy 1빼서 cy 승
		//     sk 4 빼간다면    mod5 3인 상태  -> cy는 다시 2인상태로 만들고 ... -> sk 4 빼감 -> cy 2로 만들고 ... 그러면 sk때 2가 됨 -> cy 승 (cy 무조건 승리)
		// 3 : sk 1 빼간다면 cy턴에서 mod 2 -> 그러면 위에 의해서 sk가 승리
		//     sk 4 빼간다면 mod 4 -> cy가 무조건 승리 (이 경우는 채택을 안하겠지 그러면)

	}

}