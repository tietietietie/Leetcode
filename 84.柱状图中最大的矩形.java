/*
 * @lc app=leetcode.cn id=84 lang=java
 *
 * [84] 柱状图中最大的矩形
 */

// @lc code=start
class Solution {
    public int largestRectangleArea(int[] heights) {
        return caculationArea(heights,0,heights.length -1);
    }

    private int caculationArea(int[] heights, int l, int r){
        if(l > r) return 0;
        if(l == r) return heights[l];
        int min = l;
        for(int i = l; i <= r; i++){
            if(heights[i] < heights[min])
                min = i;
        }
        return Math.max(heights[min]*(r-l+1),Math.max(caculationArea(heights,l,min-1),caculationArea(heights,min+1,r)));
    }
}
// @lc code=end

