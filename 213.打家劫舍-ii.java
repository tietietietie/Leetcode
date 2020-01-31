/*
 * @lc app=leetcode.cn id=213 lang=java
 *
 * [213] 打家劫舍 II
 */

// @lc code=start
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if(n == 0) return 0;
        if(n == 1) return nums[0];
        
        //情况1：初始节点不被打劫
        int[] temp =  new int[]{0,0};
        for(int k = 1; k < n; k++){
            int rob = nums[k] + temp[0];
            int not_rob = Math.max(temp[0],temp[1]);
            temp[0] = not_rob;
            temp[1] = rob;
        }
        int ans1 = Math.max(temp[0],temp[1]);
        //情况2：初始节点被打劫，则第二个节点不能被打劫，第三个节点随意
        temp[0] = nums[0];
        temp[1] = nums[0];
        for(int k = 2; k < n; k++){
            int rob = nums[k] + temp[0];
            int not_rob = Math.max(temp[0],temp[1]);
            temp[0] = not_rob;
            temp[1] = rob;
        }
        int ans2 = temp[0];
        return Math.max(ans1,ans2);    
    }
}
// @lc code=end

