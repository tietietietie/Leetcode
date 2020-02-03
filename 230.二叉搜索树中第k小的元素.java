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
    public int kthSmallest(TreeNode root, int k) {
        if(root == null) return -1;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode cur = root;
        while(cur != null || !stack.isEmpty()){
            while(cur.left != null){
                stack.push(cur.left);
                cur = cur.left;
            }
            cur = stack.pop();
            if(--k == 0) return cur.val;
            cur = cur.right;
        }
        return -1;
    }
}
// @lc code=end

