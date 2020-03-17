/*
 * @lc app=leetcode.cn id=739 lang=java
 *
 * [739] 每日温度
 */

// @lc code=start
class Solution {
    public int[] dailyTemperatures(int[] T) {
        int[] ans = new int[T.length];
        int length = T.length;
        ans[length-1] = 0;
        for(int i = length-2; i >= 0; i--){
            int j = i+1;
            while(T[i] >= T[j] && ans[j] != 0)
                j += ans[j];
            if(T[i] >= T[j]) ans[i] = 0;
            else ans[i] = j-i;
        }
        return ans;
    }
}
// @lc code=end

