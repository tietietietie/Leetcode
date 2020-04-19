/*
 * @lc app=leetcode.cn id=1091 lang=java
 *
 * [1091] 二进制矩阵中的最短路径
 */

// @lc code=start
class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        if(grid[0][0] == 1 || grid[m-1][n-1] == 1) return -1;
        int[][] dirArr = new int[][]{{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}};
        LinkedList<Integer> queueI = new LinkedList<>();
        LinkedList<Integer> queueJ = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        queueI.offer(0);
        queueJ.offer(0);
        visited[0][0] = true;
        int step = 1;
        if(m-1 == 0 && n-1 == 0) return 1;
        while(!queueI.isEmpty()){
            int size = queueI.size();
            for(int i = 0; i < size; i++){
                int curI = queueI.poll();
                int curJ = queueJ.poll();
                for(int[] dir : dirArr){
                    int nextI = curI+dir[0];
                    int nextJ = curJ+dir[1];
                    if(nextI == m-1 && nextJ == n-1) return step+1;
                    if(nextI >= 0 && nextI < m && nextJ >= 0 && nextJ < n && !visited[nextI][nextJ] && grid[nextI][nextJ] == 0){
                        visited[nextI][nextJ] = true;
                        queueI.offer(nextI);
                        queueJ.offer(nextJ);
                    }
                }
            }
            step++;
        }
        return -1;
    }
}
// @lc code=end

