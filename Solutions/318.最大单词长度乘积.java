/*
 * @lc app=leetcode.cn id=318 lang=java
 *
 * [318] 最大单词长度乘积
 */

// @lc code=start
class Solution {
    public int maxProduct(String[] words) {
        int length = words.length;
        int[] val = new int[length];
        for(int i = 0; i < length; i++)
            for(char c : words[i].toCharArray()){
                val[i] |= 1 << (c-'a');
            }
        int ans = 0;
        for(int i = 0; i < length; i++)
            for(int j = i+1; j < length; j++){
                if((val[i]&val[j]) == 0){
                    ans = Math.max(ans,words[i].length() * words[j].length());
                }
            }
        return ans;
    }
}
// @lc code=end

