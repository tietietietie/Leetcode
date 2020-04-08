/*
 * @lc app=leetcode.cn id=451 lang=java
 *
 * [451] 根据字符出现频率排序
 */

// @lc code=start
class Solution {
    public String frequencySort(String s) {
        HashMap<Character,Integer> map = new HashMap<>();
        char[] chars = s.toCharArray();
        for(char c : chars)
            map.put(c,map.getOrDefault(c,0)+1);
        List<Character>[] buckets = new ArrayList[s.length()+1];
        for(char c : map.keySet()){
            if(buckets[map.get(c)] == null)
                buckets[map.get(c)] = new ArrayList<>();
            buckets[map.get(c)].add(c);
        }
        StringBuilder sb = new StringBuilder();
        for(int i = buckets.length-1; i >= 1; i--){
            if(buckets[i] == null) continue;
            for(char c : buckets[i])
                for(int j = 0; j < i; j++)
                    sb.append(c);
        }
        return sb.toString();
    }
}
// @lc code=end

