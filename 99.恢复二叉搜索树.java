/*
 * @lc app=leetcode.cn id=99 lang=java
 *
 * [99] 恢复二叉搜索树
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
    private TreeNode pre, s, t;

    public void recoverTree(TreeNode root) {
        pre = null;
        s = null;
        t = null;
        inorder(root);
        int temp = s.val;
        s.val = t.val;
        t.val = temp;
    }

    private void inorder(TreeNode node){
        if(node == null) return;
        inorder(node.left);
        if(pre == null) pre = node;
        else if(pre.val > node.val){
            s = s == null ? pre : s;
            t = node;
        }
        pre = node;
        inorder(node.right);
    }
}
// @lc code=end

