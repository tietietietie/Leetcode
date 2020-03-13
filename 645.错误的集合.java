/*
 * @lc app=leetcode.cn id=645 lang=java
 *
 * [645] 错误的集合
 */

// @lc code=start
class Solution {
    public int[] findErrorNums(int[] nums) {
        int length = nums.length;
        int[] map = new int[length+1];
        for(int i : nums)
            map[i]++;
        int[] ans = new int[2];
        for(int i = 1; i <= length; i++){
            if(map[i] == 2)
                ans[0] = i;
            else if(map[i] == 0)
                ans[1] = i;
        }
        return ans;
    }
}
// @lc code=end

