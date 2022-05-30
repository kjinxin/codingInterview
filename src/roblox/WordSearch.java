package roblox;
/*
leetcode 79
Given an m x n grid of characters board and a string word, return true if word exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally
or vertically neighboring. The same letter cell may not be used more than once.
 */
public class WordSearch {
    public int[][] dirs = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    public boolean dfs(char[][] board, int x, int y, String word, int idx) {
        if (idx >= word.length()) {
            return true;
        }
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != word.charAt(idx)) {
            return false;
        }
        board[x][y] = '$';
        for (int[] dir : dirs) {
            if (dfs(board, x + dir[0], y + dir[1], word, idx + 1)) {
                board[x][y] = word.charAt(idx);
                return true;
            }
        }
        board[x][y] = word.charAt(idx);
        return false;
    }
    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i ++) {
            for (int j = 0; j < board[0].length; j ++) {
                if (dfs(board, i, j, word, 0)) {
                    return true;
                }
            }
        }
        return false;
    }
}
