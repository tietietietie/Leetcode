/*
 * @lc app=leetcode.cn id=112 lang=java
 *
 * [112] 路径总和
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
import javafx.util.Pair;
class Solution {
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null) return false;
        Stack<Pair<TreeNode,Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root,sum));
        while(!stack.isEmpty()){
            Pair<TreeNode,Integer> pair = stack.pop();
            if(pair.getKey().left == null && pair.getKey().right == null && pair.getKey().val == pair.getValue()) return true;
            if(pair.getKey().left != null)
                stack.push(new Pair<>(pair.getKey().left,pair.getValue()-pair.getKey().val));
            if(pair.getKey().right != null)
                stack.push(new Pair<>(pair.getKey().right,pair.getValue()-pair.getKey().val));
        }
        return false;
    }
}
// @lc code=end

