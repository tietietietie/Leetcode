/*
 * @lc app=leetcode.cn id=337 lang=java
 *
 * [337] 打家劫舍 III
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
    public int rob(TreeNode root) {
        if(root == null) return 0;
        //val1表示打劫当前节点能获得的最大财富
        int val1 = root.val;
        if(root.left != null) val1 += (rob(root.left.left) + rob(root.left.right));
        if(root.right != null) val1 += (rob(root.right.left) + rob(root.right.right));
        //val2表示不打劫当前节点能获得的最大财富
        int val2 = rob(root.right) + rob(root.left);
        return Math.max(val1,val2);      
    }
}
// @lc code=end

