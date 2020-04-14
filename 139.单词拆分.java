/*
 * @lc app=leetcode.cn id=139 lang=java
 *
 * [139] 单词拆分
 */

// @lc code=start
class Solution {
    public Boolean[] memo;
    public boolean wordBreak(String s, List<String> wordDict) {
        memo = new Boolean[s.length()+1];
        return wordBreak(s,new HashSet<String>(wordDict), 0);
    }
    private boolean wordBreak(String s, HashSet<String> set, int start){
        if(start == s.length()){
            return memo[s.length()] = true;
        }
        if(memo[start] != null)
            return memo[start];
        for(int end = start+1; end <= s.length(); end++)
            if(set.contains(s.substring(start,end)) && wordBreak(s,set,end))
                return memo[start] = true;
        return memo[start] = false;
    }
}
// @lc code=end

