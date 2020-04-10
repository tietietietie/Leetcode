/*
 * @lc app=leetcode.cn id=279 lang=java
 *
 * [279] 完全平方数
 */

// @lc code=start
class Solution {
    public int numSquares(int n) {
        ArrayList<Integer> squareNums = new ArrayList<>();
        for(int i = 1; i*i <= n; i++)
            squareNums.add(i*i);
        HashSet<Integer> curLevel = new HashSet<>();
        curLevel.add(n);
        int level = 0;
        while(!curLevel.isEmpty()){
            level++;
            HashSet<Integer> nextLevel = new HashSet<>();
            for(int remainder : curLevel){
                for(int squareNum : squareNums){
                    if(remainder == squareNum)
                        return level;
                    else if(remainder > squareNum)
                        nextLevel.add(remainder - squareNum);
                    else
                        break;
                }
            }
            curLevel = nextLevel;
        }
        return -1;
    }
}
// @lc code=end

