/*
 * @lc app=leetcode.cn id=210 lang=java
 *
 * [210] 课程表 II
 */

// @lc code=start
class Solution {
    public int[] ans;
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] digraph = new List[numCourses];
        for(int i = 0; i < numCourses; i++)
            digraph[i] = new ArrayList<>();
        for(int[] edge : prerequisites)
            digraph[edge[1]].add(edge[0]);
        boolean[] globalMarked = new boolean[numCourses];
        boolean[] onStack = new boolean[numCourses];
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < numCourses; i++)
            if(hasCycle(digraph,i,globalMarked,onStack,stack))
                return new int[0];
        ans = new int[stack.size()];
        for(int i = 0; i < ans.length; i++)
            ans[i] = stack.pop();
        return ans;
    }


    private boolean hasCycle(List<Integer>[] digraph, int i, boolean[] globalMarked, boolean[] onStack, Stack<Integer> stack){
        if(onStack[i])
            return true;
        if(globalMarked[i])
            return false;
        onStack[i] = true;
        globalMarked[i] = true;
        for(int next : digraph[i]){
            if(hasCycle(digraph,next,globalMarked,onStack,stack))
                return true;
        }
        onStack[i] = false;
        stack.push(i);
        return false;
    }
}
// @lc code=end

