/*
 * @lc app=leetcode.cn id=127 lang=java
 *
 * [127] 单词接龙
 */

// @lc code=start
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        String[] wordArr = wordList.toArray(new String[wordList.size()]);
        boolean flag = true;
        boolean[] visited = new boolean[wordArr.length];
        for(int i = 0; i < wordArr.length; i++){
            if(endWord.equals(wordArr[i])){
                flag = false;
            }
            if(beginWord.equals(wordArr[i])){
                visited[i] = true;
            }
        }
        if(flag) return 0;
        LinkedList<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int ans = 1;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i = 0; i < size; i++){
                String curStr = queue.poll();
                if(curStr.equals(endWord)) return ans;
                for(int j = 0; j < wordArr.length; j++)
                    if(!visited[j] && diffOneChar(curStr,wordArr[j])){
                        queue.offer(wordArr[j]);
                        visited[j] = true;
                    }
            }
            ans++;
        }
        return 0;
    }

    private boolean diffOneChar(String s1, String s2){
        int len = s1.length(), diff = 0;
        for(int i = 0; i < len; i++){
            if(s1.charAt(i) != s2.charAt(i))
                diff++;
            if(diff == 2) return false;
        }
        return diff == 1;
    }
}
// @lc code=end

