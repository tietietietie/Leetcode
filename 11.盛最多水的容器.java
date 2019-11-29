/*
 * @lc app=leetcode.cn id=11 lang=java
 *
 * [11] 盛最多水的容器
 */

// @lc code=start
class Solution {
    public int maxArea(int[] height) {
        int n = height.length;
        int left = 0, right = n-1, maxarea = 0, area = 0;
        while(left != right)
        {
            area = (right-left+1)*Math.min(height[right],height[left]);
            maxarea = Math.max(area,maxarea);
            if(height[left] < height[right])
                left++;
            else
                right--;
        }
        return maxarea;
    }
}
// @lc code=end

