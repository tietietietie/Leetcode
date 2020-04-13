/*
 * @lc app=leetcode.cn id=494 lang=java
 *
 * [494] 目标和
 */

// @lc code=start
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        int sum = 0;
        for(int num : nums)
            sum += num;
        if((sum+S)%2 != 0 || S > sum) return 0;
        sum = (sum+S)/2;
        int[] dp = new int[sum+1];
        dp[0] = 1;
        for(int i = 0; i < nums.length; i++)
            for(int j = sum; j >= nums[i]; j--)
                    dp[j] += dp[j-nums[i]];
        return dp[sum];
    }
}
// @lc code=end

