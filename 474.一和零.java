/*
 * @lc app=leetcode.cn id=474 lang=java
 *
 * [474] 一和零
 */

// @lc code=start
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        int[][][] dp = new int[strs.length+1][m+1][n+1];
        for(int i = 1; i <= strs.length; i++){
            int[] count = countStr(strs[i-1]);
            for(int j = 0; j <= m; j++)
                for(int k = 0; k <= n; k++){
                    if(j >= count[0] && k >= count[1])
                        dp[i][j][k] = Math.max(dp[i-1][j][k],dp[i-1][j-count[0]][k-count[1]]+1);
                    else
                        dp[i][j][k] = dp[i-1][j][k];
                }
        }  
        return dp[strs.length][m][n];
    }

    private int[] countStr(String s){
        int[] count = new int[2];
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '0')
                count[0] = count[0]+1;
            else
                count[1] = count[1]+1;
        }
        return count;
    }
}
// @lc code=end

