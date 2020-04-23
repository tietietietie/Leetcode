/*
 * @lc app=leetcode.cn id=37 lang=java
 *
 * [37] 解数独
 */

// @lc code=start
class Solution {
    private boolean[][] rowsUsed, colsUsed, cubesUsed;
    private char[][] board;
    public void solveSudoku(char[][] board) {
        this.board = board;
        rowsUsed = new boolean[9][10];
        colsUsed = new boolean[9][10];
        cubesUsed = new boolean[9][10];
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                if(board[i][j] != '.'){
                    int value = board[i][j]-'0';
                    rowsUsed[i][value] = true;
                    colsUsed[j][value] = true;
                    cubesUsed[cptCube(i,j)][value] = true;
                }
        dfs(0,0);
    }

    private boolean dfs(int row, int col){
        while(row < 9 && board[row][col] != '.'){
            row = col == 8 ? row+1 : row;
            col = col == 8 ? 0 : col+1;
        }
        if(row == 9) return true;
        for(int num = 1; num <= 9; num++){
            if(rowsUsed[row][num] || colsUsed[col][num] || cubesUsed[cptCube(row,col)][num])
                continue;
            board[row][col] = (char)('0'+num);
            rowsUsed[row][num] = colsUsed[col][num] = cubesUsed[cptCube(row,col)][num] = true;
            if(dfs(row,col))
                return true;
            board[row][col] = '.';
            rowsUsed[row][num] = colsUsed[col][num] = cubesUsed[cptCube(row,col)][num] = false;
        }
        return false;
    }

    private int cptCube(int row, int col){
        return (row/3)*3 + col/3;
    }
}
// @lc code=end

