/*
 * @lc app=leetcode.cn id=594 lang=java
 *
 * [594] 最长和谐子序列
 */

// @lc code=start
class Solution {
    public int findLHS(int[] nums) {
        int length = nums.length;
        int ans = 0;
        for(int i = 0; i < length; i++){
            int left = findLeft(nums,i,nums[i],nums[i]+1);
            int right = findRight(nums,i,nums[i],nums[i]+1);
            ans = Math.max(right-left-1,ans);
            left = findLeft(nums,i,nums[i]-1,nums[i]);
            right = findRight(nums,i,nums[i]-1,nums[i]);
            ans = Math.max(right-left-1,ans);
        }
        return ans;
    }

    private int findLeft(int[] nums, int i, int min, int max){
        int left = i;
        while(left >= 0 && (nums[left] == min || nums[left] == max))
            left--;
         return left;   
    }

    private int findRight(int[] nums, int i, int min, int max){
        int right = i;
        while(right < nums.length && (nums[right] == min || nums[right] == max))
            right++;
        return right;
    }
}
// @lc code=end

