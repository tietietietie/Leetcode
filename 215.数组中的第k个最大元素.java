/*
 * @lc app=leetcode.cn id=215 lang=java
 *
 * [215] 数组中的第K个最大元素
 */

// @lc code=start
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int l = 0, r = nums.length-1;
        int k1 = partition(nums,l,r);
        k = nums.length-k;
        while(k1 != k){
            if(k1 < k){
                l = k1+1;
                k1 = partition(nums,l,r);
            }else if(k1 > k){
                r = k1-1;
                k1 = partition(nums,l,r);
            }
        }
        return nums[k1];
    }

    public int partition(int[] nums, int l, int r){
        int target = nums[l];
        while(l < r){
            while(l < r && nums[r] >= target) r--;
            nums[l] = nums[r];
            while(l < r && nums[l] <= target) l++;
            nums[r] = nums[l];
        }
        nums[l] = target;
        return l;
    }
}
// @lc code=end

