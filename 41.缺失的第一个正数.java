/*
 * @lc app=leetcode.cn id=41 lang=java
 *
 * [41] 缺失的第一个正数
 */

// @lc code=start
class Solution {
    public int firstMissingPositive(int[] nums) {
        //检查是否存在1
        int contains = 0, n = nums.length;
        for(int i = 0; i < n; i++){
            if(nums[i] == 1){
                contains++;
                break;
            }
        }
        if(contains == 0){
            return 1;
        }
        //考虑nums为[1]时，
        if(n == 1)
            return 2;
        
        //将非正数和大于n的数，变为1
        for(int i = 0; i < n; i++)
            if(nums[i] <= 0 || nums[i] > n){
                nums[i] = 1;
            }
        //构造检查器
        for(int i = 0; i < n; i++){
            int a = Math.abs(nums[i]);
            if(a == n)
                nums[0] = -Math.abs(nums[0]);
            else if(nums[a] > 0)
                nums[a] = -nums[a];
        }
        //寻找没有出现过的最小正数，即第一次出现正数时的索引
        for(int i = 1; i < n; i++){
            if(nums[i] > 0)
                return i;
        }

        if(nums[0] > 0)
            return n;
        
        return n+1;
    }
}
// @lc code=end

