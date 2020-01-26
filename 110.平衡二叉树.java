/*
 * @lc app=leetcode.cn id=110 lang=java
 *
 * [110] 平衡二叉树
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
    private Map<TreeNode,Integer> map = new HashMap<>();
    public boolean isBalanced(TreeNode root) {
        maxDepth(root);
        return recursivelyCheck(root);        
    }
    private int maxDepth(TreeNode root){
        if(root == null){
            map.put(root,0);
            return 0;
        }
        int depth = Math.max(maxDepth(root.left),maxDepth(root.right)) + 1;
        map.put(root,depth);
        return depth;            
    }
    private boolean recursivelyCheck(TreeNode root){
        if(root == null) return true;
        return Math.abs(map.get(root.left)-map.get(root.right)) < 2 
        && recursivelyCheck(root.left) && recursivelyCheck(root.right);
    }
}
// @lc code=end

