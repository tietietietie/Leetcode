/*
 * @lc app=leetcode.cn id=95 lang=java
 *
 * [95] 不同的二叉搜索树 II
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
    public List<TreeNode> generateTrees(int n) {
        if(n <= 0) return new ArrayList<TreeNode>();
        return generateTrees(1,n);
    }

    private List<TreeNode> generateTrees(int l, int r){
        List<TreeNode> trees = new ArrayList<>();
        if(l > r){
            trees.add(null);
            return trees;
        }
        for(int i = l; i <= r; i++){
            List<TreeNode> leftTrees = generateTrees(l,i-1);
            List<TreeNode> rightTrees = generateTrees(i+1,r);
            for(TreeNode leftTree : leftTrees)
                for(TreeNode rightTree : rightTrees){
                    TreeNode root = new TreeNode(i);
                    root.left = leftTree;
                    root.right = rightTree;
                    trees.add(root);
                }
        }
        return trees;
    }
}
// @lc code=end

