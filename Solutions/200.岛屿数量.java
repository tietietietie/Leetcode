/*
 * @lc app=leetcode.cn id=200 lang=java
 *
 * [200] 岛屿数量
 */

// @lc code=start
class Solution {
    public int numIslands(char[][] grid) {
        int m = grid.length;
        if(m == 0) return 0;
        int n = grid[0].length;
        if(n == 0) return 0;
        int ans = 0;
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++){
                if(grid[i][j] == '1'){
                    dfs(grid,m,n,i,j);
                    ans++;
                }    
            }
        return ans;
    }
    private void dfs(char[][] grid, int m, int n, int i, int j){
        if(i == m || i < 0 || j == n || j < 0) return;
        if(grid[i][j] == '0') return;
        grid[i][j] = '0';
        dfs(grid,m,n,i-1,j);
        dfs(grid,m,n,i+1,j);
        dfs(grid,m,n,i,j-1);
        dfs(grid,m,n,i,j+1);
        return;
    }
}
// @lc code=end

