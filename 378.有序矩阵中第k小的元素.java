/*
 * @lc app=leetcode.cn id=378 lang=java
 *
 * [378] 有序矩阵中第K小的元素
 */

// @lc code=start
public class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int lo = matrix[0][0], hi = matrix[matrix.length - 1][matrix[0].length - 1];//[lo, hi)
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = 0;
            for(int i = 0; i < matrix.length; i++) 
                for(int j = 0; j < matrix.length && matrix[i][j] <= mid; j++)
                    count++;
            if(count < k) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
// @lc code=end

