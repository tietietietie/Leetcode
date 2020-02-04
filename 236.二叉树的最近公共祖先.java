/*
 * @lc app=leetcode.cn id=236 lang=java
 *
 * [236] 二叉树的最近公共祖先
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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return null;
        TreeNode left = lowestCommonAncestor(root.left,p,q);
        TreeNode right = lowestCommonAncestor(root.right,p,q);
        //如果左右子树都找到了节点，则此节点为ans
        if(left != null && right != null) return root;
        //如果此节点为p,q则返回p,q
        if(root == p || root == q) return root;
        //如果只有一个非null，则返回非null的值（可能为p,q,ans）
        if(right != null) return right;
        if(left != null) return left; 
        //只剩下left/right都是null的情况   
        return null;
    }
}
// @lc code=end

