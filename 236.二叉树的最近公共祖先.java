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
        LinkedList<TreeNode> q1 = new LinkedList<>();
        LinkedList<TreeNode> q2 = new LinkedList<>();
        ancestors(root,p,q1);
        ancestors(root,q,q2);
        TreeNode ans = null;
        //确定最深公共节点
        while(q1.peek() == q2.peek()){
            ans = q1.peek();
            q1.poll();
            q2.poll();                 
        }
        return ans;         
    }
    private boolean ancestors(TreeNode root, TreeNode node, LinkedList<TreeNode> q){
        if(root == null) return false;
        q.offer(root);
        if(root == node) return true;
        boolean exist = false;
        if(root.left != null) exist = ancestors(root.left,node,q);
        if(!exist && root.right != null) exist = ancestors(root.right,node,q);
        if(!exist) q.pollLast();
        return exist;
    }
}
// @lc code=end

