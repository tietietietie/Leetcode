/*
 * @lc app=leetcode.cn id=84 lang=java
 *
 * [84] 柱状图中最大的矩形
 */

// @lc code=start
class Solution {
    public int largestRectangleArea(int[] heights) {
        int length = heights.length;
        if(length == 0) return 0;
        int ans = 0;
        for(int i = 0; i < length; i++){
            int curHeight = heights[i];
            int l = i-1, r = i+1;
            while(r < length && heights[r] >= curHeight)
                r++;
            while(l >= 0 && heights[l] >= curHeight)
                l--;
            ans = Math.max(ans,(r-l-1)*curHeight);
        }
        return ans;
    }
}
// @lc code=end

