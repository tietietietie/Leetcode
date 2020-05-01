/*
 * @lc app=leetcode.cn id=108 lang=java
 *
 * [108] 将有序数组转换为二叉搜索树
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
    int len = nums.length;
    return constructBST(nums,0,len-1);        
    }

    private TreeNode constructBST(int[] nums, int left, int right){
        if(left > right) return null;
        int i = (left+right)/2;
        TreeNode node = new TreeNode(nums[i]);
        node.left = constructBST(nums,left,i-1);
        node.right = constructBST(nums,i+1,right);
        return node;
    }
}
// @lc code=end

