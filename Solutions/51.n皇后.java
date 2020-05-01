/*
 * @lc app=leetcode.cn id=51 lang=java
 *
 * [51] N皇后
 */

// @lc code=start
class Solution {
    private int[] cols, hills, dales, queens;
    private int n;
    public List<List<String>> solveNQueens(int n) {
        this.n = n;
        this.cols = new int[n];
        this.hills = new int[2*n];
        this.dales = new int[2*n];
        this.queens = new int[n];
        List<List<String>> ans = new ArrayList<>();
        dfs(0,ans);
        return ans;
    }

    private void dfs(int row, List<List<String>> ans){
        for(int col = 0; col < n; col++)
            if(isNotUnderAttack(row,col)){
                placeQueen(row,col);
                if(row == n-1) buildPath(ans);
                else dfs(row+1,ans);
                removeQueen(row,col);
            }
    }

    private boolean isNotUnderAttack(int row, int col){
        int result = cols[col] + hills[row-col+n] + dales[row+col];
        return result == 0;
    }

    private void placeQueen(int row, int col){
        queens[row] = col;
        cols[col] = 1;
        hills[row-col+n] = 1;
        dales[row+col] = 1;
    }

    private void removeQueen(int row, int col){
        cols[col] = 0;
        hills[row-col+n] = 0;
        dales[row+col] = 0;
    }

    private void buildPath(List<List<String>> ans){
        ArrayList<String> path = new ArrayList<>();
        for(int i = 0; i < n; i++){
            StringBuilder sb = new StringBuilder();
            int queenPos = queens[i];
            for(int j = 0; j < queenPos; j++)
                sb.append('.');
            sb.append('Q');
            for(int j = queenPos+1; j < n; j++)
                sb.append('.');
            path.add(sb.toString());
        }
        ans.add(path);
    }
}
// @lc code=end

