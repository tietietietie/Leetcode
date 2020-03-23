/*
 * @lc app=leetcode.cn id=684 lang=java
 *
 * [684] 冗余连接
 */

// @lc code=start
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        UF uf = new UF(n);
        int[] ans = new int[]{-1,-1};
        for(int[] edge : edges){
            if(uf.connected(edge[0],edge[1]))
                ans = edge;
            else
                uf.union(edge[0],edge[1]);
        }
        return ans;
    }

    private class UF{
        private int[] id;
        public UF(int n){
            id = new int[n+1];
            for(int i = 1; i <= n; i++)
                id[i] = i;
        }
        public int find(int p){
            return id[p];
        }
        public void union(int p, int q){
            int pID = id[p];
            int qID = id[q];
            for(int i = 1; i < id.length; i++)
                if(id[i] == qID)
                    id[i] = pID;
        }
        public boolean connected(int p, int q){
            return id[p] == id[q];
        }
    }
}
// @lc code=end

