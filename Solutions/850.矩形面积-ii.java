/*
 * @lc app=leetcode.cn id=850 lang=java
 *
 * [850] 矩形面积 II
 */

// @lc code=start
class Solution {
    public int rectangleArea(int[][] rectangles) {
        int OPEN = 1, CLOSE = 0;
        int[][] events = new int[2 * rectangles.length][];
        int i = 0;
        for(int[] rectangle : rectangles){
            events[i++] = new int[]{rectangle[1],OPEN,rectangle[0],rectangle[2]};
            events[i++] = new int[]{rectangle[3],CLOSE,rectangle[0],rectangle[2]};
        }
        Arrays.sort(events, (o1,o2) -> o1[0] - o2[0]);
        long ans = 0;
        int cur_y = events[0][0];
        ArrayList<int[]> active = new ArrayList<>();
        for(int[] event : events){
            int y = event[0], flag = event[1], x1 = event[2], x2 = event[3];
            long query = 0;
            int cur_x = -1;
            for(int[] interval : active){
                cur_x  = Math.max(cur_x, interval[0]);
                query += Math.max(interval[1]-cur_x,0);
                cur_x = Math.max(cur_x,interval[1]);
            }
            ans += query * (y-cur_y);
            if(flag == OPEN){
                active.add(new int[]{x1,x2});
                Collections.sort(active, (o1,o2) -> o1[0] - o2[0]);
            }else{
                for(int j = 0; j < active.size(); j++)
                    if(active.get(j)[0] == x1 && active.get(j)[1] == x2){
                        active.remove(j);
                        break;
                    }
            }
            cur_y = y;
        }
        ans %= 1000000007;
        return (int)ans;
    }
}
// @lc code=end

