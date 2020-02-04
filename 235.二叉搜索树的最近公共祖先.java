/*
 * @lc app=leetcode.cn id=235 lang=java
 *
 * [235] 二叉搜索树的最近公共祖先
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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode cur = root;
        int pval = p.val;
        int qval = q.val;
        //cur表示当前判断的节点
        while(cur != null){
            if(pval < cur.val && qval < cur.val){
                cur = cur.left;
                continue;
            }
            else if(pval > cur.val && qval > cur.val){
                cur = cur.right;
                continue;
            }
            return cur;
        }
        return null;
    }
}
// @lc code=end

