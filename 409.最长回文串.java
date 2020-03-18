/*
 * @lc app=leetcode.cn id=409 lang=java
 *
 * [409] 最长回文串
 */

// @lc code=start
class Solution {
    public int longestPalindrome(String s) {
        char[] chars = s.toCharArray();
        int[] map = new int[128];
        for(char c : chars)
            map[c]++;
        int pair = 0;
        for(int i : map)
            pair += (i/2);
        int ans = pair*2;
        if(ans < chars.length)
            ans++;
        return ans;
    }
}
// @lc code=end

