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
    //ans表示所有路径条数，sum为目标数，sums表示该节点到所有父节点的剩余路径
    private int ans,sum;

    public int pathSum(TreeNode root, int sum) {
    ans = 0;
    this.sum = sum;
    ArrayList<Integer> sums = new ArrayList<Integer>();
    sums.add(sum);
    recursivelyCount(root,sums);
    return ans;
    }

    private void recursivelyCount(TreeNode node, ArrayList<Integer> sums){
        if(node == null)
            return;
        //防止改变sums的值
        ArrayList<Integer> Nsums = new ArrayList<Integer>();
        Nsums = (ArrayList<Integer>)sums.clone();
        for(int i = 0; i < Nsums.size(); i++){
            if(Nsums.get(i) == node.val)
                ans++;
            Nsums.set(i,Nsums.get(i) - node.val);
        }
        Nsums.add(sum);
        recursivelyCount(node.left,Nsums);
        recursivelyCount(node.right,Nsums);
    }
}
// @lc code=end

