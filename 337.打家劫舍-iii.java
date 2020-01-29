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
        //results里面保存着打劫root节点，和不打劫root节点两种选择，
        //能在root子树中得到的财产
        int[] results = robOptions(root);
        return Math.max(results[0],results[1]);     
    }

    private int[] robOptions(TreeNode root){
        if(root == null) return new int[2];
        int[] left = robOptions(root.left);
        int[] right = robOptions(root.right);
        //当前节点选择rob,则其能打劫到的财产为本节点财产加上
        //左子节点不被打劫时的左子树的最大财产，和右子节点不被打劫
        //时右子树的最大财产
        int rob = root.val + left[0] + right[0];
        //当前节点不被打劫，则返回左右子树能打劫的最大财产和
        int not_rob = Math.max(left[0],left[1]) + Math.max(right[0],right[1]);
        return new int[]{not_rob,rob};
    }
}
// @lc code=end

