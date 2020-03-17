/*
 * @lc app=leetcode.cn id=20 lang=java
 *
 * [20] 有效的括号
 */

// @lc code=start
class Solution {
    public boolean isValid(String s) {
        char[] chars = s.toCharArray();
        HashMap<Character,Character> map = new HashMap<>();
        map.put(')','(');
        map.put(']','[');
        map.put('}','{');
        Stack<Character> stack = new Stack<>();
        for(char i : chars){
            if(i == '(' || i == '[' || i == '{')
                stack.push(i);
            else if(stack.isEmpty() || stack.peek() != map.get(i)){
                return false;
            }else{
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
}
// @lc code=end

