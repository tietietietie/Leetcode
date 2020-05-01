/*
 * @lc app=leetcode.cn id=628 lang=java
 *
 * [628] 三个数的最大乘积
 */

// @lc code=start
class Solution {
    public int maximumProduct(int[] nums) {
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        for(int num : nums){
            if(num >= max3){
                max1 = max2;
                max2 = max3;
                max3 = num;
            }else if(num >= max2){
                max1 = max2;
                max2 = num;
            }else if(num > max1){
                max1 = num;
            }
            if(num <= min1){
                min2 = min1;
                min1 = num;
            }else if(num < min2)
                min2 = num;
        }
        int sum1 = min1*min2*max3;
        int sum2 = max1*max2*max3;
        return sum1 > sum2 ? sum1 : sum2;
    }
}
// @lc code=end

