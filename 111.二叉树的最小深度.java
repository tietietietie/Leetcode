/*
 * @lc app=leetcode.cn id=111 lang=java
 *
 * [111] 二叉树的最小深度
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
    public int minDepth(TreeNode root) {
        if(root == null) return 0;
        int d1 = minDepth(root.left);
        int d2 = minDepth(root.right); 
        if(d1 == 0 || d2 == 0) return d1+d2+1; 
        return Math.min(d1,d2)+1;
    }
}
// @lc code=end

