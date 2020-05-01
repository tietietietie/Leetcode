/*
 * @lc app=leetcode.cn id=122 lang=java
 *
 * [122] 买卖股票的最佳时机 II
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length < 2) return 0;
        int ans = 0;
        for(int i = 0; i < prices.length-1; i++)
            ans += prices[i+1]-prices[i] > 0 ? prices[i+1]-prices[i] : 0;
        return ans;
    }
}
// @lc code=end

