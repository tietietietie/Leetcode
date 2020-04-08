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
        ArrayList<Integer>[] buckets = new ArrayList[nums.length+1];
        for(int num : map.keySet()){
            if(buckets[map.get(num)] == null)
                buckets[map.get(num)] = new ArrayList<>();
                buckets[map.get(num)].add(num);
        }
        List<Integer> ans = new ArrayList<>();
        for(int i = buckets.length-1; i >= 1; i--){
            if(buckets[i] == null) continue;
            else if(ans.size() < k) ans.addAll(buckets[i]);
            else break;
        }
        return ans;
    }
}
// @lc code=end

