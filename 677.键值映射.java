/*
 * @lc app=leetcode.cn id=677 lang=java
 *
 * [677] 键值映射
 */

// @lc code=start
class MapSum {

    Map<String,Integer> map;
    public MapSum() {
        this.map = new HashMap<>();
    }

    public void insert(String key, int val) {
        map.put(key,val);
    }


    public int sum(String prefix) {
        int sum = 0;
        for(String key : map.keySet()){
            if(key.startsWith(prefix))
                sum += map.get(key);
        }
        return sum;
    }

}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */
// @lc code=end

