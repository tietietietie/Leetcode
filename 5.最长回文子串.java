/*
 * @lc app=leetcode.cn id=5 lang=java
 *
 * [5] 最长回文子串
 */

// @lc code=start
class Solution {
    public String longestPalindrome(String s) {
        int n = s.length(),maxlen = 0,pleft = 0,pright = 0;
        if(n == 0)
            return "";
        boolean[][] p = new boolean[n][n];
        for(int len = 1; len <= n ;len++)
            for(int start = 0; start < n; start++)
            {
                int end = start + len - 1;
                if(end >= n)
                    break;
                p[start][end] = (len == 1 || len == 2 || p[start+1][end-1]) && s.charAt(start) == s.charAt(end);
                if(p[start][end]&&(len > maxlen))
                {
                    pleft = start;
                    pright = end;
                }
            }
        return s.substring(pleft,pright+1);
            }   
    public static void main(String[] args) {
        String a = "bababa";
        Solution s = new Solution();
        System.out.print(s.longestPalindrome(a));
    }
}
// @lc code=end

