/*
 * @lc app=leetcode.cn id=309 lang=java
 *
 * [309] 最佳买卖股票时机含冷冻期
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if(n < 2) return 0;
        int dp_i_0 = 0;
        int dp_i_1 = -prices[0];
        int dp_pre_0 = 0;
        for(int i = 2; i <= n; i++){
            int temp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i-1]);
            dp_i_1 = Math.max(dp_i_1, dp_pre_0 - prices[i-1]);
            dp_pre_0 = temp;
        }
        return dp_i_0;
    }
}
// @lc code=end

