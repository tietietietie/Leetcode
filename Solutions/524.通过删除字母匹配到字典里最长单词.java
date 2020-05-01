/*
 * @lc app=leetcode.cn id=524 lang=java
 *
 * [524] 通过删除字母匹配到字典里最长单词
 */

// @lc code=start
class Solution {
    public String findLongestWord(String s, List<String> d) {
        String ans = "";
        for(String word : d){
            int length1 = ans.length(), length2 = word.length();
            if(length1 > length2) continue;
            int i = 0;
            for(char c : s.toCharArray())
                if(i < length2 && c == word.charAt(i)) i++;            
            if(i == length2 && (length1 < length2 || word.compareTo(ans) < 0))
                ans = word;
        }
        return ans;
    }
}
// @lc code=end

