/*
 * @lc app=leetcode.cn id=714 lang=java
 *
 * [714] 买卖股票的最佳时机含手续费
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        if(n < 2) return 0;
        int dp_i_0 = 0;
        int dp_i_1 = -prices[0];
        for(int i = 2; i <= n; i++){
            int temp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0,dp_i_1 + prices[i-1] - fee);
            dp_i_1 = Math.max(dp_i_1,temp - prices[i-1]);
        }
        return dp_i_0;
    }
}
// @lc code=end

