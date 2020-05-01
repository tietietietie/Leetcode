/*
 * @lc app=leetcode.cn id=605 lang=java
 *
 * [605] 种花问题
 */

// @lc code=start
class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if(n == 0) return true;
        for(int i = 0; i < flowerbed.length; i++){
            int left = i-1 >= 0 ? flowerbed[i-1] : 0;
            int right = i+1 < flowerbed.length ? flowerbed[i+1] : 0;
            if(left+right+flowerbed[i] == 0){
                flowerbed[i] = 1;
                n--;
            }
            if(n == 0) return true;
        }
        return n == 0;
    }
}
// @lc code=end

