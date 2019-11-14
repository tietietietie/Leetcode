class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] nums = new int[m+n];
        if(m == 0)
            nums = nums2;
        else if(n == 0)
            nums = nums1;
        else
        {
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
        }


        if((m+n)%2 == 1)
            return (double)nums[(m+n)/2];
        else
            return (double)(nums[(m+n)/2 -1] + nums[(m+n)/2])/2;
    }
}
