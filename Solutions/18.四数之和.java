/*
 * @lc app=leetcode.cn id=18 lang=java
 *
 * [18] 四数之和
 */

// @lc code=start
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        int i,j,left,right,sum;
        List<List<Integer>> ans = new ArrayList();
        for(i = 0; i < nums.length-3; i++)
        {
            if(i == 0 || nums[i] != nums[i-1])
            {
                for(j = i+1; j < nums.length-2; j++)
                {
                    if(j == i+1 || nums[j] != nums[j-1])
                    {
                        left = j+1;
                        right = nums.length-1;
                        sum = target - nums[j] - nums[i];
                        while(left < right)
                        {
                            if(nums[left] + nums[right] == sum)
                            {
                                ans.add(Arrays.asList(nums[i],nums[j],nums[left],nums[right]));
                                left++;
                                while(left < right && nums[left] == nums[left-1])
                                    left++;
                                right--;
                                while(left < right && nums[right] == nums[right+1])
                                    right--;
                            }
                            else if(nums[left] + nums[right] < sum)
                                left++;
                            else
                                right--;
                        }
                    } 
                }
            } 
        }
        return ans;            
    }
}

// @lc code=end

