/*
 * @lc app=leetcode.cn id=435 lang=java
 *
 * [435] 无重叠区间
 */

// @lc code=start
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        int ans = 0;
        Arrays.sort(intervals,(o1,o2) -> o1[0]-o2[0]);
        int curStart = Integer.MIN_VALUE, curEnd = Integer.MIN_VALUE;
        for(int[] interval : intervals){
            if(interval[0] >= curEnd){
                curStart = interval[0];
                curEnd = interval[1];
            }else if(interval[1] < curEnd){
                curStart = interval[0];
                curEnd = interval[1];
                ans++;
            }else
                ans++;
        }
        return ans;
    }
}
// @lc code=end

