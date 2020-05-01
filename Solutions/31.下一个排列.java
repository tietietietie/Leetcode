/*
 * @lc app=leetcode.cn id=31 lang=java
 *
 * [31] 下一个排列
 */

// @lc code=start
class Solution {
    public void nextPermutation(int[] nums) {
        int tar = -1;
        for(int i = 0; i < nums.length-1; i++)
        {
            if(nums[i+1] > nums[i])
                tar = i;
        }
        if(tar == -1)
            Arrays.sort(nums);
        else
        {
            for(int j = tar+1; j < nums.length; j++)
            {
                if(j+1 >= nums.length || nums[j+1] <= nums[tar])
                {
                    swap(nums,tar,j);
                    reverse(nums,tar+1);
                    break;
                }
            }
        }
    }

    private void swap(int[] nums, int i, int j)
    {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int i)
    {
        int j = nums.length-1;
        while(i < j)
        {
            swap(nums,i,j);
            i++;
            j--;
        }
    }
}
// @lc code=end

