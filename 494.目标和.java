/*
 * @lc app=leetcode.cn id=494 lang=java
 *
 * [494] 目标和
 */

// @lc code=start
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        int[][] dp = new int[nums.length][2001];
        dp[0][nums[0]+1000] = 1;
        dp[0][-nums[0]+1000] += 1;
        for(int i = 1; i < nums.length; i++)
            for(int j = -1000; j <= 1000; j++){
                if(j-nums[i] >= -1000)
                    dp[i][j+1000] += dp[i-1][j-nums[i]+1000];
                if(j+nums[i] <= 1000)
                    dp[i][j+1000] += dp[i-1][j+nums[i]+1000];
            }
        return S > 1000 ? 0 : dp[nums.length-1][S+1000];
    }
}
// @lc code=end

