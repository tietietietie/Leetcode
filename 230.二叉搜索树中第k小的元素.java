/*
 * @lc app=leetcode.cn id=230 lang=java
 *
 * [230] 二叉搜索树中第K小的元素
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
    private int count;
    private int ans;
    public int kthSmallest(TreeNode root, int k) {
        count = 0;
        inorder(root,k);
        return ans;
    }

    private void inorder(TreeNode root, int k){
        if(root == null) return;
        inorder(root.left,k);
        count++;
        if(count == k) ans = root.val;
        else inorder(root.right,k);
        return;
    }
}
// @lc code=end

