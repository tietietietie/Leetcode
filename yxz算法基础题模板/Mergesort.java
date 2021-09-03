public class Mergesort {
    //辅助数组，双指针, 递归
    private static int[] aux;
    public static void merge_sort(int[] nums) {
        aux = new int[nums.length];
        merge_sort(nums, 0, nums.length-1);
    }
    private static void merge_sort(int[] nums, int l, int r) {
        if(l >= r) return;
        int mid = (l + r) / 2;
        merge_sort(nums, l, mid);
        merge_sort(nums, mid+1, r);
        int i = l, j = mid+1, k = l;
        while(i <= mid && j <= r) {
            if(nums[i] < nums[j]) {
                aux[k++] = nums[i++];
            } else {
                aux[k++] = nums[j++];
            }
        }
        while(i <= mid) aux[k++] = nums[i++];
        while(j <= r) aux[k++] = nums[j++];
        for(k = l; k <= r; k++)
            nums[k] = aux[k];
    }
}
