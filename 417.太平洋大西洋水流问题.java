/*
 * @lc app=leetcode.cn id=417 lang=java
 *
 * [417] 太平洋大西洋水流问题
 */

// @lc code=start
class Solution {
    private int[][][] memo;
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        int m = matrix.length;
        if(m == 0) return ans;
        int n = matrix[0].length;
        if(n == 0) return ans;
        memo = new int[m][n][2];
        dfs(matrix,m,n,0,0,memo,ans);
        return ans;
    }
    private void dfs(int[][] matrix, int m, int n, int i, int j, int[][][] memo, List<List<Integer>> ans){
        if(memo[i][j][0] != 0 && memo[i][j][1] != 0) return;
        if(i != 0){
            dfs(matrix,m,n,i-1,j,memo,ans);
        }
        if(j != 0){
            dfs(matrix,m,n,i,j-1,memo,ans);
        }
        if(i != m-1){
            dfs(matrix,m,n,i+1,j,memo,ans);
        }
        if(j != n-1){
            dfs(matrix,m,n,i,j+1,memo,ans);
        }
        if(i == 0 || j == 0) memo[i][j][0] = 1;
        if(i == m-1 || j == m-1) memo[i][j][1] = 1;
        if(i != 0 && j != 0 && i != m-1 && j != n-1){
            memo[i][j][0] = memo[i-1][j][0] + memo[i+1][j][0] + memo[i][j-1][0] + memo[i][j+1][0];
            memo[i][j][1] = memo[i-1][j][1] + memo[i+1][j][1] + memo[i][j-1][1] + memo[i][j+1][1];
        }
        memo[i][j][0] = memo[i][j][0] > 0 ? 1 : -1;
        memo[i][j][1] = memo[i][j][1] > 0 ? 1 : -1;
        if(memo[i][j][0] == 1 && memo[i][j][1] == 1){
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(i);
            temp.add(j);
            ans.add(temp);
        }
    }
}
// @lc code=end

