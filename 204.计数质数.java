/*
 * @lc app=leetcode.cn id=204 lang=java
 *
 * [204] 计数质数
 */

// @lc code=start
class Solution {
    public int countPrimes(int n) {
         boolean[] isPrime = new boolean[n];
         int count = 0;
        for(int i = 2; i < n; i++){
            if(isPrime[i])
                continue;
            count++;
            for(long j = (long)(i) * i; j < n; j += i)
                isPrime[(int)j] = true;
        }
        return count;
    }
}
// @lc code=end

