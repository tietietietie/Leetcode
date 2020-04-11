/*
 * @lc app=leetcode.cn id=646 lang=java
 *
 * [646] 最长数对链
 */

// @lc code=start
class Solution {
    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs,(o1,o2) -> o1[1] - o2[1]);
        int[] dp = new int[pairs.length];
        Arrays.fill(dp,1);
        int ans = 1;
        for(int i = 0; i < pairs.length; i++){
            for(int j = 0; j < i; j++){
                if(pairs[j][1] < pairs[i][0])
                    dp[i] = Math.max(dp[i],dp[j]+1);
                else
                    break;
            }
            ans = Math.max(dp[i],ans); 
        }
        return ans;
    }
}
// @lc code=end

