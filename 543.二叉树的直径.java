/*
 * @lc app=leetcode.cn id=543 lang=java
 *
 * [543] 二叉树的直径
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
    public int diameterOfBinaryTree(TreeNode root) {
        if(root == null)
            return 0;
        int d1 = depth(root.right) + depth(root.left);
        int d2 = Math.max(diameterOfBinaryTree(root.left),diameterOfBinaryTree(root.right));
        return Math.max(d1,d2);
    }
    private int depth(TreeNode root){
        if(root == null)
            return 0;
        return Math.max(depth(root.left),depth(root.right))+1;
    }
}
// @lc code=end

