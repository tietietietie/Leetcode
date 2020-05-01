/*
 * @lc app=leetcode.cn id=15 lang=java
 *
 * [15] 三数之和
 */

// @lc code=start
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList();
        int left, right;
        for(int i = 0; i < nums.length-2 && nums[i] <= 0; i++)
        {
            if(i != 0 && nums[i] == nums[i-1])
                continue;
            left = i+1;
            right = nums.length-1;
            //int tar = -nums[i];
            while(left < right)
            {
                if(nums[left] + nums[right] + nums[i] == 0)
                {
                    ans.add(Arrays.asList(nums[i],nums[left],nums[right]));
                    do{left++;}while(left<right && nums[left] == nums[left-1]);
                    do{right--;}while(left<right && nums[right] == nums[right+1]);
                }
                else if(nums[left] + nums[right] + nums[i] < 0)
                   left++;
                else
                    right--;    
            }
        }
        return ans;
    }
}
// @lc code=end

