/*
 * @lc app=leetcode.cn id=218 lang=java
 *
 * [218] 天际线问题
 */

// @lc code=start
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<int[]> heights = new ArrayList<>();
        for(int[] building : buildings){
            heights.add(new int[]{building[0],-building[2]});
            heights.add(new int[]{building[0],building[1]});
        }
        Collections.sort(heights, (o1,o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]);
        TreeMap<Integer,Integer> heightMap = new TreeMap<>();
        List<List<Integer>> ans = new ArrayList<>();
        heightMap.put(0,1);
        int preHeight = 0;
        for(int[] height : heights){
            if(height[1] < 0){
                Integer cnt  = heightMap.get(-height[1]);
                cnt = cnt == null ? 1 : cnt+1;
                heightMap.put(-height[1],cnt);
            }else{
                Integer cnt = heightMap.get(height[1]);
                if(cnt == 1)
                    heightMap.remove(height[1]);
                else
                    heightMap.put(height[1],cnt-1);
            }
            int curHeight = heightMap.firstKey();
            if(curHeight != preHeight){
                ans.add(Arrays.asList(height[0],curHeight));
                preHeight = curHeight;
            }
        }
        return ans;
    }
}
// @lc code=end

