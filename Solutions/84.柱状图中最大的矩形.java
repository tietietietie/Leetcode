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
        int mid = (l+r)/2;
        int curL = mid;
        int curR = mid;
        int minH = heights[mid];
        int midArea = minH;
        while(curL >= l && curR <= r){
            minH = Math.min(minH, Math.min(heights[curL], heights[curR]));
            midArea = Math.max(midArea,minH*(curR-curL+1));
            if(curL == l)
                curR++;
            else if(curR == r)
                curL--;
            else if(heights[curL-1] >= heights[curR+1])
                curL--;
            else 
                curR++;
        }
        return Math.max(midArea,Math.max(caculationArea(heights,l,mid-1),caculationArea(heights,mid+1,r)));
    }
}
// @lc code=end

