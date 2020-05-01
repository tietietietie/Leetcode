/*
 * @lc app=leetcode.cn id=78 lang=java
 *
 * [78] 子集
 */

// @lc code=start
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        ArrayList<Integer> path = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        dfs(nums,0,n,path,ans);
        return ans;
    }

    private void dfs(int[] nums, int i, int n, ArrayList<Integer> path, List<List<Integer>> ans){
        ans.add(new ArrayList<>(path));
        for(int j = i; j < n; j++){
            path.add(nums[j]);
            dfs(nums,j+1,n,path,ans);
            path.remove(path.size()-1);
        }
    }
}
// @lc code=end

