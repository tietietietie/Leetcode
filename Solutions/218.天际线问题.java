/*
 * @lc app=leetcode.cn id=218 lang=java
 *
 * [218] 天际线问题
 */

// @lc code=start
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        if(buildings.length == 0){
            return  new ArrayList<>();
        }
        return merge(buildings, 0, buildings.length - 1);
    }
    
    private List<List<Integer>> merge(int[][] buildings, int start, int end) {
        if(start > end) return new ArrayList<>();
        if(start == end){
            List<List<Integer>> skyline = new ArrayList<>();
            skyline.add(Arrays.asList(buildings[start][0],buildings[start][2]));
            skyline.add(Arrays.asList(buildings[start][1],0));
            return skyline;
        }
        int mid = (start + end) / 2;
        List<List<Integer>> leftSkyline  = merge(buildings,start,mid);
        List<List<Integer>> rightSkyline = merge(buildings,mid+1,end);
        List<List<Integer>> curSkyline = new ArrayList<>();
        int leftHeight = 0, rightHeight = 0, curHeight = 0, i = 0, j = 0;
        while(i < leftSkyline.size() || j < rightSkyline.size()){
            if(i == leftSkyline.size()){
                curSkyline.add(rightSkyline.get(j));
                j++;
                continue;
            }
            if(j == rightSkyline.size()){
                curSkyline.add(leftSkyline.get(i));
                i++;
                continue;
            }
            int leftX  = leftSkyline.get(i).get(0);
            int rightX = rightSkyline.get(j).get(0);
            int curX = leftX < rightX ? leftX : rightX;
            if(leftX < rightX){
                leftHeight = leftSkyline.get(i).get(1);
                i++;
            }else if(leftX > rightX){
                rightHeight = rightSkyline.get(j).get(1);
                j++;
            }else{
                leftHeight = leftSkyline.get(i).get(1);
                rightHeight = rightSkyline.get(j).get(1);
                i++;
                j++;
            }
            int maxHeight = Math.max(leftHeight,rightHeight);
            if(maxHeight != curHeight){
                curSkyline.add(Arrays.asList(curX,maxHeight));
                curHeight = maxHeight;
            }
        }
        return curSkyline;
    }
}
// @lc code=end

