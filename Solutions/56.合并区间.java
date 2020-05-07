/*
 * @lc app=leetcode.cn id=56 lang=java
 *
 * [56] 合并区间
 */

// @lc code=start
class Solution {
    public int[][] merge(int[][] intervals) {
        int[][] ans = new int[intervals.length][];
        Arrays.sort(intervals, (o1,o2) -> o1[0] - o2[0]);
        int idx = -1;
        for(int[] interval : intervals){
            if(idx == -1 || ans[idx][1] < interval[0])
                ans[++idx] = interval;
            else
                ans[idx][1] = Math.max(ans[idx][1],interval[1]);
        }
        return Arrays.copyOf(ans,idx+1);
    }
}
// @lc code=end

