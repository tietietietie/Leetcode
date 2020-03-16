/*
 * @lc app=leetcode.cn id=769 lang=java
 *
 * [769] 最多能完成排序的块
 */

// @lc code=start
class Solution {
    public int maxChunksToSorted(int[] arr) {
        return splitChunk(arr,0,arr.length-1);
    }

    private int splitChunk(int[] arr, int l, int r){
        if(l > r) return 0;
        int count = 0;
        while(l <= r && l == arr[l]){
            l++;
            count++;
        }
        while(l <=r  && r == arr[r]){
            r--;
            count++;
        }
        if(l > r) return count;        
        int max = -1;
        int idx = -1;
        for(int i = l; i <=r; i++)
            if(arr[i] > max){
                max = arr[i];
                idx = i;
            }
        if(max > r)
            return -1;
        int chunks1 = splitChunk(arr,l,idx-1);
        return chunks1 == -1 ? count+1 : chunks1+count+1;      
    }
}
// @lc code=end

