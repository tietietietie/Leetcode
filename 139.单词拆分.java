/*
 * @lc app=leetcode.cn id=139 lang=java
 *
 * [139] 单词拆分
 */

// @lc code=start
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[s.length()];
        HashSet<String> set = new HashSet<>(wordDict);
        queue.add(0);
        while(!queue.isEmpty()){
            int curIdx = queue.poll();
            if(visited[curIdx] == false){
                for(int end = curIdx+1; end <= s.length(); end++)
                    if(set.contains(s.substring(curIdx,end))){
                        if(end == s.length()) return true;
                        else queue.offer(end);
                    }
                visited[curIdx] = true;
            }
        }
        return false;
    }
}
// @lc code=end

