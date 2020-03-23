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
        //-1:no color, 0:blue, 1:red
        Arrays.fill(colors,-1);
        for(int i = 0; i < length; i++){
            //如果当前i节点未被连接过，则从新开始一个新的stack，对其连接的所有节点着色
            if(colors[i] == -1){
                colors[i] = 0;
                Stack<Integer> stack = new Stack<>();
                stack.push(i);
                while(!stack.isEmpty()){
                    int node = stack.pop();
                    for(int j : graph[node]){
                        if(colors[j] == -1){
                            colors[j] = colors[node] ^ 1;
                            stack.push(j);
                        }else if(colors[j] == colors[node])
                            return false;
                    }
                }
            }
        }
        return true;
    }
}
// @lc code=end

