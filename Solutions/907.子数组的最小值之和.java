/*
 * @lc app=leetcode.cn id=907 lang=java
 *
 * [907] 子数组的最小值之和
 */

// @lc code=start
class Solution {
    public int sumSubarrayMins(int[] A) {
        int length = A.length;
        int[] left = new int[length];
        int[] right = new int[length];
        left[0] = -1;
        right[length-1] = length;
        for(int i = 0; i < length; i++){
            int l = i-1;
            while(l >=0 && A[l] > A[i])
                l = left[l];
            left[i] = l;
        }
        for(int i = length-1; i >= 0; i--){
            int r = i+1;
            while(r < length && A[r] >= A[i])
                r = right[r];
            right[i] = r;
        }
        int ans = 0;
        int mod = 1000000007;
        for(int i = 0; i < length; i++)
            ans = (ans + (i-left[i])*(right[i]-i)*A[i]) % mod;
        return ans;
    }
}
// @lc code=end

