/*
 * @lc app=leetcode.cn id=205 lang=java
 *
 * [205] 同构字符串
 */

// @lc code=start
class Solution {
    public boolean isIsomorphic(String s, String t) {
        if(s.length() != t.length()) return false;
        int length = s.length();
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        HashMap<Character,Character> map1 = new HashMap<>();
        for(int i = 0; i < length; i++){
            if(map1.get(tChars[i]) == null)
                map1.put(tChars[i],sChars[i]);
            else if(map1.get(tChars[i]) != sChars[i])
                return false;
        }
        HashMap<Character,Character> map2 = new HashMap<>();
        for(int i = 0; i < length; i++){
            if(map2.get(sChars[i]) == null)
                map2.put(sChars[i],tChars[i]);
            else if(map2.get(sChars[i]) != tChars[i])
                return false;
        }
        return true;
    }
}
// @lc code=end

