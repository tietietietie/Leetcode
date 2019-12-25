import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Solution
{
    // public int maxArea(int[] height) {
    //     int n = height.length;
    //     int left = 0, right = n-1, maxarea = 0, area = 0;
    //     while(left != right)
    //     {
    //         area = (right-left)*Math.min(height[right],height[left]);
    //         maxarea = Math.max(area,maxarea);
    //         if(height[left] < height[right])
    //         {
    //             while(height[left+1] < height[left])
    //                 left++;
    //             left++;
    //         }
    //         else
    //         {
    //             while(height[right-1] <= height[right])
    //                 right--;
    //             right--;
    //         }
    //     }
    //     return maxarea;
    // }
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        int i,j,left,right;
        List<List<Integer>> ans = new ArrayList();
        for(i = 0; i < nums.length-1; i++)
        {
            while(i != 0 && i < nums.length-1 && nums[i] == nums[i-1])
                i++;
            for(j = i+1; j < nums.length; j++)
            {
                while(j != i+1 && j < nums.length &&  nums[j] == nums[j-1])
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
                        while(left < right && nums[right] == nums[left+1])
                            right--;
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] a = {-7,-5,0,7,1,1,-10,-2,7,7,-2,-6,0,-10,-5,7,-8,5};
        Solution s = new Solution();
        System.out.println(s.fourSum(a,0));
    }
}