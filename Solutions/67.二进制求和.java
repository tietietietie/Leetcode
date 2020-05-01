/*
 * @lc app=leetcode.cn id=67 lang=java
 *
 * [67] 二进制求和
 */

// @lc code=start
class Solution {
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for(int i = a.length()-1, j = b.length()-1; i >= 0 || j >= 0 || carry == 1; i--, j--){
            int sum = carry;
            if(i >= 0)
                sum += a.charAt(i) - '0';
            if(j >= 0)
                sum += b.charAt(j) - '0';
            sb.append(sum%2);
            carry = sum/2;
        }
        return sb.reverse().toString();
    }
}
// @lc code=end

