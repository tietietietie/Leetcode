/*
 * @lc app=leetcode.cn id=547 lang=java
 *
 * [547] 朋友圈
 */

// @lc code=start
class Solution {
    public int findCircleNum(int[][] M) {
        int m = M.length;
        boolean[] visited = new boolean[m];
        int ans = 0;
        LinkedList<Integer> queue = new LinkedList<>();
        for(int i = 0; i < m; i++){
            if(!visited[i]){
                ans++;
                queue.offer(i);
                visited[i] = true;
                while(!queue.isEmpty()){
                    int curNode = queue.poll();
                    for(int j = 0; j < m; j++)
                        if(M[curNode][j] == 1 && !visited[j]){
                            queue.offer(j);
                            visited[j] = true;
                        }
                }
            }
        }
        return ans;
    }
}
// @lc code=end

