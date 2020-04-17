/*
 * @lc app=leetcode.cn id=650 lang=java
 *
 * [650] 只有两个键的键盘
 */

// @lc code=start
class Solution {
    public int minSteps(int n) {
        int[] dp = new int[n+1];
        dp[1] = 0;
        for(int i = 2; i <= n; i++){
            dp[i] = i;
            for(int j = 2; j < i; j++)
                if(i%j == 0)
                    dp[i] = Math.min(dp[i],dp[j] + i/j);
        }
        return dp[n];
    }
}
// @lc code=end

