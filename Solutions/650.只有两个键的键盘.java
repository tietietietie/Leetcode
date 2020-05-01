/*
 * @lc app=leetcode.cn id=650 lang=java
 *
 * [650] 只有两个键的键盘
 */

// @lc code=start
class Solution {
    public int minSteps(int n) {
        if(n == 1) return 0;
        int ans = 0, i = 2;
        while(n != 1){
            while(n%i == 0){
                ans += i;
                n /= i;
            }
            i++;
        }
        return ans;
    }
}
// @lc code=end

