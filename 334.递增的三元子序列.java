/*
 * @lc app=leetcode.cn id=334 lang=java
 *
 * [334] 递增的三元子序列
 */

// @lc code=start
class Solution {
    public boolean increasingTriplet(int[] nums) {
        int i = 0, n = nums.length;
        Stack<Integer> stack = new Stack<>();
        while(i < n && stack.size() < 3){
            while(!stack.isEmpty() && stack.peek() >= nums[i])
                stack.pop();
            stack.push(nums[i]);
            i++;
        }
        return stack.size() == 3;
    }
}
// @lc code=end

