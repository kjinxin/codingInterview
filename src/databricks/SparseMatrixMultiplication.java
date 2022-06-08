package databricks;

/**
 * https://leetcode.com/problems/sparse-matrix-multiplication/
 * 311
 */
public class SparseMatrixMultiplication {
    public int[][] multiply(int[][] mat1, int[][] mat2) {
        int m = mat1.length;
        int k = mat1[0].length;
        int n = mat2[0].length;
        int mat1NonZero = getNonZero(mat1);
        int mat2NonZero = getNonZero(mat2);
        int[] rowIdx1 = new int[m + 1], newIdx = new int[m + 1], col1 = new int[mat1NonZero], val1 = new int[mat1NonZero];
        int[] rowIdx2 = new int[k + 1], col2 = new int[mat2NonZero], val2 = new int[mat2NonZero];
        int[] tmpIdx = new int[k + 1], tmpCol = new int[mat1NonZero], tmpVal = new int[mat1NonZero];
        storeSparseMetric(mat1, rowIdx1, col1, val1);
        convertCSRtoCSC(rowIdx1, col1, val1, tmpIdx, tmpCol, tmpVal);
        convertCSRtoCSC(tmpIdx, tmpCol, tmpVal, newIdx, col1, val1);
        rowIdx1 = newIdx;
        storeSparseMetric(mat2, rowIdx2, col2, val2);


        int[][] res = new int[m][n];
        int st = 0;
        for(int row = 0; row < m; row ++) {
            for(int i = rowIdx1[row]; i < rowIdx1[row + 1]; i ++) {
                int col = col1[i];
                int val = val1[i];
                for (int j = rowIdx2[col]; j < rowIdx2[col + 1]; j ++) {
                    res[row][col2[j]] += val * val2[j];
                }
            }
        }
        return res;
    }

    public int getNonZero(int[][] mat) {
        int cnt = 0;
        for (int i = 0; i < mat.length; i ++) {
            for (int j = 0; j < mat[0].length; j ++) {
                cnt += mat[i][j] == 0 ? 0 : 1;
            }
        }
        return cnt;
    }

    public void convertCSRtoCSC(int[] rowIdx, int[] col, int[] val, int[] colIdx, int[] row, int[] newVal) {
        for(int i = 0; i < val.length; i ++) {
            colIdx[col[i] + 1] ++;
        }
        for(int i = 1; i < colIdx.length; i ++) {
            colIdx[i] += colIdx[i - 1];
        }

        for(int rNum = 0; rNum < rowIdx.length - 1; rNum ++) {
            for(int i = rowIdx[rNum]; i < rowIdx[rNum + 1]; i ++) {
                int cNum = col[i];
                int dest = colIdx[cNum];
                row[dest] = rNum;
                newVal[dest] = val[i];
                colIdx[cNum] ++;
            }
        }
        for (int i = colIdx.length - 1; i > 0; i --) {
            colIdx[i] = colIdx[i - 1];
        }
        colIdx[0] = 0;
    }
    public void storeSparseMetric(int[][] mat, int[] rowIdx, int[] col, int[] val) {
        int cnt = 0;
        for (int i = 0; i < mat.length; i ++) {
            for (int j = 0; j < mat[0].length; j ++) {
                if (mat[i][j] != 0) {
                    col[cnt] = j;
                    val[cnt] = mat[i][j];
                    cnt ++;
                }
            }
            rowIdx[i + 1] = cnt;
        }
    }
}
