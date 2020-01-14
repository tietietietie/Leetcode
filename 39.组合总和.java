/*
 * @lc app=leetcode.cn id=39 lang=java
 *
 * [39] 组合总和
 */

// @lc code=start
class Solution {
    private int[] candidates;
    private int target;
    private List<List<Integer>> ans;
    private void addNumber(int start, int residue, Stack<Integer> temp){
        if(residue == 0){
            ans.add(new ArrayList<>(temp));
            return;
        }
        for(int i = start; i < candidates.length && residue - candidates[i] >= 0; i++){
            temp.push(candidates[i]);
            addNumber(i,residue+candidates[i],temp);
            temp.pop();
        }
    }
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if(candidates.length == 0){
            return ans;
        }
        Arrays.sort(candidates);
        this.candidates = candidates;
        this.target = target;
        addNumber(0,target,new Stack<>());
        return ans;
    }
}
// @lc code=end

