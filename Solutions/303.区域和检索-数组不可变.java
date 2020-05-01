/*
 * @lc app=leetcode.cn id=303 lang=java
 *
 * [303] 区域和检索 - 数组不可变
 */

// @lc code=start
class NumArray {
    public int[] preSum;

    public NumArray(int[] nums) {
        preSum = new int[nums.length+1];
        for(int i = 0; i < nums.length; i++)
            preSum[i+1] = preSum[i]+nums[i]; 
    }
    
    public int sumRange(int i, int j) {
        return preSum[j+1]-preSum[i];
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */
// @lc code=end

