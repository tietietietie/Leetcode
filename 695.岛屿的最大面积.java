/*
 * @lc app=leetcode.cn id=695 lang=java
 *
 * [695] 岛屿的最大面积
 */

// @lc code=start
class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        int ans = 0;
        int m = grid.length, n = grid[0].length;
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++){
                if(grid[i][j] == 0 || grid[i][j] == -1)
                    continue;
                else
                    ans = Math.max(ans,dfs(grid,m,n,i,j));
            }
        return ans;
    }

    private int dfs(int[][] grid, int m, int n, int i, int j){
        if(i == m || j == n || i < 0 || j < 0) return 0;
        if(grid[i][j] == 0 || grid[i][j] == -1) return 0;
        grid[i][j] = -1;
        return 1 + dfs(grid,m,n,i-1,j) + dfs(grid,m,n,i+1,j) + dfs(grid,m,n,i,j-1) + dfs(grid,m,n,i,j+1);
    }
}
// @lc code=end

