/*
 * @lc app=leetcode.cn id=136 lang=java
 *
 * [136] 只出现一次的数字
 */

// @lc code=start
class Solution {
    public int singleNumber(int[] nums) {
        if(nums.length == 0)
            return -1;
        int ans = 0;
        for(int i : nums)
            ans ^= i;
        return ans;
    }
}
// @lc code=end

