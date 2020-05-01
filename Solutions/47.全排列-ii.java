/*
 * @lc app=leetcode.cn id=47 lang=java
 *
 * [47] 全排列 II
 */

// @lc code=start
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if(nums.length == 0) return ans;
        Arrays.sort(nums);
        boolean[] visited = new boolean[nums.length];
        ArrayList<Integer> path = new ArrayList<>();
        int lastVisit = nums[0]-1;
        for(int i = 0; i < nums.length; i++){
            if(lastVisit != nums[i]){
                dfs(nums,i,visited,path,ans);
                lastVisit = nums[i];
            }
        }
        return ans;
    }

    private void dfs(int[] nums, int i, boolean[] visited, ArrayList<Integer> path, List<List<Integer>> ans){
        if(path.size() == nums.length-1){
            path.add(nums[i]);
            ans.add(new ArrayList(path));
            path.remove(path.size()-1);
        }
        path.add(nums[i]);
        visited[i] = true;
        boolean firstVisit = true;
        int lastVisit = 0;
        for(int j = 0; j < nums.length; j++){
            if(!visited[j] && firstVisit){
                firstVisit = false;
                dfs(nums,j,visited,path,ans);
                lastVisit = nums[j];
            }
            if(!visited[j] && !firstVisit && nums[j] != lastVisit){
                dfs(nums,j,visited,path,ans);
                lastVisit = nums[j];
            }
        }
        visited[i] = false;
        path.remove(path.size()-1);
        
    }
}
// @lc code=end

