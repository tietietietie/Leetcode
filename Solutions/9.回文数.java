/*
 * @lc app=leetcode.cn id=9 lang=java
 *
 * [9] 回文数
 */

// @lc code=start
class Solution {
    public boolean isPalindrome(int x) {
        if(x < 0) return false;
        int palindrome = 0;
        int temp = x;
        while(temp != 0){
            int digit = temp%10;
            temp /= 10;
            palindrome = palindrome*10+digit;
        }
        return palindrome == x;
    }
}
// @lc code=end

