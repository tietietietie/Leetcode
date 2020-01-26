/*
 * @lc app=leetcode.cn id=104 lang=java
 *
 * [104] 二叉树的最大深度
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
    private int depth = 0;
    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        else{
            depth = Math.max(maxDepth(root.left),maxDepth(root.right)) + 1;
        }
        return depth;
    }
}
// @lc code=end

