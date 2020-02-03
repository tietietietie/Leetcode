/*
 * @lc app=leetcode.cn id=144 lang=java
 *
 * [144] 二叉树的前序遍历
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
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        TreeNode cur = root;
        while(cur != null){
            if(cur.left == null){
                ans.add(cur.val);
                cur = cur.right;
                continue;
            }
            TreeNode pre = cur.left;
            while(pre.right != null && pre.right != cur){
                pre = pre.right;
            }
            if(pre.right == null){
                pre.right = cur;
                ans.add(cur.val);
                cur = cur.left;
            }
            if(pre.right == cur){
                pre.right = null;
                cur = cur.right;
            }
        }
        return ans;        
    }
}
// @lc code=end

