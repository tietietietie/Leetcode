/*
 * @lc app=leetcode.cn id=378 lang=java
 *
 * [378] 有序矩阵中第K小的元素
 */

// @lc code=start
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        PriorityQueue<Tuple> pq = new PriorityQueue<>();
        for(int i = 0; i < n; i++)
            pq.offer(new Tuple(0,i,matrix[0][i]));
        for(int i = 0; i < k-1; i++){
            Tuple temp = pq.poll();
            if(temp.x == n-1)
                continue;
            pq.offer(new Tuple(temp.x+1,temp.y,matrix[temp.x+1][temp.y]));
        }
        return pq.poll().val;
    }

    private class Tuple implements Comparable<Tuple>{
        int x,y,val;
        public Tuple(int x, int y, int val){
            this.x = x;
            this.y = y;
            this.val = val;
        }

        @Override
        public int compareTo(Tuple that){
            return this.val - that.val;
        }
    }
}
// @lc code=end

