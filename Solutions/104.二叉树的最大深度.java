/*
 * @lc app=leetcode.cn id=104 lang=java
 *
 * [104] 二叉树的最大深度
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
    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        LinkedList<Pair<TreeNode,Integer>> stack = new LinkedList<>();
        stack.push(new Pair<>(root,1));
        int maxDepth = 0;
        while(!stack.isEmpty()){
            Pair<TreeNode,Integer> pair = stack.pop();
            int cur_depth = pair.getValue();
            TreeNode cur_node = pair.getKey();
            maxDepth = Math.max(maxDepth,cur_depth);
            if(cur_node.right != null)
                stack.push(new Pair<TreeNode,Integer>(cur_node.right,cur_depth+1));
            if(cur_node.left != null)
                stack.push(new Pair<>(cur_node.left,cur_depth+1));
        }
        return maxDepth;
    }
}
// @lc code=end

