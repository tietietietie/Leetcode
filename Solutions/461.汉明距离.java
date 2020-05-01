/*
 * @lc app=leetcode.cn id=461 lang=java
 *
 * [461] 汉明距离
 */

// @lc code=start
class Solution {
    public int hammingDistance(int x, int y) {
        int xor = x ^ y;
        int cmp = 1;
        int count = 0;
        while(cmp != 0){
            if((xor & cmp) != 0)
                count++;
            cmp = cmp << 1;
        }
        return count;
    }
}
// @lc code=end

