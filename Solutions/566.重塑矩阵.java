/*
 * @lc app=leetcode.cn id=566 lang=java
 *
 * [566] 重塑矩阵
 */

// @lc code=start
class Solution {
    public int[][] matrixReshape(int[][] nums, int r, int c) {
        int r0 = nums.length, c0 = nums[0].length;
        if(r0*c0 != r*c) return nums;
        int[][] nNums = new int[r][c];
        int index = 0;
        for(int i = 0; i < r; i++)
            for(int j = 0; j < c; j++){
                int preR = index/c0;
                int preC = index%c0;
                nNums[i][j] = nums[preR][preC];
                index++;
            }
        return nNums;
    }
}
// @lc code=end

