/*
 * @lc app=leetcode.cn id=513 lang=java
 *
 * [513] 找树左下角的值
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
    public int findBottomLeftValue(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode cur = root;
        while(!queue.isEmpty()){
            cur = queue.poll();
            if(cur.right != null) queue.offer(cur.right); 
            if(cur.left != null) queue.offer(cur.left);              
        }
        return cur.val;        
    }
}
// @lc code=end

