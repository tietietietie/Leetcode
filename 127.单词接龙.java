/*
 * @lc app=leetcode.cn id=127 lang=java
 *
 * [127] 单词接龙
 */

// @lc code=start
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int m = beginWord.length();
        HashSet<String> dict = new HashSet<>(wordList);
        HashSet<String> beginLevel = new HashSet<>();
        HashSet<String> endLevel = new HashSet<>();
        HashSet<String> temp = new HashSet<>();
        if(!dict.contains(endWord)) return 0;
        dict.remove(endWord);
        if(dict.contains(beginWord))
            dict.remove(beginWord);
        beginLevel.add(beginWord);
        endLevel.add(endWord);
        int ans = 2;
        while(!beginLevel.isEmpty() && !endLevel.isEmpty()){
            if(beginLevel.size() > endLevel.size()){
                temp = beginLevel;
                beginLevel = endLevel;
                endLevel = temp;
            }
            HashSet<String> nextLevel = new HashSet<>();
            for(String curStr : beginLevel){
                char[] strChars = curStr.toCharArray();
                for(int j = 0; j < m; j++){
                    char ch = strChars[j];
                    for(char c = 'a'; c <= 'z'; c++){
                        if(ch == c) continue;
                        strChars[j] = c;
                        String str = new String(strChars);
                        if(endLevel.contains(str)) return ans;
                        if(dict.contains(str)){
                            nextLevel.add(str);
                            dict.remove(str);
                        }
                    }
                    strChars[j] = ch;
                }
            }
            beginLevel = nextLevel;
            ans++;
        }
        return 0;
    }
}
// @lc code=end

