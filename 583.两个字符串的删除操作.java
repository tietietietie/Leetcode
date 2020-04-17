/*
 * @lc app=leetcode.cn id=583 lang=java
 *
 * [583] 两个字符串的删除操作
 */

// @lc code=start
class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[] dp = new int[n+1];
        for(int i = 0; i <= m; i++){
            int[] temp = new int[n+1];
            for(int j = 0; j <= n; j++){
                if(i == 0 || j == 0){
                    temp[j] = i+j;
                    continue;
                }
                if(word1.charAt(i-1) == word2.charAt(j-1))
                    temp[j] = dp[j-1];
                else{
                    temp[j] = Math.min(temp[j-1]+1,dp[j]+1);
                }
            }
            dp = temp;
        }
            
        return dp[n];
    }
}
// @lc code=end

