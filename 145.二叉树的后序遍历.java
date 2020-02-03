/*
 * @lc app=leetcode.cn id=145 lang=java
 *
 * [145] 二叉树的后序遍历
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
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if(root == null) return ans;
        stack.push(root);
        TreeNode cur = root;
        while(!stack.isEmpty()){
            while(cur.left != null){
                stack.push(cur.left);
                cur = cur.left;
            }
            if(cur.right != null){
                cur = cur.right;
                stack.push(cur);
            }
            else{
                cur = stack.pop();
                ans.add(cur.val);
                cur = stack.pop();
            }           
        }
        return ans;      
    }
}
// @lc code=end

