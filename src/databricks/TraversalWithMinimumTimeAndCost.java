package databricks;

import java.util.*;

public class TraversalWithMinimumTimeAndCost {
    private class Node {
        public int x, y;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public int minimumTimeAndCost(char[][] board, int[] time, int[] cost) {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> visitedWay = new HashSet<>();
        ArrayDeque<Node> q = new ArrayDeque<>();
        Node start = getNode(board, 'S');
        Node dest = getNode(board, 'D');
        int[][] dirs = new int[][] {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
        int m = board.length, n = board[0].length, vNumber = time.length;
        q.add(start);
        visited.add(start.x * n + start.y);
        int res = 0, resTime = Integer.MAX_VALUE, resCost = Integer.MAX_VALUE, level = 0;
        while(!q.isEmpty() && visitedWay.size() < vNumber) {
            // travel for each level
            for (int i = q.size(); i > 0; i --) {
                Node cur = q.poll();
                for (int[] dir : dirs) {
                    int nX = cur.x + dir[0];
                    int nY = cur.y + dir[1];
                    int hashPos = nX * n + nY;

                    if (nX >= 0 && nX < m && nY >= 0 && nY < n && !visited.contains(hashPos)) {
                        if (board[nX][nY] == 'D') {
                            int vel = board[cur.x][cur.y] - '0';
                            int nTime = level * time[vel - 1];
                            int nCost = level * cost[vel - 1];
                            if (nTime < resTime || nTime == resTime && nCost < resCost) {
                                resTime = nTime;
                                resCost = nCost;
                                res = vel;
                            }
                            visitedWay.add(vel);
                            System.out.printf("%d %d %d %d %d %n", vel, nTime, nCost, cur.x, cur.y);
                        } else {
                            int nVel = board[nX][nY] - '0';
                            if ((board[cur.x][cur.y] == 'S' || board[nX][nY] == board[cur.x][cur.y])  && board[nX][nY] != 'X' && !visitedWay.contains(nVel)) {
                                visited.add(hashPos);
                                q.add(new Node(nX, nY));
                            }
                        }
                    }
                }
            }
            level ++;
        }
        return res;
    }

    public Node getNode(char[][] board, char target) {
        for (int i = 0; i < board.length; i ++) {
            for (int j = 0; j < board[0].length; j ++) {
                if (board[i][j] == target) {
                    return new Node(i, j);
                }
            }
        }
        // raise exception if not found
        return null;
    }
    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'3', '3', 'S', '2', 'X', 'X'},
                {'3', '1', '1', '2', 'X', '2'},
                {'3', '1', '1', '2', '2', '2'},
                {'3', '1', '1', '1', 'D', '3'},
                {'3', '3', '3', '3', '3', '4'},
                {'4', '4', '4', '4', '4', '4'}
        };
        int[] time = new int[] {3, 2, 1, 1};
        int[] cost = new int[] {1, 2, 3, 4};
        TraversalWithMinimumTimeAndCost traversalWithMinimumTimeAndCost = new TraversalWithMinimumTimeAndCost();
        System.out.println(traversalWithMinimumTimeAndCost.minimumTimeAndCost(board, time, cost));
    }
}
