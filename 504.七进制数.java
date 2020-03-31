/*
 * @lc app=leetcode.cn id=504 lang=java
 *
 * [504] 七进制数
 */

// @lc code=start
class Solution {
    public String convertToBase7(int n) {
        if(n < 0) return "-" + convertToBase7(-n);
        if(n < 7) return String.valueOf(n);
        return convertToBase7(n/7) + String.valueOf(n%7);
    }
}
// @lc code=end

