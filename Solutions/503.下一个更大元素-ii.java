/*
 * @lc app=leetcode.cn id=503 lang=java
 *
 * [503] 下一个更大元素 II
 */

// @lc code=start
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int length = nums.length;
        Stack<Integer> stack = new Stack<>();
        int[] ans = new int[length];
        Arrays.fill(ans,-1);
        for(int i = 0; i < 2*length; i++){
            while(!stack.isEmpty() && nums[stack.peek()] < nums[i%length])
                ans[stack.pop()] = nums[i%length];
            if(i < length) stack.push(i);
        }
        return ans;
    }
}
// @lc code=end

