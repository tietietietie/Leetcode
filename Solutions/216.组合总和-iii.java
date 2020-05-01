/*
 * @lc app=leetcode.cn id=216 lang=java
 *
 * [216] 组合总和 III
 */

// @lc code=start
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ans = new ArrayList<>();
        if(k > n) return ans;
        Stack<Integer> path = new Stack<>();
        dfs(1,n,k,path,ans);
        return ans;
    }

    private void dfs(int start, int remainder, int k, Stack<Integer> path, List<List<Integer>> ans){
        if(k == 0 || remainder == 0){
            if(k == 0 && remainder == 0)
                ans.add(new ArrayList(path));
            return;
        }
        for(int i = start; i <= 9 && i <= remainder; i++){
            path.push(i);
            dfs(i+1,remainder-i,k-1,path,ans);
            path.pop();
        }
    }
}
// @lc code=end

