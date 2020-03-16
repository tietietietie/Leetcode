/*
 * @lc app=leetcode.cn id=697 lang=java
 *
 * [697] 数组的度
 */

// @lc code=start
class Solution {
    public int findShortestSubArray(int[] nums) {
        Tuple[] map = new Tuple[50000];
        int length = nums.length;
        for(int i = 0; i < length; i++){
            if(map[nums[i]] == null)
                map[nums[i]] = new Tuple(1,i,i);
            else{
                map[nums[i]].count += 1;
                map[nums[i]].min = Math.min(i,map[nums[i]].min);
                map[nums[i]].max = Math.max(i,map[nums[i]].max);
            }
            
        }

        int maxCount = 0;
        for(Tuple t : map)
            if(t != null && t.count > maxCount)
                maxCount = t.count;

        int ans = 50000;
        for(Tuple t : map)
            if(t != null && t.count == maxCount)
                ans = Math.min(t.max-t.min+1,ans);
            
        return ans;
    }

    private class Tuple{
        int count;
        int min;
        int max;
        public Tuple(int count, int min, int max){
            this.count = count;
            this.min = min;
            this.max = max;
        }
    }
}
// @lc code=end

