/*
 * @lc app=leetcode.cn id=671 lang=java
 *
 * [671] 二叉树中第二小的节点
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
    public int findSecondMinimumValue(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        int ans = root.val;
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode cur_node = queue.poll();
            if(cur_node.val != root.val){
                if(ans == root.val)
                    ans = cur_node.val;
                else
                    ans = Math.min(cur_node.val,ans);
            }
            if(cur_node.left != null){
                queue.offer(cur_node.left);
                queue.offer(cur_node.right);
            }       
        }
        if(ans == root.val)
            return -1;
        return ans;
    }
}
// @lc code=end

