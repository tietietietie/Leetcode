/*
 * @lc app=leetcode.cn id=300 lang=java
 *
 * [300] 最长上升子序列
 */

// @lc code=start
class Solution {
    public int lengthOfLIS(int[] nums) {
        if(nums.length == 0) return 0;
        ArrayList<Integer> preMins = new ArrayList<>();
        int ans = 1;
        for(int i = 0; i < nums.length; i++){
            if(preMins.isEmpty())
                preMins.add(nums[i]);
            else{
                int j = 0;
                for(; j < preMins.size(); j++)
                    if(nums[i] <= preMins.get(j)){
                        preMins.set(j,nums[i]);
                        break;
                    }
                if(j == preMins.size())
                    preMins.add(nums[i]);
                ans = Math.max(j+1,ans);
            }
        }
        return ans;
    }
}
// @lc code=end

