/*
 * @lc app=leetcode.cn id=128 lang=java
 *
 * [128] 最长连续序列
 */

// @lc code=start
class Solution {
    public int longestConsecutive(int[] nums) {
        HashMap<Integer,Boolean> map = new HashMap<>();
        for(int num : nums)
            if(map.get(num) == null)
                map.put(num,true);
        int ans = 0;
        for(int num : map.keySet()){
            if(map.get(num)){
                int left = num-1, right = num+1;
                while(map.get(left) != null){
                    map.put(left,false);
                    left--;
                }
                while(map.get(right) != null){
                    map.put(right,false);
                    right++;
                }
                ans = Math.max(right-left-1,ans);
            }
        }
        return ans;
    }
}
// @lc code=end

