/*
 * @lc app=leetcode.cn id=485 lang=java
 *
 * [485] 最大连续1的个数
 */

// @lc code=start
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int length = nums.length;
        int count = 0;
        int maxCount = 0;
        for(int i = 0; i < length; i++){
            if(nums[i] == 1)
                count++;
            else{
                maxCount = Math.max(maxCount,count);
                count = 0;
            }
        }
        return Math.max(maxCount,count);
    }
}
// @lc code=end

