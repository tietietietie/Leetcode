/*
 * @lc app=leetcode.cn id=769 lang=java
 *
 * [769] 最多能完成排序的块
 */

// @lc code=start
class Solution {
    public int maxChunksToSorted(int[] arr) {
        int max = 0, ans = 0;
        for(int i = 0; i < arr.length; i++){
            max = Math.max(max,arr[i]);
            if(max == i) ans++;
        }
        return ans;
    }      
}
// @lc code=end

