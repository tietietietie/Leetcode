/*
 * @lc app=leetcode.cn id=11 lang=java
 *
 * [11] 盛最多水的容器
 */

// @lc code=start
class Solution {
    public int maxArea(int[] height) {
        int n = height.length;
        int maxarea = 0, area = 0;
        for(int i = 0; i < n-1; i++)
            for(int j = i+1; j < n; j++)
            {
                area = (j-i) * Math.min(height[i], height[j]);
                maxarea = Math.max(maxarea, area);
            }
        return maxarea;
    }
}
// @lc code=end

