/*
 * @lc app=leetcode.cn id=31 lang=java
 *
 * [31] 下一个排列
 */

// @lc code=start
class Solution {
    public void nextPermutation(int[] nums) {
        int tar = -1;
        for(int i = 0; i < nums.length-1; i++)
        {
            if(nums[i+1] > nums[i])
                tar = i;
        }
        if(tar == -1)
            Arrays.sort(nums);
        else
        {
            int temp = nums[tar];
            nums[tar] = nums[tar+1];
            nums[tar+1] = temp;
        }
    }
}
// @lc code=end

