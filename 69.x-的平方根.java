/*
 * @lc app=leetcode.cn id=69 lang=java
 *
 * [69] x 的平方根
 */

// @lc code=start
class Solution {
    public int mySqrt(int x) {
        if(x == 0) return 0;
        if(x < 4) return 1;
        int l = 2, r = x/2;
        while(l <= r){
            int mid = (l+r)/2;
            long sqrt = (long)mid*mid;
            if(sqrt == x) return mid;
            else if(sqrt > x) r = mid-1;
            else l = mid+1;
        }
        return r;
    }
}
// @lc code=end

