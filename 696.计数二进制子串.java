/*
 * @lc app=leetcode.cn id=696 lang=java
 *
 * [696] 计数二进制子串
 */

// @lc code=start
class Solution {
    public int countBinarySubstrings(String s) {
        int count = 0;
        char[] chars = s.toCharArray();
        int length = chars.length;
        for(int i = 0; i < length; i++){
            int count0 = 0;
            int count1 = 0;
            int j = i;
            while(j < length && chars[j] == chars[i]){
                count0++;
                j++;
            }
                
            while(j < length && chars[j] != chars[i]){
                j++;
                count1++;
                if(count1 == count0){
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}
// @lc code=end

