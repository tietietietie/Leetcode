/*
 * @lc app=leetcode.cn id=188 lang=java
 *
 * [188] 买卖股票的最佳时机 IV
 */

// @lc code=start
class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if(n < 2 || k <= 0) return 0;
        if(k > n/2){
            int ans = 0;
            for(int i = 1; i < prices.length; i++)
                if(prices[i] - prices[i-1] > 0)
                    ans += prices[i] - prices[i-1];
            return ans;
        }
        int[][][] dp = new int[n+1][k+1][2];
        for(int i = 0; i <= k; i++){
            dp[1][i][0] = 0;
            dp[1][i][1] = -prices[0];
        }
        dp[1][0][1] = Integer.MIN_VALUE;
        for(int i = 2; i <= n; i++)
            for(int j = 1; j <= k; j++){
                dp[i][j][0] = Math.max(dp[i-1][j][0],dp[i-1][j][1]+prices[i-1]);
                dp[i][j][1] = Math.max(dp[i-1][j][1],dp[i-1][j-1][0]-prices[i-1]);
            }
        return dp[n][k][0];
    }
}
// @lc code=end

