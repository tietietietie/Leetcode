/*
 * @lc app=leetcode.cn id=257 lang=java
 *
 * [257] 二叉树的所有路径
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
    public List<String> binaryTreePaths(TreeNode root) {
        ArrayList<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if(root == null) return ans;
        dfs(root,sb,ans);
        return ans;
    }

    private void dfs(TreeNode node, StringBuilder sb, ArrayList<String> ans){
        if(node.left == null && node.right == null){
            sb.append(node.val);
            ans.add(sb.toString());
            int size = String.valueOf(node.val).length();
            sb.delete(sb.length()-size,sb.length());
            return;
        }
        sb.append(node.val);
        sb.append("->");
        if(node.left != null) dfs(node.left,sb,ans);
        if(node.right != null) dfs(node.right,sb,ans);
        int size = String.valueOf(node.val).length();
        sb.delete(sb.length()-size-2,sb.length());
    }
}
// @lc code=end

