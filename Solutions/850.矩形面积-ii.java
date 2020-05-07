/*
 * @lc app=leetcode.cn id=850 lang=java
 *
 * [850] 矩形面积 II
 */

// @lc code=start
class Solution {
    public int rectangleArea(int[][] rectangles) {
        HashSet<Integer> xSet = new HashSet<>();
        HashSet<Integer> ySet = new HashSet<>();
        for(int[] rectangle : rectangles){
            xSet.add(rectangle[0]);
            xSet.add(rectangle[2]);
            ySet.add(rectangle[1]);
            ySet.add(rectangle[3]);
        }
        Integer[] imapx = (Integer[])xSet.toArray(new Integer[0]);
        Integer[] imapy = (Integer[])ySet.toArray(new Integer[0]);
        Arrays.sort(imapx);
        Arrays.sort(imapy);
        HashMap<Integer,Integer> xmapi = new HashMap<>();
        HashMap<Integer,Integer> ymapi = new HashMap<>();
        for(int i = 0; i < imapx.length; i++)
            xmapi.put(imapx[i],i);
        for(int i = 0; i < imapy.length; i++)
            ymapi.put(imapy[i],i);
        boolean[][] grid = new boolean[imapx.length][imapy.length];
        for(int[] rectangle : rectangles)
            for(int i = xmapi.get(rectangle[0]); i < xmapi.get(rectangle[2]); i++)
                for(int j = ymapi.get(rectangle[1]); j < ymapi.get(rectangle[3]); j++)
                    grid[i][j] = true;
        long ans = 0;
        for(int i = 0; i < imapx.length; i++)
            for(int j = 0; j < imapy.length; j++)
                if(grid[i][j])
                    ans += (long)(imapx[i+1] - imapx[i]) * (imapy[j+1] - imapy[j]);
        ans %= 1000000007;
        return (int) ans;
    }
}
// @lc code=end

