/*
 * @lc app=leetcode.cn id=5 lang=java
 *
 * [5] 最长回文子串
 */

// @lc code=start
class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        if(n == 0)
        return "";
        int pleft = 0, pright = 0;
        for(int i = 0; i < n; i++)
        {
            int len1 = PalindromeCenter(s,i,i);
            int len2 = PalindromeCenter(s,i,i+1);
            int len = Math.max(len1,len2);
            if(len > pright - pleft + 1)
            {
                pleft = i - (len-1)/2;
                pright = i + len/2;
            }
        }
        return s.substring(pleft,pright+1);
    }
    private static int PalindromeCenter(String s, int c1, int c2)
    {
        int len = 0;
        int i = c1, j = c2;
        while(s.charAt(i--) == s.charAt(j++) && i >= 0 && j < s.length())
            len++;
        return len;
    }
    
    public static void main(String[] args) {
        String a = "bababa";
        Solution s = new Solution();
        System.out.print(s.longestPalindrome(a));
    }
}
// @lc code=end

