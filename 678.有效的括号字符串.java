/*
 * @lc app=leetcode.cn id=678 lang=java
 *
 * [678] 有效的括号字符串
 */

// @lc code=start
class Solution {
    public boolean checkValidString(String s) {
        Stack<Integer> left = new Stack<>();
        Stack<Integer> star = new Stack<>();
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '(')
                left.push(i);
            else if(s.charAt(i) == '*')
                star.push(i);
            else{
                if(!left.isEmpty()) left.pop();
                else if(!star.isEmpty()) star.pop();
                else return false;
            }
        }
        if(left.size() > star.size()) return false;
        while(!left.isEmpty() && !star.isEmpty())
            if(left.pop() > star.pop()) return false;            
        return left.isEmpty();
    }
}
// @lc code=end

