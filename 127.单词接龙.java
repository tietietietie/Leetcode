/*
 * @lc app=leetcode.cn id=127 lang=java
 *
 * [127] 单词接龙
 */

// @lc code=start
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int m = beginWord.length();
        HashSet<String> set = new HashSet<>(wordList);
        if(!set.contains(endWord)) return 0;
        if(set.contains(beginWord))
            set.remove(beginWord);
        LinkedList<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int ans = 1;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i = 0; i < size; i++){
                String curStr = queue.poll();
                if(curStr.equals(endWord)) return ans;
                char[] strChars = curStr.toCharArray();
                for(int j = 0; j < m; j++){
                    char ch = strChars[j];
                    for(char c = 'a'; c <= 'z'; c++){
                        if(ch == c) continue;
                        strChars[j] = c;
                        String str = new String(strChars);
                        if(set.contains(str)){
                            queue.offer(str);
                            set.remove(str);
                        }
                    }
                    strChars[j] = ch;
                }
            }
            ans++;
        }
        return 0;
    }
}
// @lc code=end

