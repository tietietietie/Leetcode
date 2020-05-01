/*
 * @lc app=leetcode.cn id=739 lang=java
 *
 * [739] 每日温度
 */

// @lc code=start
class Solution {
    public int[] dailyTemperatures(int[] T) {
        int length = T.length;
        int[] ans = new int[length];
        Stack<Integer> stack = new Stack<>();
        for(int i = length-1; i >= 0; i--){
            int count = 0;
            while(!stack.isEmpty() && T[stack.peek()] <= T[i])
                stack.pop();
            ans[i] = stack.isEmpty()?0:stack.peek()-i;
            stack.push(i);
        }
        return ans;
    }
}
// @lc code=end

