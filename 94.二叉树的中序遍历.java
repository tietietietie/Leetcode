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
        Stack<TreeNode> stack = new Stack<>();
        //cur表示当前遍历的子树
        TreeNode cur = root;
        while(cur != null || !stack.isEmpty()){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            //cur此时定位到此次循环，也就是之前cur的最左叶子节点的左侧Null,pop最左侧节点
            cur = stack.pop();//此时表示cur左子树遍历完成
            ans.add(cur.val); //添加根节点
            cur = cur.right; //定位到根节点右子树
        }
        return ans;
    }
}
// @lc code=end

