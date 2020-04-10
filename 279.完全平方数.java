/*
 * @lc app=leetcode.cn id=279 lang=java
 *
 * [279] 完全平方数
 */

// @lc code=start
class Solution {
    public int numSquares(int n) {
        HashSet<Integer> squareNums = new HashSet<>();
        for(int i = 1; i*i <= n; i++)
            squareNums.add(i*i);
        HashSet<Integer> curLevel = new HashSet<>();
        curLevel.add(n);
        int level = 0;
        while(!curLevel.isEmpty()){
            level++;
            HashSet<Integer> nextLevel = new HashSet<>();
            for(int remainder : curLevel){
                if(squareNums.contains(remainder))
                    return level;
                for(int squareNum : squareNums)
                    if(remainder > squareNum)
                        nextLevel.add(remainder-squareNum);
            }
            curLevel = nextLevel;
        }
        return -1;
    }
}
// @lc code=end

