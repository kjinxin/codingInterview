package rippling;
/*
https://leetcode.com/problems/game-of-life/
https://www.1point3acres.com/bbs/thread-899257-1-1.html
 */
public class GameOfLife {
    public void gameOfLife(int[][] board) {
        int[] dir = {0, -1, 1};
        int m = board.length, n = board[0].length;
        for (int r = 0; r < m; r ++) {
            for (int c = 0; c < n; c ++) {
                int cntLife = 0;
                for (int i : dir) {
                    for (int j : dir) {
                        if (!(i == 0 && j == 0)) {
                            int nr = r + i;
                            int nc = c + j;
                            if (nr >= 0 && nr < m && nc >= 0 && nc < n && Math.abs(board[nr][nc]) == 1) {
                                cntLife ++;
                            }
                        }
                    }
                }
                if (board[r][c] == 1 && (cntLife < 2 || cntLife > 3)) {
                    board[r][c] = -1;
                }
                if (board[r][c] == 0 && cntLife == 3) {
                    board[r][c] = 2;
                }
            }
        }
        for (int r = 0; r < m; r ++) {
            for (int c = 0; c < n; c ++) {
                if (board[r][c] > 0) {
                    board[r][c] = 1;
                } else {
                    board[r][c] = 0;
                }
            }
        }
    }
}
