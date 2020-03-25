/*
 * @lc app=leetcode.cn id=260 lang=java
 *
 * [260] 只出现一次的数字 III
 */

// @lc code=start
class Solution {
    public int[] singleNumber(int[] nums) {
        int n = nums.length;
        if(n < 2) return new int[]{-1,-1};
        int xor = 0;
        for(int i : nums)
            xor ^= i;
        int cmp = xor & (-xor);
        int ans1 = 0, ans2 = 0;
        for(int i : nums){
            if((i&cmp) == 0)
                ans1 ^= i;
            else
                ans2 ^= i;
        }
        return new int[]{ans1,ans2};
    }
}
// @lc code=end

