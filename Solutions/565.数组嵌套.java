/*
 * @lc app=leetcode.cn id=565 lang=java
 *
 * [565] 数组嵌套
 */

// @lc code=start
class Solution {
    public int arrayNesting(int[] nums) {
        int length = nums.length;
        boolean[] visited = new boolean[length];
        int ans = 0;
        for(int i = 0; i < length; i++){
            if(!visited[i]){
                visited[i] = true;
                int cur = nums[i];
                int count = 1;
                while(cur != i){
                    visited[cur] = true;
                    cur = nums[cur];
                    count++;
                }
                ans = Math.max(count,ans);
            }
        }
        return ans;
    }
}
// @lc code=end

