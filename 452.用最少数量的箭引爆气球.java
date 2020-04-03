/*
 * @lc app=leetcode.cn id=452 lang=java
 *
 * [452] 用最少数量的箭引爆气球
 */

// @lc code=start
class Solution {
    public int findMinArrowShots(int[][] points) {
        if(points.length == 0) return 0;
        Arrays.sort(points, (o1,o2) -> o1[0] - o2[0]);
        int ans = 1;
        for(int i = 1; i < points.length; i++){
            int left = Math.max(points[i-1][0],points[i][0]);
            int right = Math.min(points[i-1][1],points[i][1]);
            if(left > right)
                ans++;
            else{
                points[i][0] = left;
                points[i][1] = right;
            }
        }
        return ans;
    }
}
// @lc code=end

