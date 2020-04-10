/*
 * @lc app=leetcode.cn id=279 lang=java
 *
 * [279] 完全平方数
 */

// @lc code=start
class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n+1];
        dp[1] = 1;
        for(int i = 2; i <= n; i++){
            if((int)Math.sqrt(i) == Math.sqrt(i)){
                dp[i] = 1;
            }else{
                dp[i] = i;
                for(int j = 1; j <= (int)Math.sqrt(i); j++)
                    dp[i] = Math.min(dp[i],1+dp[i-j*j]);
            }
        }
        return dp[n];
    }
}
// @lc code=end

