/*
 * @lc app=leetcode.cn id=77 lang=java
 *
 * [77] 组合
 */

// @lc code=start
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        if(n <= 0 || k > n) return ans;
        ArrayList<Integer> path = new ArrayList<>();
        for(int i = 1; i <= n; i++)
            dfs(i,n,k,path,ans);
        return ans;
    }

    private void dfs(int l, int r, int k,ArrayList<Integer> path, List<List<Integer>> ans){
        if(path.size()+r-l+1 < k) return;
        if(path.size() == k-1){
            path.add(l);
            ans.add(new ArrayList(path));
            path.remove(path.size()-1);
            return;
        }
        path.add(l);
        for(int j = l+1; j <= r; j++)
            dfs(j,r,k,path,ans);
        path.remove(path.size()-1);
    }
}
// @lc code=end

