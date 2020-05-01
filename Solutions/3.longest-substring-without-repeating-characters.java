/*
 * @lc app=leetcode id=3 lang=java
 *
 * [3] Longest Substring Without Repeating Characters
 */

// @lc code=start
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        int[] a = new int[128];
        for(int i = 0; i < 128; i++)
            a[i] = -1;  //所有字母出现的位置不能在第1位。
        for(int i = 0, j = 0; j < n; j++)
        {
            i = Math.max(a[s.charAt(j)] + 1, i);
            ans = Math.max(ans, j-i+1);
            if(ans >= n-i+1)
                return ans;
            a[s.charAt(j)] = j;
        }
        return ans;
    }
}
// @lc code=end

