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
        Node active = new Node(0,imapx.length,imapx);
        int cur_y = events[0][0];
        long ans = 0;
        for(int[] event : events){
            int y = event[0], flag = event[1], x1 = xmapi.get(event[2]), x2 = xmapi.get(event[3]);
            int query = active.getTotal();
            ans += (long)query * (y - cur_y);
            active.update(x1,x2,flag);
            cur_y = y;
        }
        ans %= 1000000007;
        return (int) ans;
    }

    private class Node{
        private int start;
        private int end;
        private Integer[] imapx;
        private int count;
        private int total;
        private Node left;
        private Node right;
        public Node(int start, int end, Integer[] imapx){
            this.start = start;
            this.end = end;
            this.imapx = imapx;
            this.count = 0;
            this.total = 0;
            this.left = null;
            this.right = null;
        }
        public int getTotal(){
            return this.total;
        }
        public void update(int l, int r, int flag){
            if(l >= r) return;
            if(l == start && r == end)
                count += flag;
            else{
                int mid = start + (end - start) / 2;
                if(left == null) left = new Node(start,mid,imapx);
                if(right == null) right = new Node(mid,end,imapx);
                left.update(l,Math.min(mid,r),flag);
                right.update(Math.max(l,mid),r,flag);
            }
            if(count > 0) total = imapx[end] - imapx[start];
            else total = (left == null ? 0 : left.getTotal()) + (right == null ? 0 : right.getTotal());
        }
    }
}
// @lc code=end

