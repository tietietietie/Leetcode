/*
 * @lc app=leetcode.cn id=139 lang=java
 *
 * [139] 单词拆分
 */

// @lc code=start
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        return wordBreak(s,new HashSet<String>(wordDict), 0);
    }
    private boolean wordBreak(String s, HashSet<String> set, int start){
        if(start == s.length()) return true;
        for(int end = start+1; end <= s.length(); end++)
            if(set.contains(s.substring(start,end)) && wordBreak(s,set,end))
                return true;
        return false;
    }
}
// @lc code=end

