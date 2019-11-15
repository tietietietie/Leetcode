/*
 * @lc app=leetcode id=5 lang=java
 *
 * [5] Longest Palindromic Substring
 */

// @lc code=start
class Solution {
    public String longestPalindrome(String s) {
        if(s.length() == 0)
            return "";
        int pleft = 0, pright = 0, maxlen = 0;
        for (int i = 0; i < s.length(); i++)
            for (int j = i + 1; j < s.length(); j++) {
                if (isPalindrome(s, i, j) && j - i + 1 > maxlen) {
                    maxlen = j - i + 1;
                    pleft = i;
                    pright = j;
                }
            }

        return s.substring(pleft, pright+1);
    }

    private boolean isPalindrome(String s, int left, int right) {
        for (int i = 0; i <= (right - left + 1) / 2; i++)
            if (s.charAt(left + i) != s.charAt(right - i))
                return false;
        return true;
    }
}
// @lc code=end
