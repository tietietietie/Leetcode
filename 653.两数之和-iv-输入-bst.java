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
    //BST转有序数组
    private void inorder(TreeNode root, ArrayList<Integer> array){
        if(root == null) return ;  //不能return null??
        inorder(root.left,array);
        array.add(root.val);
        inorder(root.right,array);
    }

    public boolean findTarget(TreeNode root, int k) {
        ArrayList<Integer> array = new ArrayList<>();
        inorder(root,array);
        int i = 0;
        int j = array.size()-1;
        while(i < j){
            int sum = array.get(i) + array.get(j);
            if(sum == k) return true;
            else if(sum < k) i++;
            else j--;
        }
        return false;
    }
}
// @lc code=end

