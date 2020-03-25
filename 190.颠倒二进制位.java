/*
 * @lc app=leetcode.cn id=190 lang=java
 *
 * [190] 颠倒二进制位
 */

// @lc code=start
public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int ans = 0;
        for(int i = 0; i < 32; i++){
            int digit = (n & 1);
            n = (n >>> 1);
            ans = (ans << 1);
            ans += digit;
        }
        return ans;
    }
}
// @lc code=end

