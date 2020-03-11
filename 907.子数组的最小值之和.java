/*
 * @lc app=leetcode.cn id=907 lang=java
 *
 * [907] 子数组的最小值之和
 */

// @lc code=start
class Solution {
    private int ans;
    public int sumSubarrayMins(int[] A) {
        int length = A.length;
        ans = 0;
        calculate(A,0,length-1);
        return ans;
    }

    private void calculate(int[] A, int l, int r){
        if(l > r) return;
        int min = l;
        for(int i = l; i <= r; i++)
            if(A[i] < A[min])
                min = i;
        ans = (ans + (min-l+1)*(r-min+1)*A[min])%1000000007;
        calculate(A,l,min-1);
        calculate(A,min+1,r);
    }
}
// @lc code=end

