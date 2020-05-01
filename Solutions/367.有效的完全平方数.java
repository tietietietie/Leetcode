/*
 * @lc app=leetcode.cn id=367 lang=java
 *
 * [367] 有效的完全平方数
 */

// @lc code=start
class Solution {
    public boolean isPerfectSquare(int num) {
        int subSum = 1;
        while(num > 0){
            num -= subSum;
            subSum += 2;
        }
        return num == 0;
    }
}
// @lc code=end

