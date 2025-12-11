import java.io.*;
import java.util.*;

public class Main {

    static int[] dice = new int[10];
    static Node[] map = new Node[33];
    static int maxAns;


//    static int bt(int count, ) {
//
//    }


    // node는 리스트에 담겨져있음. : 0 이면 시작,
    static class Node {
        int score;
        int current;
        int nextNode;
        int subNode;

        Node(int score, int current, int nextNode, int subNode) {
            this.score = score;
            this.current = current;
            this.nextNode = nextNode;
            this.subNode = subNode;
        }

        void updateTree(int score, int nextNode, int subNode) {
            this.score = score;
            this.nextNode = nextNode;
            this.subNode = subNode;
        }

        int move(boolean isStart, int count) {
            if (count == 0) {
                return this.current;
            }
            if (this.current == 21) {
                return this.current;
            }
            if (isStart && this.subNode > 0) {
                return map[this.subNode].move(false, count - 1);
            }
            return map[this.nextNode].move(false, count - 1);
        }
    }

    static void debug(int[] members) {

        System.out.println(Arrays.toString(members));

    }

    static boolean canMove(int[] members, int currentIdx, int nextMove) {
        for (int i = 0; i < 4 ; i++) {
            if (i == currentIdx) continue;
            if (members[i] == nextMove && (nextMove!= 21)) return false;
        }
        return true;
    }

    static void bt(int count, int ans, int[] origin) {
        if (count == 10) {
            if (maxAns < ans) {
                maxAns = ans;
//                debug(origin);
            }
            return;
        }
        int[] members = origin.clone();
        for (int i = 0; i < 4; i++) {
            int nextMove = map[members[i]].move(true, dice[count]);
            if (canMove(members, i, nextMove)) {
                members[i] = nextMove;
                bt(count + 1, ans + map[nextMove].score, members);
                members[i] = origin[i];
            }
        }


        // 아래는 기존 실패한 로직
//        for (int i = 0 ; i < 33; i++) {
//
//            if (((bitmask >> i) & 1) != 1) continue;
//
//            int currentDice = dice[count];
//            Node currentNode = map[i];
//            int nextPoint = currentNode.move(true, currentDice);
//
//            // 도착은 중복되도 ㄱㅊ
//            if (((bitmask >> nextPoint) & 1) == 1 && (nextPoint != 21)) continue;
//
//            if (memberCount != 4) {
//                long nextBm = ((bitmask | (1L << nextPoint)) - (1L << i)) + 1;
//                bt(nextBm, count + 1, ans + map[nextPoint].score, memberCount + 1);
//            } else {
//                long nextBm = ((bitmask | (1L << nextPoint)) - (1L << i));
//                bt(nextBm, count + 1, ans + map[nextPoint].score, memberCount);
//            }
//        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0 ; i < 10; i++) {
            dice[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < 33; i++) {
            map[i] = new Node(i * 2, i, i+1, -1);
        }


        // 예외 케이스만 업데이트
        map[5].updateTree(10, 6, 22);
        map[10].updateTree(20, 11, 26);
        map[15].updateTree(30, 16, 28);
        map[21].updateTree(0, 21, -1);
        map[22].updateTree(13, 23, -1);
        map[23].updateTree(16, 24, -1);
        map[24].updateTree(19, 25, -1);

        map[25].updateTree(25, 31, -1);
        map[26].updateTree(22, 27, -1);
        map[27].updateTree(24, 25, -1);
        map[28].updateTree(28, 29, -1);
        map[29].updateTree(27, 30, -1);
        map[30].updateTree(26, 25, -1);
        map[31].updateTree(30, 32, -1);
        map[32].updateTree(35, 20, -1);

//
//        for (int j = 0 ; j < 33 ; j++) {
//
//            System.out.println(j + "일때" + "아래의 5칸 움직임");
//            StringBuilder sb = new StringBuilder();
//            for (int i = 1; i <6 ; i++) {
//                sb.append(map[map[j].move(true, i)].score);
//                sb.append(" ");
//            }
//            System.out.println(sb);
//
//        }
        bt(0, 0, new int[]{0, 0, 0, 0});
        System.out.println(maxAns);
    }
}