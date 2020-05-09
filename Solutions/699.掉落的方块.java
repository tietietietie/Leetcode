/*
 * @lc app=leetcode.cn id=699 lang=java
 *
 * [699] 掉落的方块
 */

// @lc code=start
class Solution {
    public List<Integer> fallingSquares(int[][] positions) {
        HashSet<Integer> xSet = new HashSet<>();
        for(int[] position : positions){
            xSet.add(position[0]);
            xSet.add(position[0]+position[1]);
        }
        Integer[] imapx = (Integer[]) xSet.toArray(new Integer[0]);
        Arrays.sort(imapx);
        HashMap<Integer,Integer> xmapi = new HashMap<>();
        for(int i = 0; i < imapx.length; i++)
            xmapi.put(imapx[i],i);
        Node root = new Node(0,imapx.length-1,0);
        List<Integer> ans = new ArrayList<>();
        for(int[] position : positions){
            int x1 = xmapi.get(position[0]), x2 = xmapi.get(position[0]+position[1]), height = position[1];
            root.update(x1,x2,height);
            ans.add(root.maxHeight);
        }
        return ans;
    }

    private class Node{
        private int start;
        private int end;
        private Node left;
        private Node right;
        public int maxHeight;
        
        public Node(int start, int end, int height){
            this.start = start;
            this.end = end;
            left = null;
            right = null;
            maxHeight = height;
        }

        private void update(int l, int r, int height){
            if(l >= r) return;
            if(start == l && end == r)
                maxHeight += height;
            else{
                int mid = (start+end) / 2;
                if(left  == null) left  = new Node(start,mid,maxHeight);
                if(right == null) right = new Node(mid,end,maxHeight);
                left.update(l,Math.min(mid,r),height);
                right.update(Math.max(mid,l),r,height);
                maxHeight = Math.max(left.maxHeight,right.maxHeight);
            }
        }
    }
}
// @lc code=end

