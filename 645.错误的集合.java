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
        int dup = 1;
        for(; dup < length; dup++)
            if(nums[dup] == nums[dup-1])
                break;
        int[] ans = new int[2];
        ans[0] = nums[dup];
        int curSum = 0;
        for(int i : nums)
            curSum += i;
        int preSum = (length+1)*(length)/2;
        ans[1] = ans[0] + preSum - curSum;
        return ans;
    }
}
// @lc code=end

