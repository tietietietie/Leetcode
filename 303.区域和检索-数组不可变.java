/*
 * @lc app=leetcode.cn id=303 lang=java
 *
 * [303] 区域和检索 - 数组不可变
 */

// @lc code=start
class NumArray {
    public int[] preSum;

    public NumArray(int[] nums) {
        preSum = new int[nums.length];
        if(nums.length == 0) return;
        preSum[0] = nums[0];
        for(int i = 1; i < nums.length; i++)
            preSum[i] = preSum[i-1]+nums[i]; 
    }
    
    public int sumRange(int i, int j) {
        if(i == 0) return preSum[j];
        return preSum[j]-preSum[i-1];
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */
// @lc code=end

