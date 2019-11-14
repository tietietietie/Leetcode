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
        int m = nums1.length, n = nums2.length;
        int[] nums = new int[m+n];
        if(m == 0)
            nums = nums2;
        if(n == 0)
            nums = nums1;
        int count = 0, i = 0, j = 0;
        while(count < nums.length)
        {
            if(i == m)
                nums[count++] = nums2[j++];
            if(j == n)
                nums[count++] = nums1[i++];
            if(nums1[i] < nums2[j])
                nums[count++] = nums1[i++];
            else
                nums[count++] = nums2[j++];
        }

        if(m+n == 0)
            return 0.0;
        else if((m+n)/2 == 1)
            return (double)nums[(m+n)/2];
        else
            return (double)(nums[(m+n)/2 -1] + nums[(m+n)/2])/2;
    }
    public static void main(String[] args) {
        double median;
        int[] a = {1,3}, b ={2};
        Solution s = new Solution();  //必须创建一个新的solution，就算这个函数在class内
        median = s.findMedianSortedArrays(a, b);  //也不能直接调用non-static的函数
    }
}