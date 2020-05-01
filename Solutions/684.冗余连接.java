/*
 * @lc app=leetcode.cn id=684 lang=java
 *
 * [684] 冗余连接
 */

// @lc code=start
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        HashSet<Integer> nodes = new HashSet<>();
        int[] ans = new int[2];
        for(int[] edge : edges){
            if(nodes.contains(edge[0]) && nodes.contains(edge[1]))
                ans = edge;
            if(!nodes.contains(edge[0]))
                nodes.add(edge[0]);
            if(!nodes.contains(edge[1]))
                nodes.add(edge[1]);
        }
        return ans;
    }
}
// @lc code=end

