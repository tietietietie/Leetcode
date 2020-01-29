/*
 * @lc app=leetcode.cn id=404 lang=java
 *
 * [404] 左叶子之和
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
    private int ans;
    public int sumOfLeftLeaves(TreeNode root) {
        ans = 0;
        //根节点不是左叶子节点
        addLeftLeaves(root,false);
        return ans;
    }

    private void addLeftLeaves(TreeNode n, boolean isLeft){
        if(n == null) return;
        if(isLeft && n.left == null && n.right == null) ans += n.val;
        addLeftLeaves(n.left,true);
        addLeftLeaves(n.right,false);
    }
}
// @lc code=end

