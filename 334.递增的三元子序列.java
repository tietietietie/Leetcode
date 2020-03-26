/*
 * @lc app=leetcode.cn id=334 lang=java
 *
 * [334] 递增的三元子序列
 */

// @lc code=start
class Solution {
    public boolean increasingTriplet(int[] nums) {
        ArrayList<Integer> list = new ArrayList<>();
        int length = nums.length;
        if(length == 0) return false;
        list.add(nums[0]);
        for(int i = 1; i < length; i++){
            int j = 0;
            for(; j < list.size(); j++)
                if(list.get(j) >= nums[i])
                    break;
            if(j < list.size())
                list.set(j,nums[i]);
            else
                list.add(nums[i]);  //set
            if(list.size() == 3)
                return true;
        }
        return false;
    }
}
// @lc code=end

