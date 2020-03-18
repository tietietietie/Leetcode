/*
 * @lc app=leetcode.cn id=409 lang=java
 *
 * [409] 最长回文串
 */

// @lc code=start
class Solution {
    public int longestPalindrome(String s) {
        char[] chars = s.toCharArray();
        int[] map1 = new int[26];
        int[] map2 = new int[26];
        for(char c : chars){
            if(c > 'Z')
                map1[c-'a']++;
            else
                map2[c-'A']++;
        }
        int pair = 0;
        for(int i : map1)
            pair += (i/2);
        for(int i : map2)
            pair += (i/2);
        int ans = pair*2;
        if(ans < chars.length)
            ans++;
        return ans;
    }
}
// @lc code=end

