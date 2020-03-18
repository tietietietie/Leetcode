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
        HashMap<Character,Character> map = new HashMap<>();
        for(int i = 0; i < length; i++){
            if(map.get(tChars[i]) == null){
                if(map.containsValue(sChars[i]))
                    return false;
                else
                    map.put(tChars[i],sChars[i]);
            }else if(map.get(tChars[i]) != sChars[i])
                return false;
        }
        return true;
    }
}
// @lc code=end

