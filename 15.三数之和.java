/*
 * @lc app=leetcode.cn id=15 lang=java
 *
 * [15] 三数之和
 */

// @lc code=start
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);  //不需要多余的a
        //int[][] ans = new int[][];
        List<List<Integer>> ans = new ArrayList();
        for(int i = 0; i < nums.length-2; i++)
        {
            //while(i != 0 && nums[i] == nums[i-1] && )
            //    i++;
            if(i != 0 && nums[i] == nums[i-1])
                continue;
            for(int j = i+1; j < nums.length-1; j++)
            {
                //while(j != i+1 && nums[j] == nums[j-1])
                //    j++;
                if(j != i+1 && nums[j] == nums[j-1])
                    continue;
                for(int k = j+1; k < nums.length; k++)
                {
                    if(nums[i] + nums[j] + nums[k] == 0)
                    {
                        ans.add(Arrays.asList(nums[i],nums[j],nums[k]));
                        break;
                    }
                }
            }
        }
        return ans;
    }
}
// @lc code=end

