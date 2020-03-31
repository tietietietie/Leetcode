/*
 * @lc app=leetcode.cn id=405 lang=java
 *
 * [405] 数字转换为十六进制数
 */

// @lc code=start
class Solution {
    public String toHex(int num) {
        StringBuilder sb = new StringBuilder();
        if(num == 0) return "0";
        int cmp = 15;
        while(num != 0){
            int digit = num & cmp;
            num >>>= 4;
            if(digit < 10)
                sb.append(digit);
            else
                sb.append((char)(digit-10+'a'));
        }
        return sb.reverse().toString();
    }
}
// @lc code=end

