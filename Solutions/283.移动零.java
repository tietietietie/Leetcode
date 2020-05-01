/*
 * @lc app=leetcode.cn id=283 lang=java
 *
 * [283] 移动零
 */

// @lc code=start
class Solution {
    public void moveZeroes(int[] nums) {
        int length = nums.length;
        int l = -1, cur = 0;
        while(cur < length && nums[cur] != 0)
            cur++;
        l = cur;
        while(cur < length){
            if(nums[cur] == 0)
                cur++;
            else{
                swap(nums,l,cur);
                l++;
                cur++;
            }
        }
    }

    private void swap(int[] nums, int l, int cur){
        int temp = nums[l];
        nums[l] = nums[cur];
        nums[cur] = temp; 
    }
}
// @lc code=end

