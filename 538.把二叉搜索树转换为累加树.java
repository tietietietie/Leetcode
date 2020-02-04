/*
 * @lc app=leetcode.cn id=538 lang=java
 *
 * [538] 把二叉搜索树转换为累加树
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
    private int temp;
    public TreeNode convertBST(TreeNode root) {
        temp = 0;
        inorder(root);
        return root;
    }

    private void inorder(TreeNode root){
        if(root == null) return ;
        inorder(root.right);
        temp += root.val;
        root.val = temp;
        inorder(root.left);
        return ;
    }
}
// @lc code=end

