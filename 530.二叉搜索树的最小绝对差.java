/*
 * @lc app=leetcode.cn id=530 lang=java
 *
 * [530] 二叉搜索树的最小绝对差
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
    private int ans,pre;
    public int getMinimumDifference(TreeNode root) {
        ans = Integer.MAX_VALUE;
        pre = -1;
        inorder(root);
        return ans;
    }
    private void inorder(TreeNode node){
        if(node == null) return;
        inorder(node.left);
        if(pre != -1) ans = Math.min(ans,node.val-pre);
        pre = node.val;
        inorder(node.right);
        return;
    }
}
// @lc code=end

