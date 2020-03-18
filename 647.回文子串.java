/*
 * @lc app=leetcode.cn id=647 lang=java
 *
 * [647] 回文子串
 */

// @lc code=start
class Solution {
    public int countSubstrings(String s) {
        int length = s.length();
        if(length == 0) return 0;
        char[] chars = s.toCharArray();
        int count = 0;
        for(int i = 0; i < length; i++)
            for(int j = i; j < length; j++)
                if(isPalindrome(chars,i,j))
                    count++;
        return count;
    }

    private boolean isPalindrome(char[] chars, int l, int r){
        while(l < r){
            if(chars[l] == chars[r]){
                l++;
                r--;
            }else{
                return false;
            }
        }
        return true;   
    }
}
// @lc code=end

