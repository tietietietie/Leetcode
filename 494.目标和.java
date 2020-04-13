/*
 * @lc app=leetcode.cn id=494 lang=java
 *
 * [494] 目标和
 */

// @lc code=start
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        return dfs(nums,0,S);
    }

    private int dfs(int[] nums, int i, int target){
        if(i == nums.length && target == 0) return 1;
        if(i == nums.length) return 0;
        int ans = 0;
        ans += dfs(nums,i+1,target-nums[i]);
        ans += dfs(nums,i+1,target+nums[i]);
        return ans;
    }
}
// @lc code=end

