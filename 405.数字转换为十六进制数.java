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
        char[] map = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        while(num != 0){
            int digit = num & cmp;
            num >>>= 4;
            sb.append(map[digit]);
        }
        return sb.reverse().toString();
    }
}
// @lc code=end

