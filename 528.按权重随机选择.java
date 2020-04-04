/*
 * @lc app=leetcode.cn id=528 lang=java
 *
 * [528] 按权重随机选择
 */

// @lc code=start
class Solution {

    public int[] preSum;
    Random random = new Random();

    public Solution(int[] w) {
        preSum = new int[w.length];
        preSum[0] = w[0];
        for(int i = 1; i < w.length; i++)
            preSum[i] += preSum[i-1]+w[i];
    }
    
    public int pickIndex() {
        int target = random.nextInt(preSum[preSum.length-1]);
        int l = 0, h = preSum.length-1;
        while(l != h){
            int mid = (l+h)/2;
            if(target >= preSum[mid]) l = mid+1;
            else h = mid;
        }
        return l;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(w);
 * int param_1 = obj.pickIndex();
 */
// @lc code=end

