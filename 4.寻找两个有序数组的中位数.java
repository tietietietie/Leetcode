/*
 * @lc app=leetcode.cn id=4 lang=java
 *
 * [4] 寻找两个有序数组的中位数
 */

// @lc code=start
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int i, j, m, n;
        double median;
        m = nums1.length;
        n = nums2.length;
        int[] A =nums1, B = nums2;
        if(n < m)
        {
            int temp = n;
            n = m;
            m = temp;
            A = nums2;
            B = nums1;
        }
        int imin = 0, imax = m;
        while(imin <= imax)
        {
            i = (imin + imax) / 2;
            j = (m+n+1)/2 - i;
            if(i > 0 && (A[i-1] > B[j]) && j < n)
                imax = i - 1;
            else if (i < m && (B[j-1] > A[i]) && j > 0)
                imin = i + 1;
            else
            {
                int medianl, medianr;
                if(i == 0)
                    medianl = B[j-1];
                else if(j == 0)
                    medianl = A[i];
                else 
                    medianl = Math.max(A[i-1],B[j-1]); 
                if(i == m)
                    medianr = B[j];
                else if(j == n)
                    medianr = A[i];
                else
                    medianr = Math.min(A[i],B[j]);
                if((m+n)/2 == 1)
                    median = medianl;
                else
                    median = (medianl+medianr)/2.0;
                return median;
            }
        }
        return 0.0;
    }
    public static void main(String[] args) {
        double median;
        int[] a = {1,2}, b ={3,4};
        Solution s = new Solution();  //必须创建一个新的solution，就算这个函数在class内
        median = s.findMedianSortedArrays(a, b);  //也不能直接调用non-static的函数
    }
}
// @lc code=end

