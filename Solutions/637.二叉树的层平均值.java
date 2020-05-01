/*
 * @lc app=leetcode.cn id=637 lang=java
 *
 * [637] 二叉树的层平均值
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
    public List<Double> averageOfLevels(TreeNode root) {
        //ans存储每层平均值
        List<Double> ans = new ArrayList<>();
        //使用queue BFS
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        //BFS
        while(!queue.isEmpty()){
            //当前queue存了一层节点，获得该层节点数。
            int cnt = queue.size();
            double sum = 0; //该层节点和
            //计算该层的节点和，并把下一层节点入队
            for(int i = 0; i < cnt; i++){
                TreeNode cur = queue.poll();
                sum += cur.val;
                if(cur.left != null)
                    queue.offer(cur.left);
                if(cur.right != null)
                    queue.offer(cur.right);
            }
            ans.add((double) sum/cnt);
            //至此该层节点全部出队，剩下了下一层节点
        }
        return ans;    
    }
}
// @lc code=end

