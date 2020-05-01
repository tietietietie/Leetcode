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
        dfs(1,n,k,path,ans);
        return ans;
    }

    private void dfs(int l, int r, int k, ArrayList<Integer> path, List<List<Integer>> ans){
        if(k == 0){
            ans.add(new ArrayList(path));
            return;
        }
        for(int i = l; i <= r && r-i+1 >= k; i++){
            path.add(i);
            dfs(i+1,r,k-1,path,ans);
            path.remove(path.size()-1);
        }
    }
}
// @lc code=end

