/*
 * @lc app=leetcode.cn id=169 lang=java
 *
 * [169] 多数元素
 */

// @lc code=start
class Solution {
    public int majorityElement(int[] nums) {
        HashMap<Integer,Integer> map = new HashMap<>();
        int majority = nums[0];
        for(int i : nums){
            if(map.get(i) == null){
                map.put(i,1);
            }else{
                map.put(i,map.get(i)+1);
                if(map.get(i) > map.get(majority))
                    majority = i;
            }
            if(map.get(majority) > nums.length/2)
                return majority;
        }
        return -1;
    }
}
// @lc code=end

