/*
 * @lc app=leetcode.cn id=572 lang=java
 *
 * [572] 另一个树的子树
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
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(s == null) return false;
        return isSame(s,t) || isSubtree(s.left,t) || isSubtree(s.right,t);       
    }

    private boolean isSame(TreeNode n1, TreeNode n2){
        if(n1 == null && n2 == null) return true;
        if(n1 == null || n2 == null) return false;
        return n1.val == n2.val && isSame(n1.left,n2.left) && isSame(n1.right,n2.right);
    }
}
// @lc code=end

