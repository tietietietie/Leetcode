/*
 * @lc app=leetcode.cn id=415 lang=java
 *
 * [415] 字符串相加
 */

// @lc code=start
class Solution {
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for(int i = num1.length()-1, j = num2.length()-1; i >= 0 || j >= 0 || carry == 1; i--, j--){
            int sum = carry;
            if(i >= 0)
                sum += num1.charAt(i) - '0';
            if(j >= 0)
                sum += num2.charAt(j) - '0';
            sb.append(sum%10);
            carry = sum/10;
        }
        return sb.reverse().toString();
    }
}
// @lc code=end

