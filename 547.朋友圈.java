/*
 * @lc app=leetcode.cn id=547 lang=java
 *
 * [547] 朋友圈
 */

// @lc code=start
class Solution {
    public int findCircleNum(int[][] M) {
        int n = M.length;
        int[] uf = new int[n];
        for(int i = 0; i < n; i++)
            uf[i] = i;
        for(int i = 0; i < n; i++)
            for(int j = i+1; j < n; j++){
                if(M[i][j] == 1 && uf[i] != uf[j]){
                    int temp = uf[j];
                    for(int k = 0; k < n; k++){
                        if(uf[k] == temp)
                            uf[k] = uf[i];
                    }
                }
            }
        HashSet<Integer> set = new HashSet<>();
        for(int item : uf)
            if(!set.contains(item))
                set.add(item);
        return set.size();
    }
}
// @lc code=end

