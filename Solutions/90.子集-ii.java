/*
 * @lc app=leetcode.cn id=90 lang=java
 *
 * [90] 子集 II
 */

// @lc code=start
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        ArrayList<Integer> path = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        Arrays.sort(nums);
        dfs(nums,0,n,path,ans);
        return ans;
    }

    private void dfs(int[] nums, int i, int n, ArrayList<Integer> path, List<List<Integer>> ans){
        ans.add(new ArrayList<>(path));
        for(int j = i; j < n; j++){
            if(j != i && nums[j-1] == nums[j])
                continue;
            path.add(nums[j]);
            dfs(nums,j+1,n,path,ans);
            path.remove(path.size()-1);
        }
    }
}
// @lc code=end

