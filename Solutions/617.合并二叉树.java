/*
 * @lc app=leetcode.cn id=617 lang=java
 *
 * [617] 合并二叉树
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
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if(t1 == null) return t2;
        if(t2 == null) return t1;
        Stack<TreeNode[]> stack = new Stack<>();
        stack.push(new TreeNode[]{t1,t2});
        while(!stack.isEmpty()){
            TreeNode[] t = stack.pop();
            t[0].val = t[0].val + t[1].val;

            if(t[0].left == null) 
                t[0].left = t[1].left;
            else if(t[0].left != null && t[1].left != null) 
                stack.push(new TreeNode[]{t[0].left,t[1].left});

            if(t[0].right == null) 
                t[0].right = t[1].right;           
            else if(t[0].right != null && t[1].right != null) 
                stack.push(new TreeNode[]{t[0].right,t[1].right});
        }
        return t1;
    }
}
// @lc code=end

