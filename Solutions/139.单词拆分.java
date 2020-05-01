/*
 * @lc app=leetcode.cn id=139 lang=java
 *
 * [139] 单词拆分
 */

// @lc code=start
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()+1];
        dp[0] = true;
        for(int i = 1; i <= s.length(); i++)
            for(String word : wordDict){
                int wordLen = word.length();
                if(wordLen <= i && word.equals(s.substring(i-wordLen,i)))
                    dp[i] = dp[i-wordLen] || dp[i];
            }
        return dp[s.length()];
    }
}
// @lc code=end

