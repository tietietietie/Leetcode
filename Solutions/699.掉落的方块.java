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
        Tree tree = new Tree(imapx.length);
        List<Integer> ans = new ArrayList<>();
        int maxHeight = 0;
        for(int[] position : positions){
            int x1 = xmapi.get(position[0]), x2 = xmapi.get(position[0]+position[1]), height = position[1];
            int h = tree.query(x1,x2) + height;
            tree.update(x1,x2,h);
            maxHeight = Math.max(h,maxHeight);
            ans.add(maxHeight);
        }
        return ans;
    }

    private class Tree{
        public int[] tree;
        private int n;
        
        public Tree(int n){
            this.n = n;
            tree = new int[4 * n];
        }

        public void update(int l, int r, int height){
            update(tree,0,0,n-1,l,r,height);
        }

        private void update(int[] tree, int idx, int start, int end, int l, int r, int height){
            if(start >= end || start >= r || end <= l) return;
            tree[idx] = Math.max(tree[idx],height);
            if(start != end-1){
                int mid = (start+end) / 2;
                int leftNode  = 2*idx + 1;
                int rightNode = 2*idx + 2;
                update(tree,leftNode,start,mid,l,r,height);
                update(tree,rightNode,mid,end,l,r,height);
            }
        }

        public int query(int l, int r){
            return query(tree,0,0,n-1,l,r);
        }

        private int query(int[] tree, int idx, int start, int end, int l, int r){
            if(start >= end || start >= r || end <= l) return 0;
            if(start >= l && end <= r) return tree[idx];
            int mid = (start+end) / 2;
            int leftNode  = 2*idx + 1;
            int rightNode = 2*idx + 2;
            int leftHeight  = query(tree,leftNode,start,mid,l,r);
            int rightHeight = query(tree,rightNode,mid,end,l,r);
            return Math.max(leftHeight,rightHeight);
        }
    }
}
// @lc code=end

