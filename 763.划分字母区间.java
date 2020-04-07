/*
 * @lc app=leetcode.cn id=763 lang=java
 *
 * [763] 划分字母区间
 */

// @lc code=start
class Solution {
    public List<Integer> partitionLabels(String S) {
        List<Integer> ans = new ArrayList<>();
        int[] ends = new int[26];
        for(int i = 0; i < S.length(); i++)
            ends[S.charAt(i)-'a'] = i;
        int curStart = 0, curEnd = 0;
        for(int i = 0; i < S.length(); i++){
            curEnd = Math.max(curEnd,ends[S.charAt(i)-'a']);
            if(i == curEnd){
                ans.add(curEnd-curStart+1);
                curStart = i+1;
            }
        }
        return ans;
    }
}
// @lc code=end

