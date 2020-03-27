/*
 * @lc app=leetcode.cn id=693 lang=java
 *
 * [693] 交替位二进制数
 */

// @lc code=start
class Solution {
    public boolean hasAlternatingBits(int n) {
        HashSet<Integer> set = new HashSet<>();
        int item = 1;
        boolean flag = true;
        while(item > 0){
            set.add(item);
            if(flag){
                item <<= 1;
                flag = !flag;
            }else{
                item <<= 1;
                item++;
                flag = !flag;
            }
        }
        return set.contains(n);
    }
}
// @lc code=end

