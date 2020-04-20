/*
 * @lc app=leetcode.cn id=79 lang=java
 *
 * [79] 单词搜索
 */

// @lc code=start
class Solution {
private int[][] dirArr = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
private int m, n;
    public boolean exist(char[][] board, String word) {
        m = board.length;
        n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        int len = word.length();
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++){
                if(board[i][j] == word.charAt(0) && dfs(board,i,j,word,0,len,visited))
                    return true;
            }
        return false;
    }

    private boolean dfs(char[][] board, int i, int j, String word, int curPos, int len, boolean[][] visited){
        if(curPos == len-1) return true;
        visited[i][j] = true;
        for(int[] dir : dirArr){
            int nextI = i+dir[0];
            int nextJ = j+dir[1];
            if(nextI >= 0 && nextI < m && nextJ >= 0 && nextJ < n && !visited[nextI][nextJ] && board[nextI][nextJ] == word.charAt(curPos+1) && dfs(board,nextI,nextJ,word,curPos+1,len,visited))
                return true;
        }
        visited[i][j] = false;
        return false;
    }
}
// @lc code=end

