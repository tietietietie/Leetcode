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
        for(char c : chars){
            if(map.get(c) == null)
                map.put(c,1);
            else
                map.put(c,map.get(c)+1);
        }
        Pair<Character,Integer>[] pairs = new Pair[map.keySet().size()];
        int i = 0;
        for(char c : map.keySet()){
            pairs[i] = new Pair<>(c,map.get(c));
            i++;
        }
        Arrays.sort(pairs,(o1,o2) -> o2.getValue()-o1.getValue());
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < pairs.length; j++){
            int length = pairs[j].getValue();
            char c = pairs[j].getKey();
            for(int k = 0; k < length; k++)
                sb.append(c);
        }
        return sb.toString();
    }
}
// @lc code=end

