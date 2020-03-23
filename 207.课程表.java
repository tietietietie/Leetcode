/*
 * @lc app=leetcode.cn id=207 lang=java
 *
 * [207] 课程表
 */

// @lc code=start
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] digraph = new List[numCourses];
        for(int i = 0; i < numCourses; i++)
            digraph[i] = new ArrayList<>();
        for(int[] edge : prerequisites)
            digraph[edge[1]].add(edge[0]);
        boolean[] globalMarked = new boolean[numCourses];
        boolean[] onStack = new boolean[numCourses];
        for(int i = 0; i < numCourses; i++)
            if(hasCycle(digraph,i,globalMarked,onStack))
                return false;
        return true;
    }

    private boolean hasCycle(List<Integer>[] digraph, int i, boolean[] globalMarked, boolean[] onStack){
        if(onStack[i])
            return true;
        if(globalMarked[i])
            return false;
        onStack[i] = true;
        globalMarked[i] = true;
        for(int next : digraph[i]){
            if(hasCycle(digraph,next,globalMarked,onStack))
                return true;
        }
        onStack[i] = false;
        return false;
    }
}
// @lc code=end

