/*
 * @lc app=leetcode.cn id=242 lang=java
 *
 * [242] 有效的字母异位词
 */

// @lc code=start
class Solution {
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()) return false;
        int length = s.length();
        int[] counter = new int[26];
        for(int i = 0; i < length; i++){
            counter[s.charAt(i)-'a']++;
        }
        for(int i = 0; i < length; i++){
            counter[t.charAt(i)-'a']--;
            if(counter[t.charAt(i)-'a'] < 0)
                return false;
        }
        return true;
    }
}
// @lc code=end

