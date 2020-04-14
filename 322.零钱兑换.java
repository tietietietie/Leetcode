/*
 * @lc app=leetcode.cn id=322 lang=java
 *
 * [322] 零钱兑换
 */

// @lc code=start
class Solution {
    private int ans;
    public int coinChange(int[] coins, int amount) {
        ans = Integer.MAX_VALUE;
        Arrays.sort(coins);
        coinChange(coins, coins.length-1, 0, amount);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
    private void coinChange(int[] coins, int start, int count, int amount){
        if(amount == 0){
            ans = Math.min(count,ans);
            return;
        }
        if(start == -1) return;
        for(int i = amount/coins[start]; i >= 0 && count+i < ans; i--){
            coinChange(coins,start-1,count+i,amount-i*coins[start]);
        }
    }
}
// @lc code=end

