/*
 * @lc app=leetcode.cn id=322 lang=java
 *
 * [322] 零钱兑换
 */

// @lc code=start
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[][] dp = new int[coins.length+1][amount+1];
        Arrays.fill(dp[0],-1);
        for(int i = 1; i <= coins.length; i++)
            dp[i][0] = 0;
        for(int i = 1; i <= coins.length; i++)
            for(int j = 1; j <= amount; j++){
                if(j < coins[i-1])
                    dp[i][j] = dp[i-1][j];
                else if(dp[i][j-coins[i-1]] != -1 && dp[i-1][j] != -1)
                    dp[i][j] = Math.min(dp[i-1][j],dp[i][j-coins[i-1]]+1);
                else if(dp[i][j-coins[i-1]] != -1)
                    dp[i][j] = dp[i][j-coins[i-1]]+1;
                else if(dp[i-1][j] != -1)
                    dp[i][j] = dp[i-1][j];
                else
                    dp[i][j] = -1;
            }
        return dp[coins.length][amount];
    }
}
// @lc code=end

