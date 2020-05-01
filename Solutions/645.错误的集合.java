/*
 * @lc app=leetcode.cn id=645 lang=java
 *
 * [645] 错误的集合
 */

// @lc code=start
class Solution {
    public int[] findErrorNums(int[] nums) {
        int xor = 0, xor1 = 0, xor2 = 0;
        int length = nums.length;
        for(int i = 1; i <= length; i++)
            xor ^= i;
        for(int i : nums)
            xor ^= i;
        int flag = xor & (-xor);
        for(int i = 1; i <= length; i++){
            if((i & flag) == 0)
                xor1 ^= i;
            else
                xor2 ^= i;
        }
        for(int i : nums){
            if((i & flag) == 0)
                xor1 ^= i;
            else
                xor2 ^= i;
        }
        for(int i : nums){
            if(i == xor1)
                return new int[]{xor1,xor2};
        }
        return new int[]{xor2,xor1};
    }
}
// @lc code=end

