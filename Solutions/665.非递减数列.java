/*
 * @lc app=leetcode.cn id=665 lang=java
 *
 * [665] 非递减数列
 */

// @lc code=start
class Solution {
    public boolean checkPossibility(int[] nums) {
        boolean changed = false;
        for(int i = 0; i < nums.length-1; i++){
            if(!changed && nums[i] > nums[i+1]){
                changed = true;
                int lTemp = i-1 >= 0 ? nums[i-1] : Integer.MIN_VALUE;
                int rTemp = i+2 < nums.length ? nums[i+2] : Integer.MAX_VALUE;
                if(lTemp > nums[i+1] && rTemp < nums[i])
                    return false;
            }else if(changed && nums[i] > nums[i+1])
                return false;
        }
        return true;
    }
}
// @lc code=end

