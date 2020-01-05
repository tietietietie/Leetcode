/*
 * @lc app=leetcode.cn id=33 lang=java
 *
 * [33] 搜索旋转排序数组
 */

// @lc code=start
class Solution {
    public int search(int[] nums, int target) {
        if(nums.length < 2)
        {
            int ans = -1;
            for(int i = 0; i < nums.length; i++)
                if(nums[i] == target)
                {
                    ans = i;
                    break;
                }
            return ans;
        }
        int left = 0, right = nums.length-1, start;
        while(true)
        {
            int mid = (left+right)/2;
            if(nums[mid] < nums[mid+1] && nums[mid] < nums[mid-1])
            {
                start = mid;
                break;
            }
            else if(nums[mid-1] < nums[mid] && nums[mid+1] < nums[mid])
            {
                start = mid+1;
                break;
            }
            else if(nums[mid] > left && nums[mid] > right)
                left = mid+1;
            else 
                right = mid-1;
        }
        
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

