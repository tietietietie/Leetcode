/*
 * @lc app=leetcode.cn id=230 lang=java
 *
 * [230] 二叉搜索树中第K小的元素
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
    public int kthSmallest(TreeNode root, int k) {
        HashMap<TreeNode,Integer> map = new HashMap<>();
        dfs(root,map);
        int ans = find(root,map,k);
        return ans;
    }

    private int dfs(TreeNode root, HashMap<TreeNode,Integer> map){
        if(root == null) return 0;
        int left = dfs(root.left,map);
        int right = dfs(root.right,map);
        map.put(root,left);
        return left+right+1;
    }

    private int find(TreeNode root,HashMap<TreeNode,Integer> map, int k){
        int a = map.get(root);
        if(a+1 == k)
            return root.val;
        else if(a+1 > k)
            return find(root.left,map,k);
        else
            return find(root.right,map,k-a-1);
    } 
}
// @lc code=end

