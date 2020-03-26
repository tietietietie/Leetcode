/*
 * @lc app=leetcode.cn id=231 lang=java
 *
 * [231] 2的幂
 */

// @lc code=start
class Solution {
    public boolean isPowerOfTwo(int n) {
        if(n <= 0) return false;
        return n == (n & (-n));
    }
}
// @lc code=end

