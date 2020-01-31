/*
 * @lc app=leetcode.cn id=198 lang=java
 *
 * [198] 打家劫舍
 */

// @lc code=start
class Solution {
    public int rob(int[] nums) {
        //n表示户数
        int n = nums.length;
        if(n == 0) return 0;
        if(n == 1) return nums[0];
        //temp存储上一节点偷了或没偷，能获得的最大财产
        int[] temp = new int[]{0,nums[0]};
        //迭代遍历到第n个节点
        for(int k = 1; k < n; k++){
            int rob = nums[k] + temp[0];
            int not_rob = Math.max(temp[0],temp[1]);
            temp[0] = not_rob;
            temp[1] = rob;
        }
        return Math.max(temp[0],temp[1]);
    }
}
// @lc code=end

