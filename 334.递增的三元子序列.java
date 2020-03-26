/*
 * @lc app=leetcode.cn id=334 lang=java
 *
 * [334] 递增的三元子序列
 */

// @lc code=start
class Solution {
    public boolean increasingTriplet(int[] nums) {
        int min = Integer.MAX_VALUE, mid = Integer.MAX_VALUE;
        int length = nums.length;
        for(int i = 0; i < length; i++){
            if(nums[i] <= min)
                min = nums[i];
            else if(nums[i] <= mid)
                mid = nums[i];
            else
                return true;
        }
        return false;
    }
}
// @lc code=end

