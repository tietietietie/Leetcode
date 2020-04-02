/*
 * @lc app=leetcode.cn id=367 lang=java
 *
 * [367] 有效的完全平方数
 */

// @lc code=start
class Solution {
    public boolean isPerfectSquare(int num) {
        if(num < 2) return true;
        int l = 0, r = num/2;
        while(l <= r){
            int mid = (l+r)/2;
            long guess_square = (long)mid*mid;
            if(guess_square > num)
                r = mid-1;
            else if(guess_square < num)
                l = mid+1;
            else
                return true;
        }
        return false;
    }
}
// @lc code=end

