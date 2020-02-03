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
        dfs(root,ans);
        return ans;        
    }

    private void dfs(TreeNode root, List<Integer> ans){
        if(root == null) return;
        ans.add(root.val);
        dfs(root.left,ans);
        dfs(root.right,ans);
    }
}
// @lc code=end

