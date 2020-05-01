/*
 * @lc app=leetcode.cn id=124 lang=java
 *
 * [124] 二叉树中的最大路径和
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
    public int maxPathSum(TreeNode root) {
        ans = root.val;
        maxSidePathSum(root);
        return ans;        
    }

    private int maxSidePathSum(TreeNode root){
        int left = root.val, right = root.val;
        if(root.left != null){
            int lsum = maxSidePathSum(root.left);
            if(lsum > 0)
                left += lsum;
        }
        if(root.right != null){
            int rsum = maxSidePathSum(root.right);
            if(rsum > 0)
                right += rsum;
        }
        ans = Math.max(ans,right+left-root.val);
        return Math.max(left,right);
    }
}
// @lc code=end

