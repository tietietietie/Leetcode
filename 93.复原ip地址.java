/*
 * @lc app=leetcode.cn id=93 lang=java
 *
 * [93] 复原IP地址
 */

// @lc code=start
class Solution {
    public List<String> restoreIpAddresses(String s) {
        ArrayList<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int step = 0, curPos = 0, len = s.length();
        if(s.equals("")) return ans;
        dfs(s,len,curPos,step,sb,ans);
        return ans;
    }

    private void dfs(String s, int len, int curPos, int step, StringBuilder sb, ArrayList<String> ans){
        if(step == 4 || curPos == len){
            if(step == 4 && curPos == len)
                ans.add(sb.substring(0,sb.length()-1));
            return;
        }
        if(s.charAt(curPos) == '0'){
            sb.append("0.");
            dfs(s,len,curPos+1,step+1,sb,ans);
            sb.delete(sb.length()-2,sb.length());
            return;
        }
        if(len-curPos >= 3 && Integer.valueOf(s.substring(curPos,curPos+3)) <= 255){
            sb.append(s.substring(curPos,curPos+3));
            sb.append('.');
            dfs(s,len,curPos+3,step+1,sb,ans);
            sb.delete(sb.length()-4,sb.length());
        }
        if(len-curPos >= 2){
            sb.append(s.substring(curPos,curPos+2));
            sb.append('.');
            dfs(s,len,curPos+2,step+1,sb,ans);
            sb.delete(sb.length()-3,sb.length());
        }
        if(len-curPos >= 1){
            sb.append(s.substring(curPos,curPos+1));
            sb.append('.');
            dfs(s,len,curPos+1,step+1,sb,ans);
            sb.delete(sb.length()-2,sb.length());
        }
    }
}
// @lc code=end

