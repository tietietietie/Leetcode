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
        int min,mid,max,tar;
        for(int i = 0; i < nums.length-2; i++)
        {
            if(i != 0 && nums[i] == nums[i-1])
                continue;
            for(int j = i+1; j < nums.length-1; j++)
            {
                if(j != i+1 && nums[j] == nums[j-1])
                    continue;
                min = j+1; 
                max  = nums.length-1;
                tar = -(nums[i] + nums[j]);
                while(min <= max)
                {
                    mid = (min+max)/2;
                    if(nums[mid] == tar)
                    {
                        ans.add(Arrays.asList(nums[i],nums[j],nums[mid]));
                        break;
                    }
                    else if(nums[mid] < tar)
                        min = mid + 1;
                    else max = mid - 1;
                }
            }
        }
        return ans;
    }
}
// @lc code=end

