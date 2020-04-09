/*
 * @lc app=leetcode.cn id=241 lang=java
 *
 * [241] 为运算表达式设计优先级
 */

// @lc code=start
class Solution {
    public HashMap<String,List<Integer>> map = new HashMap<>();
    public List<Integer> diffWaysToCompute(String input) {
        if(map.get(input) != null) return map.get(input);
        List<Integer> res = new ArrayList<>();
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*'){
                List<Integer> leftRes = diffWaysToCompute(input.substring(0,i));
                List<Integer> rightRes = diffWaysToCompute(input.substring(i+1));
                for(int left : leftRes)
                    for(int right : rightRes)
                        switch(input.charAt(i)){
                            case '+':
                                res.add(left+right);
                                break;
                            case '-':
                                res.add(left-right);
                                break;
                            case '*':
                                res.add(left*right);
                                break;
                        }
            }
        }
        if(res.isEmpty())
            res.add(Integer.valueOf(input));
        map.put(input,res);
        return res;
    }
}
// @lc code=end

