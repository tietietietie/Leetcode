/*
 * @lc app=leetcode.cn id=338 lang=java
 *
 * [338] 比特位计数
 */

// @lc code=start
class Solution {
    public int[] countBits(int num) {
        int[] ans = new int[num+1];
        for(int i = 1; i <= num; i++)
            ans[i] = ans[i & (i-1)] + 1;
        return ans;
    }
}
// @lc code=end

