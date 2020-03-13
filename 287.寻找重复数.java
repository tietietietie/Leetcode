/*
 * @lc app=leetcode.cn id=287 lang=java
 *
 * [287] 寻找重复数
 */

// @lc code=start
class Solution {
    public int findDuplicate(int[] nums) {
        int l = 1, r = nums.length-1;
        while(l < r){
            int mid = (l+r)/2;
            int cnt = 0;
            for(int i : nums)
                if(i <= mid)
                    cnt++;
            if(cnt <= mid) l = mid+1;
            else r = mid;
        }
        return l;
    }
}
// @lc code=end

