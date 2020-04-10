/*
 * @lc app=leetcode.cn id=413 lang=java
 *
 * [413] 等差数列划分
 */

// @lc code=start
class Solution {
    public int numberOfArithmeticSlices(int[] A) {
        int n = A.length;
        if(n < 3) return 0;
        int[] dp = new int[n];
        int sum = 0;
        for(int i = 2; i < n; i++)
            if(A[i]-A[i-1] == A[i-1]-A[i-2]){
                dp[i] = dp[i-1]+1;
                sum += dp[i];
            }
        return sum;
    }
}
// @lc code=end

