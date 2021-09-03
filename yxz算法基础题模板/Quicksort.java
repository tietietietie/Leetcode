public class Quicksort{
    public static void quicksort(int[] nums) {
        partition(nums, 0, nums.length-1);
    }

    private static void partition(int[] nums, int l, int r) {
        if(l >= r) return;
        int pivot = nums[l], i = l, j = r;
        while(i < j) {
            while(i < j && nums[j] >= pivot) j--;
            nums[i] = nums[j];
            while(i < j && nums[i] <= pivot) i++;
            nums[j] = nums[i];
        }
        nums[i] = pivot;
        partition(nums, l, i-1);
        partition(nums, i+1, r);
    }

    public static void main(String[] args) {
        
    }
}