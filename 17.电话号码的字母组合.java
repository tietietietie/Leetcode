/*
 * @lc app=leetcode.cn id=17 lang=java
 *
 * [17] 电话号码的字母组合
 */

// @lc code=start
class Solution {
    public List<String> letterCombinations(String digits) {
        HashMap<Character,Character[]> map = new HashMap<>();
        char[] digitsArr = digits.toCharArray();
        for(char digit : digitsArr)
            if(map.get(digit) == null){
                switch(digit){
                    case '2':
                        map.put('2',new Character[]{'a','b','c'});
                        break;
                    case '3':
                        map.put('3',new Character[]{'d','e','f'});
                        break;
                    case '4':
                        map.put('4',new Character[]{'g','h','i'});
                        break;
                    case '5':
                        map.put('5',new Character[]{'j','k','l'});
                        break;
                    case '6':
                        map.put('6',new Character[]{'m','o','n'});
                        break;
                    case '7':
                        map.put('7',new Character[]{'p','q','r','s'});
                        break;
                    case '8':
                        map.put('8',new Character[]{'t','u','v'});
                        break;
                    case '9':
                        map.put('9',new Character[]{'w','x','y','z'});
                        break;
                }
            }
        int len = digitsArr.length;
        StringBuilder sb = new StringBuilder();
        ArrayList<String> ans = new ArrayList<>();
        if(len == 0) return ans;
        dfs(digitsArr,0,len,sb,ans,map);
        return ans;
    }

    private void dfs(char[] digitsArr, int step, int len, StringBuilder sb, ArrayList<String> ans, HashMap<Character,Character[]> map){
        if(step == len){
            ans.add(sb.toString());
            return;
        }
        char curChar = digitsArr[step];
        for(char c : map.get(curChar)){
            sb.append(c);
            dfs(digitsArr,step+1,len,sb,ans,map);
            sb.deleteCharAt(sb.length()-1);
        }
    }
}
// @lc code=end

