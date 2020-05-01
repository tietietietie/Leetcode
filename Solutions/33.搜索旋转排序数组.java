/*
 * @lc app=leetcode.cn id=33 lang=java
 *
 * [33] 搜索旋转排序数组
 */

// @lc code=start
class Solution {
    public int search(int[] nums, int target) {
        //考虑空集
        if(nums.length == 0)
            return -1;
        int left = 0, right = nums.length-1, start;
        // 考虑没有旋转的情况
        if(nums[left] <= nums[right])
            start = 0;
        //旋转点非0时，如何找start
        else
        {
            while(true)
            {
                int mid = (left+right)/2;
                //旋转点非0，则满足：
                if(nums[mid] > nums[mid+1])
                {
                    start = mid+1;
                    break;
                }
                else if(nums[mid] >= nums[left])
                    left = mid+1;
                else 
                    right = mid-1;
            }
        }

        //Three conditions
        if(start == 0)
            return bisort(nums,0,nums.length-1,target);     
        if(target >= nums[0])
            return bisort(nums,0,start-1,target);
        else 
            return bisort(nums,start,nums.length-1,target);
    }

    private int bisort(int[] nums, int lo, int hi, int tar)
    {
        int ans = -1;
        while(lo <= hi)
        {
            int mid = (lo+hi)/2;
            if(nums[mid] == tar)
                {
                    ans = mid;
                    break;
                }
            else if(nums[mid] > tar)
                hi = mid-1;
            else
                lo = mid+1;
        }
        return ans;
    }
}
// @lc code=end

