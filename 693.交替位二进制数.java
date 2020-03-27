/*
 * @lc app=leetcode.cn id=693 lang=java
 *
 * [693] 交替位二进制数
 */

// @lc code=start
class Solution {
    public boolean hasAlternatingBits(int n) {
        int temp = (n ^ (n >> 1));
        return (temp & (temp+1)) == 0;
    }
}
// @lc code=end

