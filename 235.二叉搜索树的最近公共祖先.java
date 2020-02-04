/*
 * @lc app=leetcode.cn id=235 lang=java
 *
 * [235] 二叉搜索树的最近公共祖先
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
        while(q1.peek() == q2.peek()){
            ans = q1.peek();
            q1.poll();
            q2.poll();                 
        }
        return ans;     
    }

    private void ancestors(TreeNode root, TreeNode node, Queue<TreeNode> q){
        q.offer(root);
        if(root.val == node.val) 
            return;
        else if(root.val < node.val)
            ancestors(root.right,node,q);
        else
            ancestors(root.left,node,q);
        return;
    }
}
// @lc code=end

