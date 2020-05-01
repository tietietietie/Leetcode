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
            // if(i != 0 && nums[i] == nums[i-1])
            //     continue;
            if (i == 0 || (i > 0 && nums[i] != nums[i-1]))
            {
                left = i+1;
                right = nums.length-1;
                while(left < right)
                {
                //  if(left != i+1 && nums[left-1] == nums[left])
                //     {
                //         left++;
                //         continue;
                //     }
                //     if(right != nums.length-1 && nums[right+1] == nums[right])
                //     {
                //         right--;
                //         continue;
                //     }   
                    if(nums[left] + nums[right] + nums[i] == 0)
                    {
                        ans.add(Arrays.asList(nums[i],nums[left],nums[right]));
                        do{left++;}while(left<right && nums[left] == nums[left-1]);
                        do{right--;}while(left<right && nums[right+1] == nums[right]);
                    }
                    else if(nums[left] + nums[right] + nums[i] < 0)   
                        left++;
                    else
                        right--;    //为什么？？？
                }
            }
        }
        return ans;
    }
}
// @lc code=end

