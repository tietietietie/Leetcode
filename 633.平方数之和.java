/*
 * @lc app=leetcode.cn id=633 lang=java
 *
 * [633] 平方数之和
 */

// @lc code=start
class Solution {
    public boolean judgeSquareSum(int c) {
        int max = (int)Math.sqrt(c) + 1;
        int left = 0, right = max;
        while(left <= right){
            int sum = left*left + right*right;
            if(sum == c)
                return true;
            else if(sum < c)
                left++;
            else
                right--;
        }
        return false;
    }
}
// @lc code=end

