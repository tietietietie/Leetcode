/*
 * @lc app=leetcode.cn id=485 lang=java
 *
 * [485] 最大连续1的个数
 */

// @lc code=start
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int length = nums.length;
        int cur = 0;
        int ans = 0;
        while(cur < length){
            while(cur < length && nums[cur] == 0)
                cur++;
            int l = cur;
            while(cur < length && nums[cur] == 1)
                cur++;
            ans = Math.max(ans,cur-l);
        }
        return ans;
    }
}
// @lc code=end

