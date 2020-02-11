/*
 * @lc app=leetcode.cn id=677 lang=java
 *
 * [677] 键值映射
 */

// @lc code=start
class MapSum {
    private static int R = 256;
    private Node root;
    private int sum;
    private class Node{
        private int val;
        private Node[] next = new Node[R];
    }

    /** Initialize your data structure here. */
    public MapSum() {
        root = new Node();
    }
    
    public void insert(String key, int val) {
        insert(key,val,root,0);        
    }

    private Node insert(String key, int val, Node node, int d){
        if(node == null) node = new Node();
        if(d == key.length()){
            node.val = val;
            return node;
        }
        char c = key.charAt(d);
        node.next[c] = insert(key,val,node.next[c],d+1);
        return node; 
    }
    
    public int sum(String prefix) {
        Node x = search(root,prefix,0);
        if(x == null) return 0;
        this.sum = x.val;
        sum(x);
        return this.sum;
    }

    private Node search(Node node,String prefix,int d){
        if(node == null) return null;
        if(d == prefix.length()) return node;
        char c = prefix.charAt(d);
        return search(node.next[c],prefix,d+1);
    }

    private void sum(Node node){
        
    }
}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */
// @lc code=end

