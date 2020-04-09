/*
 * @lc app=leetcode.cn id=70 lang=java
 *
 * [70] 爬楼梯
 */

// @lc code=start
class Solution {
    public int climbStairs(int n) {
        if(n <= 0) return 0;
        if(n == 1 || n == 2) return n;
        int a = 1, b = 2;
        for(int i = 2; i <= n; i++){
            b = a+b;
            a = b-a;
        }
        return a;
    }
}
// @lc code=end

