/*
 * @lc app=leetcode.cn id=18 lang=java
 *
 * [18] 四数之和
 */

// @lc code=start
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        int i,j,left,right;
        List<List<Integer>> ans = new ArrayList();
        for(i = 0; i < nums.length-3; i++)
        {
            while(i != 0 && i < nums.length-3 && nums[i] == nums[i-1])
                i++;
            for(j = i+1; j < nums.length-2; j++)
            {
                while(j != i+1 && j < nums.length-2 && nums[j] == nums[j-1])
                    j++;
                left = j+1;
                right = nums.length-1;
                while(left < right)
                {
                    int sum = nums[i] + nums[j] + nums[right] + nums[left];
                    if(sum == target)
                    {
                        ans.add(Arrays.asList(nums[i],nums[j],nums[left],nums[right]));
                        left++;
                        while(left < right && nums[left] == nums[left-1])
                            left++;
                        right--;
                        while(left < right && nums[right] == nums[right+1])
                            right--;
                    }
                    else if(sum < target)
                    {
                        left++;
                        while(left < right && nums[left] == nums[left-1])
                            left++;
                    }
                    else
                    {
                        right--;
                        while(left < right && nums[right] == nums[right+1])
                            right--;
                    }
                }
            }
        }
        return ans;
    }
}
// @lc code=end

