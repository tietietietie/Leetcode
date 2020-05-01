/*
 * @lc app=leetcode.cn id=785 lang=java
 *
 * [785] 判断二分图
 */

// @lc code=start
class Solution {
    public boolean isBipartite(int[][] graph) {
        int length = graph.length;
        int[] colors = new int[length];
        Arrays.fill(colors,-1);
        for(int i = 0; i < length; i++){
            if(colors[i] == -1 && !dfs(graph,i,0,colors))
                return false;
        }
        return true;
    }


    private boolean dfs(int[][] graph, int i, int suggestColor, int[] colors){
        if(colors[i] != -1)
            return colors[i] == suggestColor;
        colors[i] = suggestColor;
        for(int nei : graph[i])
            if(!dfs(graph,nei,suggestColor ^ 1,colors))
                return false;
        return true;
    }
}
// @lc code=end

