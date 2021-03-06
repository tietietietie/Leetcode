/*
 * @lc app=leetcode.cn id=16 lang=java
 *
 * [16] 最接近的三数之和
 */

// @lc code=start
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int i,j,k,ans,temp;
        ans = nums[0] + nums[1] + nums[2];
        for(i = 0; i < nums.length; i++)
        {
            if(i != 0 && nums[i] == nums[i-1])
                continue;
            j = i+1;
            k = nums.length-1;
            while(j < k)
            {
                temp = nums[i]+nums[j]+nums[k];
                if(Math.abs(temp - target) < Math.abs(ans - target))
                    ans = temp;
                if(temp == target)
                    return target;
                else if(temp > target)
                {
                    k--;
                    while(j<k && nums[k] == nums[k+1])
                        k--;
                }    
                else
                {
                    j++;
                    while(j<k && nums[j] == nums[j-1])
                        j++;
                }
            }
        }
        return ans;
    }
}
// @lc code=end

