public class Solution
{
    public double findMedianSortedArrays(int[] nums1, int[] nums2)
    {
        int m = nums1.length, n = nums2.length;
        return (getKth(nums1,0,nums2,0, (m+n+1)/2)+getKth(nums1,0,nums2,0, (m+n+2)/2)) * 0.5;
        private double getKth(int[] nums1, int left1, int[] nums2, int left2, int k)
        {
            if(left1 == m)
                return nums2[left1+k-1];
            else if(left2 == n)
                return nums1[left2+k-1];
            if(k == 1)
                return Math.min(nums1[left1],nums2[left2]);
            
            int i = left1 + Math.min(k/2-1,m-left1-1);
            int j = left2 + Math.min(k/2-1,n-left2-1);

            if(nums1[left1] < nums2[left2])
                return getKth(nums1,i+1,nums2,left2);
            else
            return getKth(nums1,left1,nums2,j+1);
        }
    }
}