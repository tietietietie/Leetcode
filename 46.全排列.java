/*
 * @lc app=leetcode.cn id=46 lang=java
 *
 * [46] 全排列
 */

// @lc code=start
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if(nums.length == 0) return ans;
        LinkedList<Integer> curPermute = new LinkedList<>();
        boolean[] visited = new boolean[nums.length];
        for(int i = 0; i < nums.length; i++){
            dfs(nums,i,curPermute,visited,ans);
        }
        return ans;
    }
    private void dfs(int[] nums, int curPos, LinkedList<Integer> curPermute, boolean[] visited, List<List<Integer>> ans){
        if(curPermute.size() == nums.length-1){
            curPermute.add(nums[curPos]);
            ans.add((LinkedList<Integer>)curPermute.clone());
            curPermute.removeLast();
            return;
        }
        visited[curPos] = true;
        curPermute.add(nums[curPos]);
        for(int i = 0; i < nums.length; i++){
            if(!visited[i]){
                dfs(nums,i,curPermute,visited,ans);
            }
        }
        visited[curPos] = false;
        curPermute.removeLast();
    }
}
// @lc code=end

