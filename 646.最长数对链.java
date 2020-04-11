/*
 * @lc app=leetcode.cn id=646 lang=java
 *
 * [646] 最长数对链
 */

// @lc code=start
class Solution {
    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs,(o1,o2) -> o1[1] - o2[1]);
        int ans = 1, curEnd = pairs[0][1];
        for(int i = 1; i < pairs.length; i++){
            if(pairs[i][0] > curEnd){
                curEnd = pairs[i][1];
                ans++;
            }
        }
        return ans;
    }
}
// @lc code=end

