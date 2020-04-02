/*
 * @lc app=leetcode.cn id=238 lang=java
 *
 * [238] 除自身以外数组的乘积
 */

// @lc code=start
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] ans = new int[nums.length];
        ans[0] = 1;
        for(int i = 1; i < nums.length; i++)
            ans[i] = ans[i-1]*nums[i-1];
        int right = 1;
        for(int i = nums.length-1; i>= 0; i--){
            ans[i] = ans[i]*right;
            right *= nums[i];
        }
        return ans;
    }
}
// @lc code=end

