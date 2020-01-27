/*
 * @lc app=leetcode.cn id=437 lang=java
 *
 * [437] 路径总和 III
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
    public int pathSum(TreeNode root, int sum) {
    int[] pathNodes = new int[1000];
    return pathSum(root,sum,pathNodes,0);
    }

    private int pathSum(TreeNode node, int sum, int[] pathNodes, int depth){
        if(node == null)
            return 0;
        int n = 0;
        pathNodes[depth] = node.val;
        int temp = 0;
        for(int i = depth; i >=0; i--){
            temp += pathNodes[i];
            if(temp == sum)
                n++;
        }
        int n1 = pathSum(node.left,sum,pathNodes,depth+1);
        int n2 = pathSum(node.right,sum,pathNodes,depth+1);
        return n+n1+n2;
    }
}
// @lc code=end

