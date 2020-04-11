/*
 * @lc app=leetcode.cn id=300 lang=java
 *
 * [300] 最长上升子序列
 */

// @lc code=start
class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int length = 0;
        for(int i = 0; i < nums.length; i++){
            int l = 0, r = length;
            while(l < r){
                int mid = (l+r)/2;
                if(tails[mid] < nums[i]) l = mid+1;
                else r = mid;
            }
            tails[l] = nums[i];
            if(l == length) length++;
        }
        return length;
    }
}
// @lc code=end

