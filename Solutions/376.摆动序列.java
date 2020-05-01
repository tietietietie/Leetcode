/*
 * @lc app=leetcode.cn id=376 lang=java
 *
 * [376] 摆动序列
 */

// @lc code=start
class Solution {
    public int wiggleMaxLength(int[] nums) {
        if(nums.length < 2) return nums.length;
        int preDiff = nums[1] - nums[0];
        int ans = preDiff == 0 ? 1 : 2;
        for(int i = 2; i < nums.length; i++){
            int diff = nums[i] - nums[i-1];
            if((preDiff >= 0 && diff < 0) || (preDiff <= 0 && diff > 0)){
                ans++;
                preDiff = diff;
            }
        }
        return ans;
    }
}
// @lc code=end

