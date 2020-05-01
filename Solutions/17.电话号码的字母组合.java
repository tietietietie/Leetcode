/*
 * @lc app=leetcode.cn id=17 lang=java
 *
 * [17] 电话号码的字母组合
 */

// @lc code=start
class Solution {
    private static String[] keys = new String[]{"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    public List<String> letterCombinations(String digits) {
        char[] digitsArr = digits.toCharArray();
        int len = digitsArr.length;
        StringBuilder sb = new StringBuilder();
        ArrayList<String> ans = new ArrayList<>();
        if(len == 0) return ans;
        dfs(digitsArr,0,len,sb,ans);
        return ans;
    }

    private void dfs(char[] digitsArr, int step, int len, StringBuilder sb, ArrayList<String> ans){
        if(step == len){
            ans.add(sb.toString());
            return;
        }
        char curChar = digitsArr[step];
        char[] letters = keys[curChar-'0'].toCharArray();
        for(char c : letters){
            sb.append(c);
            dfs(digitsArr,step+1,len,sb,ans);
            sb.deleteCharAt(sb.length()-1);
        }
    }
}
// @lc code=end

