/*
 * @lc app=leetcode.cn id=474 lang=java
 *
 * [474] 一和零
 */

// @lc code=start
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m+1][n+1];
        for(int i = 0; i < strs.length; i++){
            int[] count = countStr(strs[i]);
            for(int j = m; j >= count[0]; j--)
                for(int k = n; k >= count[1]; k--){
                    dp[j][k] = Math.max(dp[j][k],dp[j-count[0]][k-count[1]]+1);
                }
        }  
        return dp[m][n];
    }

    private int[] countStr(String s){
        int[] count = new int[2];
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '0')
                count[0] += 1;
            else
                count[1] += 1;
        }
        return count;
    }
}
// @lc code=end

