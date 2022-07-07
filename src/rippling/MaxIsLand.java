package rippling;
/*
https://leetcode.com/problems/max-area-of-island/
https://www.1point3acres.com/bbs/thread-878320-1-1.html
 */
public class MaxIsLand {
    public int maxAreaOfIsland(int[][] grid) {
        int color = 2;
        int ans = 0;
        for (int i = 0; i < grid.length; i ++) {
            for (int j = 0; j < grid[0].length; j ++) {
                if (grid[i][j] == 1) {
                    ans = Math.max(ans, dfs(grid, i, j, color));
                    color ++;
                }
            }
        }

        return ans;
    }

    private int dfs(int[][] grid, int r, int c, int color) {
        int[][] dirs = new int[][] {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int res = 1;
        grid[r][c] = color;
        for (int[] dir : dirs) {
            int nr = r + dir[0];
            int nc = c + dir[1];
            if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length && grid[nr][nc] == 1) {
                res += dfs(grid, nr, nc, color);
            }
        }
        return res;
    }
}
