import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/16478

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 입력: pab, pbc, pcd
        if (!sc.hasNextDouble()) return;
        double pab = sc.nextDouble();
        double pbc = sc.nextDouble();
        double pcd = sc.nextDouble();

        // 방멱의 정리: pab * pcd = pbc * pda
        // pda = (pab * pcd) / pbc
        double pda = (pab * pcd) / pbc;

        // 결과 출력 (오차 범위 10^-6 허용)
        System.out.printf("%.10f\n", pda);
    }
}