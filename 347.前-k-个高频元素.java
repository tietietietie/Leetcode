/*
 * @lc app=leetcode.cn id=347 lang=java
 *
 * [347] 前 K 个高频元素
 */

// @lc code=start
class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int num : nums){
            if(map.get(num) == null)
                map.put(num,1);
            else
                map.put(num,map.get(num)+1);
        }
        PriorityQueue<Pair<Integer,Integer>> pq = new PriorityQueue<>((o1,o2) -> o1.getValue() - o2.getValue());
        for(int num : map.keySet()){
            if(pq.size() < k)
                pq.offer(new Pair<>(num,map.get(num)));
            else if(pq.peek().getValue() < map.get(num)){
                pq.poll();
                pq.offer(new Pair<>(num,map.get(num)));
            }
        }
        List<Integer> ans = new ArrayList<>();
        while(!pq.isEmpty())
            ans.add(pq.poll().getKey());
        return ans;
    }
}
// @lc code=end

