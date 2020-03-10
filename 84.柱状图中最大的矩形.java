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
        int[] minLefts = new int[length];
        int[] minRights = new int[length];
        for(int i = 0; i < length; i++){
            int l = i-1;
            while(l >= 0 && heights[l] >= heights[i])
                l = minLefts[l];
            minLefts[i] = l;
        }
        for(int i = length-1; i >= 0; i--){
            int r = i+1;
            while(r < length && heights[r] >= heights[i])
                r = minRights[r];
            minRights[i] = r;
        }
        for(int i = 0; i < length; i++){
            ans = Math.max(ans,(minRights[i]-minLefts[i]-1)*heights[i]);
        }
        return ans;
    }
}
// @lc code=end

