/*
 * @lc app=leetcode.cn id=907 lang=java
 *
 * [907] 子数组的最小值之和
 */

// @lc code=start
class Solution {
    public int sumSubarrayMins(int[] A) {
        int length = A.length;
        Stack<Integer> prefix = new Stack<>();
        Stack<Integer> suffix = new Stack<>();
        int[] left = new int[length];
        int[] right = new int[length];
        int ans = 0;
        for(int i = 0; i < length; i++){
            while(!prefix.isEmpty() && A[prefix.peek()] > A[i])
                prefix.pop();
            left[i] = prefix.isEmpty()?-1:prefix.peek();
            prefix.push(i);
        }
        for(int i = length-1; i >= 0; i--){
            while(!suffix.isEmpty() && A[suffix.peek()] >= A[i])
                suffix.pop();
            right[i] = suffix.isEmpty()?length:suffix.peek();
            suffix.push(i);
        }
        for(int i = 0; i < length; i++)
            ans = (ans + (i-left[i])*(right[i]-i)*A[i])%1000000007;
        return ans;
    }
}
// @lc code=end

