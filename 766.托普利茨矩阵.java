/*
 * @lc app=leetcode.cn id=766 lang=java
 *
 * [766] 托普利茨矩阵
 */

// @lc code=start
class Solution {
    public int m;
    public int n;
    public boolean isToeplitzMatrix(int[][] matrix) {
        m = matrix.length;
        n = matrix[0].length;
        for(int j = 0; j < n; j++)
            if(!isEqual(matrix,0,j))
                return false;
        for(int i = 1; i < m; i++)
            if(!isEqual(matrix,i,0))
                return false;
        return true;
    }

    private boolean isEqual(int[][] matrix, int i, int j){
        int a = matrix[i][j];
        while(i < m && j < n)
            if(matrix[i][j] != a)
                return false;
            else{
                i++;
                j++;
            }
        return true;
    }
}
// @lc code=end

