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
        dp[2] = numberOfArithmeticSlices(A,2);
        for(int i = 3; i < n; i++)
            dp[i] = dp[i-1] + numberOfArithmeticSlices(A,i);
        return dp[n-1];
    }

    private int numberOfArithmeticSlices(int[] A, int r){
        int length = 0, diff = A[r] - A[r-1];
        while(r >= 1 && A[r]-A[r-1] == diff){
            length++;
            r--;
        }
        return Math.max(0,length-1);
    }
}
// @lc code=end

