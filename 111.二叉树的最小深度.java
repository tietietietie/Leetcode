/*
 * @lc app=leetcode.cn id=111 lang=java
 *
 * [111] 二叉树的最小深度
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
    public int minDepth(TreeNode root) {
        //创建queue，进行BFS
        LinkedList<TreeNode> queue = new LinkedList<>();
        LinkedList<Integer> depths = new LinkedList<>(); 
        queue.offer(root);
        depths.offer(1);
        while(!queue.isEmpty()){
            TreeNode n = queue.poll();
            int depth = depths.poll();
            if(n == null) continue;
            if(n.left == null && n.right == null) return depth;
            queue.offer(n.left);
            depths.offer(depth+1);
            queue.offer(n.right);
            depths.offer(depth+1);
        }
        return 0;      
    }
}
// @lc code=end

