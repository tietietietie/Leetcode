/*
 * @lc app=leetcode.cn id=647 lang=java
 *
 * [647] 回文子串
 */

// @lc code=start
class Solution {
    public int countSubstrings(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int ans = 0;
        for(int i = n-1; i >= 0; i--)
            for(int j = i; j < n; j++){
                dp[i][j] = s.charAt(i) == s.charAt(j) && (j-i <= 1 || dp[i+1][j-1]);
                if(dp[i][j]) ans++;
            }
        return ans;
    }
}
// @lc code=end

