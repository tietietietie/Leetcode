/*
 * @lc app=leetcode.cn id=75 lang=java
 *
 * [75] 颜色分类
 */

// @lc code=start
class Solution {
    public void sortColors(int[] nums) {
        int l = 0, cur = 0, r = nums.length-1;
        while(cur <= r){
            if(nums[cur] == 1)
                cur++;
            else if(nums[cur] == 0){
                swap(nums,cur,l);
                cur++;
                l++;
            }else{
                swap(nums,cur,r);
                r--;
            }
        }
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
// @lc code=end

