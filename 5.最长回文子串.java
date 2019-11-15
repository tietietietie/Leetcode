/*
 * @lc app=leetcode.cn id=5 lang=java
 *
 * [5] 最长回文子串
 */

// @lc code=start
class Solution {
    public String longestPalindrome(String s) {
        int pleft = 0, pright = 0, maxlen = 0;
        for(int i = 0; i < s.length(); i++)
            for(int j = i+1; j < s.length(); j++)
            {
               if(isPalindrome(s, i, j) && j-i+1 > maxlen)
               {
                   maxlen = j-i+1;
                   pleft = i;
                   pright = j;
               }
            }
        return s.substring(pleft,pright);
    }

    private boolean isPalindrome(String s,int left, int right){
        for(int i = 0; i <= (right -left + 1)/2; i++)
            if(s.charAt(left+i) != s.charAt(right-i))
                return false;
        return true;
    }

    public static void main(String[] args) {
        String a = "bababa";
        Solution s = new Solution();
        System.out.print(s.longestPalindrome(a));
    }
}
// @lc code=end

