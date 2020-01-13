/*
 * @lc app=leetcode.cn id=35 lang=java
 *
 * [35] 搜索插入位置
 */

// @lc code=start
class Solution {
    public int searchInsert(int[] nums, int target) {
        if(nums.length == 0)
            return 0;
        int lo = 0, hi = nums.length-1;
        while(lo < hi){
            int mid = (lo+hi)/2;
            if(nums[mid] == target)
                return mid;
            else if(nums[mid] < target)
                lo = mid+1;
            else
                hi = mid-1;
        }
        if(nums[lo] >= target)
            return lo;
        else 
            return lo+1;
    }
}
// @lc code=end

