/*
 * @lc app=leetcode.cn id=540 lang=java
 *
 * [540] 有序数组中的单一元素
 */

// @lc code=start
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int l = 0, r = nums.length-1;
        while(l < r){
            int mid = (l+r)/2;
            if(mid%2 == 1) mid--;
            if(nums[mid] == nums[mid+1])
                l = mid+2;
            else
                r = mid;
        }
        return nums[l];
    }
}
// @lc code=end

