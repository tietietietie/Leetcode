/*
 * @lc app=leetcode.cn id=667 lang=java
 *
 * [667] 优美的排列 II
 */

// @lc code=start
class Solution {
    public int[] constructArray(int n, int k) {
        int[] ans = new int[n];
        int l = 1, r = k+1 ,i = 0;
        boolean even = true;
        while(l <= r){
            if(even){
                ans[i] = l;
                l++;
                i++;
                even = false;;
            }else{
                ans[i] = r;
                r--;
                i++;
                even = true;
            }
        }
        while(i < n){
            ans[i] = i+1;
            i++;
        }
        return ans;
    }
}
// @lc code=end

