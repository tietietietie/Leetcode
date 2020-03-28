/*
 * @lc app=leetcode.cn id=128 lang=java
 *
 * [128] 最长连续序列
 */

// @lc code=start
class Solution {
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for(int num : nums)
            set.add(num);
        int ans= 0;
        for(int num : set){
            if(!set.contains(num-1)){
                int count = 0;
                int temp = num;
                while(set.contains(temp)){
                    count++;
                    temp++;
                }
                ans = Math.max(ans,count);
            }
        }
        return ans;
    }
}
// @lc code=end

