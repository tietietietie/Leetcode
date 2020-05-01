/*
 * @lc app=leetcode.cn id=677 lang=java
 *
 * [677] 键值映射
 */

// @lc code=start
class MapSum {
    private class TireNode{
        Map<Character,TireNode> next = new HashMap<>();
        int sum;
    }

    Map<String,Integer> map;
    TireNode root;
    
    public MapSum() {
        this.map = new HashMap<>();
        this.root = new TireNode();
    }

    public void insert(String key, int val) {
        int delte = val - map.getOrDefault(key,0);
        map.put(key,val);
        TireNode cur = root;
        cur.sum += delte;
        for(char c : key.toCharArray()){
            cur.next.putIfAbsent(c,new TireNode());
            cur = cur.next.get(c);
            cur.sum += delte;
        }
    }


    public int sum(String prefix) {
        TireNode cur = root;
        for(char c : prefix.toCharArray()){
            cur = cur.next.get(c);
            if(cur == null) return 0;
        }
        return cur.sum;
    }

}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */
// @lc code=end

