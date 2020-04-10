/*
 * @lc app=leetcode.cn id=279 lang=java
 *
 * [279] 完全平方数
 */

// @lc code=start
class Solution {
    private boolean isSquare(int n){
        int sq = (int)Math.sqrt(n);
        return n == sq*sq;
    }
    public int numSquares(int n) {
        int temp = n;
        while(temp%4 == 0)
            temp /= 4;
        if(temp%8 == 7)
            return 4;
        
        if(isSquare(n))
            return 1;
        
        for(int i = 1; i*i < n; i++)
            if(isSquare(n-i*i))
                return 2;
        
        return 3;
    }
}
// @lc code=end

