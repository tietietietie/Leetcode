/*
 * @lc app=leetcode.cn id=121 lang=java
 *
 * [121] 买卖股票的最佳时机
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        if(prices.length < 2) return 0; 
        int preMin = prices[0];
        int ans = 0;
        for(int i = 1; i < prices.length; i++){
            ans = Math.max(prices[i]-preMin,ans);
            if(prices[i] < preMin)
                preMin = prices[i];
        }
        return ans;
    }
}
// @lc code=end

