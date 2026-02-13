import java.io.*;
import java.util.*;

public class Main {
    // https://www.acmicpc.net/problem/5635

    static class Student implements Comparable<Student> {
        String name;
        int day, month, year;

        public Student(String name, int day, int month, int year) {
            this.name = name;
            this.day = day;
            this.month = month;
            this.year = year;
        }

        @Override
        public int compareTo(Student other) {
            // 1. 연도 비교
            if (this.year != other.year) {
                return Integer.compare(this.year, other.year);
            }
            // 2. 월 비교
            if (this.month != other.month) {
                return Integer.compare(this.month, other.month);
            }
            // 3. 일 비교
            return Integer.compare(this.day, other.day);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int d = sc.nextInt();
            int m = sc.nextInt();
            int y = sc.nextInt();
            students.add(new Student(name, d, m, y));
        }

        // 생일 기준 오름차순 정렬 (나이가 많은 사람 -> 나이가 적은 사람 순)
        Collections.sort(students);

        // 가장 나이가 적은 사람 (리스트의 마지막)
        System.out.println(students.get(n - 1).name);
        // 가장 나이가 많은 사람 (리스트의 처음)
        System.out.println(students.get(0).name);
    }
}