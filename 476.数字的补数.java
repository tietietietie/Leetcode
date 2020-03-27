/*
 * @lc app=leetcode.cn id=476 lang=java
 *
 * [476] 数字的补数
 */

// @lc code=start
class Solution {
    public int findComplement(int num) {
        if(num < 0) return -1;
        int ans = 0;
        int temp = num;
        int digitNum = 0;
        while(temp != 0){
            digitNum++;
            temp >>>= 1;
        }
        int flag = 1;
        for(int i = 0; i < digitNum; i++){
            if((flag & num) == 0)
                ans += flag;
            flag <<= 1;
        }
        return ans;
    }
}
// @lc code=end

