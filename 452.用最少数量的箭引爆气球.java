/*
 * @lc app=leetcode.cn id=452 lang=java
 *
 * [452] 用最少数量的箭引爆气球
 */

// @lc code=start
class Solution {
    public int findMinArrowShots(int[][] points) {
        if(points.length == 0) return 0;
        Arrays.sort(points, (o1,o2) -> o1[1] - o2[1]);
        int ans = 1;
        int curEnd = points[0][1];
        for(int i = 0; i < points.length; i++){
            if(points[i][0] > curEnd){
                ans++;
                curEnd = points[i][1];
            }
        }
        return ans;
    }
}
// @lc code=end

