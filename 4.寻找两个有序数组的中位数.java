/*
 * @lc app=leetcode.cn id=4 lang=java
 *
 * [4] 寻找两个有序数组的中位数
 */

// @lc code=start
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // int i, j, m, n;
        // double median;
        // m = nums1.length;
        // n = nums2.length;
        // int[] A =nums1, B = nums2;
        // if(n < m)
        // {
        //     int temp = n;
        //     n = m;
        //     m = temp;
        //     A = nums2;
        //     B = nums1;
        // }
        // int imin = 0, imax = m;
        // while(imin <= imax)
        // {
        //     i = (imin + imax) / 2;
        //     j = (m+n+1)/2 - i;
        //     if(i > 0 && (A[i-1] > B[j]) && j < n)
        //         imax = i - 1;
        //     else if (i < m && (B[j-1] > A[i]) && j > 0)
        //         imin = i + 1;
        //     else
        //     {
        //         int medianl, medianr;
        //         if(i == 0)
        //             medianl = B[j-1];
        //         else if(j == 0)
        //             medianl = A[i-1];
        //         else 
        //             medianl = Math.max(A[i-1],B[j-1]); 
        //         if((m+n)%2 == 1)
        //             return medianl;

        //         if(i == m)
        //             medianr = B[j];
        //         else if(j == n)
        //             medianr = A[i];
        //         else
        //             medianr = Math.min(A[i],B[j]);
        //         median = (medianl+medianr)/2.0;
        //         return median;
        //     }
        // }
        // return 0.0;
        /* 合并数组解法 */
        // int m = nums1.length, n = nums2.length;
        // int[] nums = new int[m+n];
        // if(m == 0)
        //     nums = nums2;
        // if(n == 0)
        //     nums = nums1;
        // int count = 0, i = 0, j = 0;
        // while(count < nums.length)
        // {
        //     if(i == m)
        //         nums[count++] = nums2[j++];
        //     else if(j == n)
        //         nums[count++] = nums1[i++];
        //     else if(nums1[i] < nums2[j])
        //         nums[count++] = nums1[i++];
        //     else
        //         nums[count++] = nums2[j++];
        // }

        // if(m+n == 0)
        //     return 0.0;
        // else if((m+n)/2 == 1)
        //     return (double)nums[(m+n)/2];
        // else
        //     return (double)(nums[(m+n)/2 -1] + nums[(m+n)/2])/2;

        /* 找k小值递归解法 */
        int m = nums1.length, n = nums2.length;
        int a = getKth(nums1,0,m,nums2,0,n,(m+n+1)/2);
        int b = getKth(nums1,0,m,nums2,0,n,(m+n+2)/2);
        return (a+b) * 0.5;
    }
    private int getKth(int[] nums1, int left1,int m, int[] nums2, int left2, int n, int k)
        {
            if(left1 == m)
                return nums2[left2+k-1];
            else if(left2 == n)
                return nums1[left1+k-1];
            if(k == 1)
                return Math.min(nums1[left1],nums2[left2]);
            
            int i = left1 + Math.min(k/2-1,m-left1-1);
            int j = left2 + Math.min(k/2-1,n-left2-1);

            if(nums1[i] < nums2[j])
                return getKth(nums1,i+1,m,nums2,left2,n,k-(i-left1+1));
            else
            return getKth(nums1,left1,m,nums2,j+1,n,k-(j-left2+1));
        }  
    public static void main(String[] args) {
        double median;
        int[] a = {1,4}, b ={2,3,5,6};
        Solution s = new Solution();  //必须创建一个新的solution，就算这个函数在class内
        median = s.findMedianSortedArrays(a, b);  //也不能直接调用non-static的函数
        System.out.print(median);
    }
}
// @lc code=end

// 合并两个数组

