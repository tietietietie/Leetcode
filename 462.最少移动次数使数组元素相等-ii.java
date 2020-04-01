/*
 * @lc app=leetcode.cn id=462 lang=java
 *
 * [462] 最少移动次数使数组元素相等 II
 */

// @lc code=start
class Solution {
    public int minMoves2(int[] nums) {
        int target = select(nums,0,nums.length-1,nums.length/2);
        int ans = 0;
        for(int i : nums)
            ans += Math.abs(target - i);
        return ans;
    }
    
    private int select(int[] nums, int l, int r, int k){
        int pivotIdx = partition(nums,l,r);
        if(pivotIdx == k)
            return nums[k];
        else if(pivotIdx < k)
            return select(nums,pivotIdx+1,r,k);
        return select(nums,l,pivotIdx-1,k);
    }

    private int partition(int[] nums, int l, int r){
        int pivotValue = nums[l];
        while(l < r){
            while(l < r && nums[r] >= pivotValue) r--;
            nums[l] = nums[r];
            while(l < r && nums[l] <= pivotValue) l++;
            nums[r] = nums[l];
        }
        nums[l] = pivotValue;
        return l;
    }
}
// @lc code=end

