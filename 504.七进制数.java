/*
 * @lc app=leetcode.cn id=504 lang=java
 *
 * [504] 七进制数
 */

// @lc code=start
class Solution {
    public String convertToBase7(int num) {
        if(num == 0)
            return "0";
        StringBuilder sb = new StringBuilder();
        boolean isNegative = false;
        if(num < 0){
            isNegative = true;
            num = Math.abs(num);
        }
        while(num != 0){
            sb.append(num % 7);
            num /= 7;
        }
        //String ans = String.join("",chars);
        //return String.valueOf(chars);
        // int size = chars.size();
        // String ans = new String((char[])chars.toArray(new char[size]));
        String ans = sb.reverse().toString();
        return isNegative ? "-" + ans : ans;
    }
}
// @lc code=end

