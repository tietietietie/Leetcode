/*
 * @lc app=leetcode.cn id=504 lang=java
 *
 * [504] 七进制数
 */

// @lc code=start
class Solution {
    public String convertToBase7(int n) {
        if(n == 0) return "0";
        String ans = "";
        StringBuilder sb = new StringBuilder();
        if(n < 0){
            ans = "-";
            n = -n;
        }
        while(n != 0){
            sb.append(n%7);
            n /= 7;
        }
        ans += sb.reverse().toString();
        return ans;
    }
}
// @lc code=end

