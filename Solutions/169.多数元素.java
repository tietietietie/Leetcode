/*
 * @lc app=leetcode.cn id=169 lang=java
 *
 * [169] 多数元素
 */

// @lc code=start
class Solution {
    public int majorityElement(int[] nums) {
        int[] bit = new int[32];
        for(int num : nums)
            for(int i = 0; i < 32; i++)
                bit[i] += (num>>>i) & 1;
        int majority = 0;
        for(int i = 0; i < 32; i++){
            bit[i] = bit[i] > nums.length/2 ? 1 : 0;
            majority += (bit[i]<<i);
        }
        return majority;
    }
}
// @lc code=end

