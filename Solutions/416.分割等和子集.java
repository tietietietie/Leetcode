/*
 * @lc app=leetcode.cn id=416 lang=java
 *
 * [416] 分割等和子集
 */

// @lc code=start
class Solution {
    public boolean canPartition(int[] nums) {
        if(nums.length < 2) return false;
        int sum = 0;
        for(int num : nums)
             sum += num;
        if(sum%2 != 0) return false;
        sum /= 2;
        boolean[] dp = new boolean[sum+1];
        dp[0] = true;
        for(int i = 1; i <= nums.length; i++)
            for(int j = sum; j >= 0; j--){
                if(nums[i-1] > j)
                    dp[j] = dp[j];
                else
                    dp[j] = dp[j] || dp[j-nums[i-1]];
            }
        return dp[sum];
    }
}
// @lc code=end

