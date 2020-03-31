/*
 * @lc app=leetcode.cn id=524 lang=java
 *
 * [524] 通过删除字母匹配到字典里最长单词
 */

// @lc code=start
class Solution {
    public String findLongestWord(String s, List<String> d) {
        ArrayList<String> ans = new ArrayList<>();
        for(String word : d)
            if(isSubstring(s,word))
                ans.add(word);
        int length = 0;
        String longestAns = "";
        for(String word : ans)
            if(word.length() > length){
                length = word.length();
                longestAns = word;
            }else if(word.length() == length){
                if(word.compareTo(longestAns) < 0)
                    longestAns = word;
            }
        return longestAns;
    }

    private boolean isSubstring(String s, String word){
        int i = 0, j = 0, length1 = s.length(), length2 = word.length();
        while(i < length1 && j < length2){
            if(s.charAt(i) == word.charAt(j))
                j++;
            i++;
        }
        return j == length2;
    }
}
// @lc code=end

