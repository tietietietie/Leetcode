/*
 * @lc app=leetcode.cn id=518 lang=java
 *
 * [518] 零钱兑换 II
 */

// @lc code=start
class Solution {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount+1];
        dp[0] = 1;
        for(int coin : coins)
            for(int j = coin; j <= amount; j++){
                dp[j] = dp[j] + dp[j-coin];
            }
        return dp[amount];
    }
}
// @lc code=end

