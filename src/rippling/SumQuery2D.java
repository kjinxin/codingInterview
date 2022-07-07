package rippling;
/*
https://leetcode.com/problems/range-sum-query-2d-immutable/
https://leetcode.com/problems/matrix-block-sum/
给一个坐标点和半径，要求把以坐标点为中心的半径是 R 的正方形内的和求出来。基本上就是运用 2d presum。注意要考虑半径超过矩阵的边界的情况。
https://www.1point3acres.com/bbs/thread-893249-1-1.html
 */
public class SumQuery2D {
    public int[][] matrixBlockSum(int[][] mat, int k) {
        int row = mat.length, col = mat[0].length;
        int[][] preSum = new int[row + 1][col + 1];
        for (int i = 1; i < row + 1; i ++) {
            for (int j = 1; j < col + 1; j ++) {
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] + mat[i - 1][j - 1];
            }
        }

        for (int i = 0; i < row; i ++) {
            for (int j = 0; j < col; j ++) {
                int r1 = Math.max(0, i - k);
                int c1 = Math.max(0, j - k);
                int r2 = Math.min(row - 1, i + k);
                int c2 = Math.min(col - 1, j + k);
                mat[i][j] = calculateBlockSum(preSum, r1, c1, r2, c2);
            }
        }
        return mat;
    }

    private int calculateBlockSum(int[][] mat, int r1, int c1, int r2, int c2) {
        return mat[r2 + 1][c2 + 1] - mat[r1][c2 + 1] - mat[r2 + 1][c1] + mat[r1][c1];
    }
}
