/*
 * @lc app=leetcode.cn id=680 lang=java
 *
 * [680] 验证回文字符串 Ⅱ
 */

// @lc code=start
class Solution {
    public boolean validPalindrome(String s) {
        char[] chars = s.toCharArray();
        int l = 0, r = chars.length-1;
        boolean deleted = false;
        return validPalindrome(chars,l,r,deleted);
    }


    private boolean validPalindrome(char[] chars, int l, int r, boolean deleted){
        if(l >= r) return true;
        if(chars[l] != chars[r] && deleted) return false;
        if(chars[l] != chars[r]){
            deleted = true;
            return validPalindrome(chars,l+1,r,deleted) || validPalindrome(chars,l,r-1,deleted);
        }
        return validPalindrome(chars,l+1,r-1,deleted);
    }
}
// @lc code=end

