/*
 * @lc app=leetcode.cn id=94 lang=java
 *
 * [94] 二叉树的中序遍历
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
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        TreeNode cur = root;
        while(cur != null){
            //如果当前cur树没有左子树，不做任何变化
            if(cur.left == null){
                ans.add(cur.val);
                cur = cur.right;
                continue;
            }
            else{
                TreeNode pre = cur.left;
                //找到前驱节点：左子树最右节点
                while(pre.right != null){
                    pre = pre.right;
                }
                //连接
                pre.right = cur;
                //防止成环
                TreeNode temp = cur;
                cur = cur.left;
                temp.left = null;
            }
        }
        return ans;
    }
}
// @lc code=end

