/*
 * @lc app=leetcode.cn id=687 lang=java
 *
 * [687] 最长同值路径
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
    //定义ans，在递归过程中实时更新
    private int ans;
    public int longestUnivaluePath(TreeNode root) {
        ans = 0;
        //从根节点开始找；
        longestArrowLength(root);
        return ans;
    }

    private int longestArrowLength(TreeNode root){
        if(root == null) return 0;
        //arrow表示以该节点为根节点，从左子树或右子树延申的最长同值路径（单侧最长同值路径）
        int left = longestArrowLength(root.left);
        int right = longestArrowLength(root.right);
        int leftArrow = 0, rightArrow = 0;
        if(root.left != null && root.left.val == root.val) leftArrow = left+1;
        if(root.right != null && root.right.val == root.val) rightArrow = right+1;
        ans = Math.max(ans,leftArrow+rightArrow);
        return Math.max(leftArrow,rightArrow);
    }
}
// @lc code=end

