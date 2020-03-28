/*
 * @lc app=leetcode.cn id=594 lang=java
 *
 * [594] 最长和谐子序列
 */

// @lc code=start
class Solution {
    public int findLHS(int[] nums) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int num : nums){
            if(map.get(num) == null)
                map.put(num,1);
            else
                map.put(num,map.get(num)+1);
        }
        int ans = 0;
        for(int num : map.keySet())
            if(map.get(num+1) != null)
                ans = Math.max(ans,map.get(num)+map.get(num+1));
        return ans;
    }
}
// @lc code=end

