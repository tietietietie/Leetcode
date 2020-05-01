/*
 * @lc app=leetcode.cn id=268 lang=java
 *
 * [268] 缺失数字
 */

// @lc code=start
class Solution {
    public int missingNumber(int[] nums) {
        int xor = 0;
        int n = nums.length;
        for(int i = 0; i <= n; i++)
            xor ^= i;
        for(int i : nums)
            xor ^= i;
        return xor;
    }
}
// @lc code=end

