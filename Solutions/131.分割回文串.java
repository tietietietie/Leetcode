/*
 * @lc app=leetcode.cn id=131 lang=java
 *
 * [131] 分割回文串
 */

// @lc code=start
class Solution {
    public List<List<String>> partition(String s) {
        ArrayList<String> path = new ArrayList<>();
        List<List<String>> ans = new ArrayList<>();
        if(s.equals("")) return ans;
        int len = s.length();
        dfs(s,0,len,path,ans);
        return ans;
    }

    private void dfs(String s, int i, int len, ArrayList<String> path, List<List<String>> ans){
        if(i == len){
            ans.add(new ArrayList<>(path));
            return;
        }
        for(int j = i; j < len; j++){
            if(isPalindrome(s,i,j)){
                path.add(s.substring(i,j+1));
                dfs(s,j+1,len,path,ans);
                path.remove(path.size()-1);
            }
        }
    }

    private boolean isPalindrome(String s, int i, int j){
        while(i < j && s.charAt(i) == s.charAt(j)){
            i++;
            j--;
        }
        return i >= j;
    }
}
// @lc code=end

