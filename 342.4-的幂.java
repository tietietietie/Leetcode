/*
 * @lc app=leetcode.cn id=342 lang=java
 *
 * [342] 4的幂
 */

// @lc code=start
class Solution {
    public boolean isPowerOfFour(int num) {
        if(num <= 0) return false;
        int temp = num & (-num);
        if(temp != num) return false;
        int count = 0;
        while(num != 1){
            count++;
            num = (num >> 1);
        }
        return count%2 == 0;
    }
}
// @lc code=end

