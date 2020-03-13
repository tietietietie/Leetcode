/*
 * @lc app=leetcode.cn id=645 lang=java
 *
 * [645] 错误的集合
 */

// @lc code=start
class Solution {
    public int[] findErrorNums(int[] nums) {
        Arrays.sort(nums);
        int length = nums.length;
        int dup = -1, missing = 1;
        for(int i = 1; i < length; i++){
            if(nums[i] == nums[i-1])
                dup = nums[i];
            else if(nums[i] > nums[i-1]+1)
                missing = nums[i-1]+1;
        }
        if(nums[length-1] != length)
            missing = length;
        int[] ans = new int[]{dup,missing};
        return ans;
    }
}
// @lc code=end

