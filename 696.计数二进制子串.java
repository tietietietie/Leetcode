/*
 * @lc app=leetcode.cn id=696 lang=java
 *
 * [696] 计数二进制子串
 */

// @lc code=start
class Solution {
    public int countBinarySubstrings(String s) {
        char[] chars = s.toCharArray();
        int length = chars.length;
        int[] group = new int[length];
        int ans = 0;
        group[0] = 1;
        int t = 0;
        for(int i = 1; i < length; i++){
            if(chars[i] == chars[i-1])
                group[t]++;
            else{
                t++;
                group[t] = 1;
            }
        }
        for(int i = 1; i <= t; i++){
            ans += Math.min(group[i],group[i-1]);
        }
        return ans;
    }
}
// @lc code=end

