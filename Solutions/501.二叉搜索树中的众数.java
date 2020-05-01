/*
 * @lc app=leetcode.cn id=501 lang=java
 *
 * [501] 二叉搜索树中的众数
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
    private List<Integer> modes;
    private int max,count;
    private TreeNode pre;
    public int[] findMode(TreeNode root) {
        max = 0;
        count = 0;
        pre = null;
        modes = new ArrayList<>();
        inorder(root);
        int size = modes.size();
        int[] ans = new int[size];
        for(int i = 0; i < size; i++){
            ans[i] = modes.get(i);
        }
        return ans;        
    }

    private void inorder(TreeNode root){
        if(root == null) return;
        inorder(root.left);
        if(pre != null && pre.val == root.val)
            count++;
        else count = 1;

        if(count == max) modes.add(root.val);
        if(count > max){
            max = count;
            modes.clear();
            modes.add(root.val);
        }
        pre = root;
        inorder(root.right);
        return;
    }
}
// @lc code=end

