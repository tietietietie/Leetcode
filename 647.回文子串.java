/*
 * @lc app=leetcode.cn id=647 lang=java
 *
 * [647] 回文子串
 */

// @lc code=start
class Solution {
    public int countSubstrings(String s) {
        int length = s.length();
        if(length == 0) return 0;
        int ans = 0;
        for(int center = 0; center <= 2*length-2; center++){
            int left = center/2;
            int right = left + center%2;
            while(left >= 0 && right < length && s.charAt(left) == s.charAt(right)){
                ans++;
                left--;
                right++;
            }
        }
        return ans;
    }
}
// @lc code=end

