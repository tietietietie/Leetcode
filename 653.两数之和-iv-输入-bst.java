/*
 * @lc app=leetcode.cn id=653 lang=java
 *
 * [653] 两数之和 IV - 输入 BST
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
    //利用DFS判断找没找到节点，如果没找到，则加入hashset
    private boolean find(TreeNode node, int k, Set<Integer> set){
        if(node == null) return false;
        if(set.contains(k-node.val)) return true;
        set.add(node.val);
        return find(node.left,k,set) || find(node.right,k,set);
    }


    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();
        return find(root,k,set);
    }
}
// @lc code=end

