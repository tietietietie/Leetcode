/*
 * @lc app=leetcode.cn id=547 lang=java
 *
 * [547] 朋友圈
 */

// @lc code=start
class Solution {
    public int findCircleNum(int[][] M) {
        int m = M.length;
        boolean[] visited = new boolean[m];
        int ans = 0;
        for(int i = 0; i < m; i++)
            if(!visited[i]){
                ans++;
                dfs(M,visited,m,i);
            }
        return ans;
    }
    private void dfs(int[][] M, boolean[] visited, int m, int i){
        visited[i] = true;
        for(int j = 0; j < m; j++)
            if(M[i][j] == 1 && !visited[j])
                dfs(M,visited,m,j);
    }
}
// @lc code=end

