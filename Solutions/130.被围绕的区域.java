/*
 * @lc app=leetcode.cn id=130 lang=java
 *
 * [130] 被围绕的区域
 */

// @lc code=start
class Solution {
    public void solve(char[][] board) {
        int m = board.length;
        if(m == 0) return;
        int n = board[0].length;
        for(int i = 0; i < m; i++){
            if(board[i][0] == 'O')
                dfs(board,m,n,i,0);
            if(board[i][n-1] == 'O')
                dfs(board,m,n,i,n-1);
        }
        for(int j = 0; j < n; j++){
            if(board[0][j] == 'O')
                dfs(board,m,n,0,j);
            if(board[m-1][j] == 'O')
                dfs(board,m,n,m-1,j);
        }
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++){
                if(board[i][j] == '1')
                    board[i][j] = 'O';
                else if(board[i][j] == 'O')
                    board[i][j] = 'X';
            }
    }

    private void dfs(char[][] board, int m, int n, int i, int j){
        if(i == m || i < 0 || j == n || j < 0) return;
        if(board[i][j] == '1' || board[i][j] == 'X') return;
        board[i][j] = '1';
        dfs(board,m,n,i-1,j);
        dfs(board,m,n,i+1,j);
        dfs(board,m,n,i,j-1);
        dfs(board,m,n,i,j+1);
        return;
    }
}
// @lc code=end

