/*
 * @lc app=leetcode.cn id=34 lang=java
 *
 * [34] 在排序数组中查找元素的第一个和最后一个位置
 */

// @lc code=start
class Solution {
    private int targetBoundary(int[] nums, int target, boolean left){
        int lo = 0, hi = nums.length -1;
        while(lo <= hi){
            int mid = (lo + hi)/2;
            if(nums[mid] > target || (nums[mid] == target && left))
                hi = mid - 1;
            else
                lo = mid + 1;
        }
        return lo;
    }

    public int[] searchRange(int[] nums, int target) {
        int[] ans = {-1,-1};
        int leftIdx = targetBoundary(nums,target,true);
        int rightIdx = targetBoundary(nums,target,false)-1;
        if(leftIdx == nums.length || nums[leftIdx] != target)
            return ans;
        //ans = {leftIdx,rightIdx};
        ans[0] = leftIdx;
        ans[1] = rightIdx;
        return ans;
    }
}
// @lc code=end

