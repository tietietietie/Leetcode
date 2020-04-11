/*
 * @lc app=leetcode.cn id=376 lang=java
 *
 * [376] 摆动序列
 */

// @lc code=start
class Solution {
    public int wiggleMaxLength(int[] nums) {
        if(nums.length < 2) return nums.length;
        int ans = 1;
        int pre1 = nums[0];
        int i = 1;
        for(;i < nums.length; i++)
            if(nums[i] != pre1)
                break;
        if(i == nums.length) return ans;
        int pre2 = nums[i];
        ans++;
        for(int j = i+1; j < nums.length; j++){
            if(nums[j] == pre2)
                continue;
            else if(nums[j] > pre2 && pre1 > pre2){
                ans++;
                pre1 = pre2;
                pre2 = nums[j];
            }else if(nums[j] < pre2 && pre1 > pre2){
                pre2 = nums[j];
            }else if(nums[j] < pre2 && pre1 < pre2){
                ans++;
                pre1 = pre2;
                pre2 = nums[j];
            }else
                pre2 = nums[j];
        }
        return ans;
    }
}
// @lc code=end

