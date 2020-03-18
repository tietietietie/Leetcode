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
        int[] map1 = new int[128];
        int[] map2 = new int[128];
        for(int i = 0; i < length; i++){
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);
            if(map1[c1] != map2[c2])
                return false;
            else if(map1[c1] == 0){
                map1[c1] = i+1;
                map2[c2] = i+1;
            }
        }
        return true;
    }
}
// @lc code=end

