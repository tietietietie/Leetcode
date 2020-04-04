/*
 * @lc app=leetcode.cn id=435 lang=java
 *
 * [435] 无重叠区间
 */

// @lc code=start
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        if(intervals.length == 0) return 0;
        Arrays.sort(intervals,(o1,o2) -> o1[1]-o2[1]);
        int count = 1;
        int curEnd = intervals[0][1];
        for(int i = 1; i < intervals.length; i++)
            if(intervals[i][0] >= curEnd){
                count++;
                curEnd = intervals[i][1];
            }  
        return intervals.length-count;
    }
}
// @lc code=end

