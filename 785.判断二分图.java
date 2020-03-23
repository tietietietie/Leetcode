/*
 * @lc app=leetcode.cn id=785 lang=java
 *
 * [785] 判断二分图
 */

// @lc code=start
class Solution {
    private HashSet<Integer> A;
    private HashSet<Integer> B;
    public boolean isBipartite(int[][] graph) {
        int length = graph.length;
        boolean[] visited = new boolean[length];
        A = new HashSet<>();
        B = new HashSet<>();
        for(int i = 0; i < length; i++){
            if(visit(graph,i,visited) == false)
                return false;
        }
        return true;
    }

    private boolean visit(int[][] graph, int i, boolean[] visited){
        if(!visited[i]){
            visited[i] = true;
            A.add(i);
            for(int j : graph[i]){
                if(A.contains(j))
                    return false;
                visited[j] = true;
                B.add(j);
            }
        }else if(A.contains(i)){
            for(int j : graph[i]){
                if(A.contains(j))
                    return false;
                visited[j] = true;
                B.add(j);
            }
        }else{
            for(int j : graph[i]){
                if(B.contains(j))
                    return false;
                visited[j] = true;
                A.add(j);
            }
        }
        return true;
    }
}
// @lc code=end

