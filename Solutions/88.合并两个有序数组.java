/*
 * @lc app=leetcode.cn id=88 lang=java
 *
 * [88] 合并两个有序数组
 */

// @lc code=start
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m-1, p2 = n-1, p3 = m+n-1;
        while(p1 >= 0 && p2 >= 0){
            if(nums2[p2] >= nums1[p1]){
                nums1[p3] = nums2[p2];
                p2--;
            }else{
                nums1[p3] = nums1[p1];
                p1--;
            }
            p3--;
        }
        while(p2 >= 0){
            nums1[p3] = nums2[p2];
            p2--;
            p3--;
        }
    }
}
// @lc code=end

