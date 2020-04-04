/*
 * @lc app=leetcode.cn id=121 lang=java
 *
 * [121] 买卖股票的最佳时机
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        int[] futureMax = new int[prices.length];
        int max = Integer.MIN_VALUE;
        for(int i = prices.length-1; i >= 0; i--){
            if(prices[i] > max)
                max = prices[i];
            futureMax[i] = max;
        }
        int ans = 0;
        for(int i = 0; i < prices.length; i++)
            ans = Math.max(ans,futureMax[i]-prices[i]);
        return ans;
    }
}
// @lc code=end

