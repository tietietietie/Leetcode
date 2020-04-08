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
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1,o2) -> map.get(o1)-map.get(o2));
        for(int num : map.keySet()){
            pq.offer(num);
            if(pq.size() > k)
                pq.poll();
        }
        List<Integer> ans = new ArrayList<>();
        while(!pq.isEmpty())
            ans.add(pq.poll());
        return ans;
    }
}
// @lc code=end

