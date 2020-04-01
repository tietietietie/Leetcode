/*
 * @lc app=leetcode.cn id=169 lang=java
 *
 * [169] 多数元素
 */

// @lc code=start
class Solution {
    public int majorityElement(int[] nums) {
        int count = 1, majority = nums[0];
        for(int i = 1; i < nums.length; i++){
            if(count == 0){
                count = 1;
                majority = nums[i];
            }
            else if(nums[i] == majority)
                count++;
            else
                count--;
        }
        return majority;
    }
}
// @lc code=end

