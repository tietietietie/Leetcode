/*
 * @lc app=leetcode.cn id=417 lang=java
 *
 * [417] 太平洋大西洋水流问题
 */

// @lc code=start
class Solution {
    private int[][] dirArr = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> ans = new ArrayList<>();
        int m = matrix.length;
        if(m == 0) return ans;
        int n = matrix[0].length;
        if(n == 0) return ans;
        boolean[][] canReachP = new boolean[m][n];
        boolean[][] canReachA = new boolean[m][n];
        for(int i = 0; i < n; i++){
            dfs(matrix,m,n,0,i,canReachP);
            dfs(matrix,m,n,m-1,i,canReachA);
        }
        for(int i = 0; i < m; i++){
            dfs(matrix,m,n,i,0,canReachP);
            dfs(matrix,m,n,i,n-1,canReachA);
        }
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                if(canReachP[i][j] && canReachA[i][j])
                    ans.add(Arrays.asList(i,j));
        return ans;
    }
    private void dfs(int[][] matrix, int m, int n, int i, int j, boolean[][] visited){
        if(visited[i][j]) return;
        visited[i][j] = true;
        for(int[] dir : dirArr){
            int nextI = i+dir[0];
            int nextJ = j+dir[1];
            if(nextI >= 0 && nextI < m && nextJ >= 0 && nextJ < n && matrix[nextI][nextJ] >= matrix[i][j])
                dfs(matrix,m,n,nextI,nextJ,visited);
        }
    }
}
// @lc code=end

