/*
 * @lc app=leetcode.cn id=547 lang=java
 *
 * [547] 朋友圈
 */

// @lc code=start
class Solution {
    public int findCircleNum(int[][] M) {
        int m = M.length;
        UF uf = new UF(m);
        for(int i = 0; i < m; i++)
            for(int j = i+1; j < m; j++)
                if(M[i][j] == 1)
                    uf.union(i,j);
        return uf.count();
    }

    private class UF{
        private int[] parent;
        private int[] size;
        private int count;
        public UF(int n){
            parent = new int[n];
            size = new int[n];
            count = n;
            for(int i = 0; i < n; i++){
                parent[i] = i;
                size[i] = 1;
            }
        }
        public int find(int x){
            if(parent[x] == x)
                return x;
            return find(parent[x]);
        }
        public void union(int x, int y){
            int xRoot = find(x);
            int yRoot = find(y);
            if(xRoot == yRoot) return;
            if(size[xRoot] > size[yRoot]){
                parent[yRoot] = xRoot;
                size[xRoot] += size[yRoot];
            }else{
                parent[xRoot] = yRoot;
                size[yRoot] += size[xRoot];
            }
            count--;
        }
        public boolean connected(int x, int y){
            int xRoot = find(x);
            int yRoot = find(y);
            return xRoot == yRoot;
        }
        public int count(){
            return count;
        }
    }
}
// @lc code=end

