/*
 * @lc app=leetcode.cn id=101 lang=java
 *
 * [101] 对称二叉树
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
    public boolean isSymmetric(TreeNode root) {
        //array存每一层节点
        TreeNode[] lNodes = new TreeNode[1];
        lNodes[0] = root;
        return isSymmetric(lNodes,0);
    }

    private boolean isSymmetric(TreeNode[] lNodes, int level){
        //判断当前层是否对称
        int nullCount = 0;
        for(TreeNode n:lNodes){
            if(n == null)
                nullCount++;
        }
        if (nullCount == 1<<level)
            return true;
        int i = 0, j = (1<<level) - 1;
        while(i < j){
            if((lNodes[i] == null && lNodes[j] != null) || (lNodes[j] == null && lNodes[i] != null)||lNodes[i].val != lNodes[j].val)
                return false;
        }
        //更新lNodes到下一层
        TreeNode[] nextNodes = new TreeNode[1<<(level+1)];
        for(int k = 0; k < 1<<level; k++){
            nextNodes[2*k] = lNodes[k].left;
            nextNodes[2*k+1] = lNodes[k].right;
        }
        return isSymmetric(nextNodes,level+1);
    }
}
// @lc code=end

