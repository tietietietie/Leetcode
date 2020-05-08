/*
 * @lc app=leetcode.cn id=850 lang=java
 *
 * [850] 矩形面积 II
 */

// @lc code=start
class Solution {
    public int rectangleArea(int[][] rectangles) {
        int OPEN = 1, CLOSE = -1;
        HashSet<Integer> xSet = new HashSet<>();
        int[][] events = new int[2 * rectangles.length][];
        int i = 0;
        for(int[] rectangle : rectangles){
            xSet.add(rectangle[0]);
            xSet.add(rectangle[2]);
            events[i++] = new int[]{rectangle[1],OPEN,rectangle[0],rectangle[2]};
            events[i++] = new int[]{rectangle[3],CLOSE,rectangle[0],rectangle[2]};
        }
        Integer[] imapx = (Integer[])xSet.toArray(new Integer[0]);
        Arrays.sort(imapx);
        Arrays.sort(events,(o1,o2) -> o1[0] - o2[0]);
        HashMap<Integer,Integer> xmapi = new HashMap<>();
        for(i = 0; i < imapx.length; i++)
            xmapi.put(imapx[i],i);
        int[] count = new int[imapx.length];
        int cur_y = events[0][0];
        long ans = 0;
        for(int[] event : events){
            int y = event[0], flag = event[1], x1 = xmapi.get(event[2]), x2 = xmapi.get(event[3]);
            int query = 0;
            for(i = 0; i < count.length; i++)
                if(count[i] > 0)
                    query += imapx[i+1] - imapx[i];
            ans += (long)query * (y - cur_y);
            for(i = x1; i < x2; i++)
                count[i] += flag;
            cur_y = y;
        }
        ans %= 1000000007;
        return (int) ans;
    }
}
// @lc code=end

