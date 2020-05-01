/*
 * @lc app=leetcode.cn id=208 lang=java
 *
 * [208] 实现 Trie (前缀树)
 */

// @lc code=start
class Trie {

    private static int R = 26;
    private Node root;

    private static class Node{
        private boolean isEnd = false;
        private Node[] next = new Node[R];
    }
    /** Initialize your data structure here. */
    public Trie() {
        root = new Node();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        insert(root,word,0);
    }

    private Node insert(Node node, String word, int d){
        if(node == null) node = new Node();
        if(d == word.length()) {
            node.isEnd = true; 
            return node;}
        char c = word.charAt(d);
        node.next[c-'a'] = insert(node.next[c-'a'],word,d+1);
        return node;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node x = search(root,word,0);
        if(x == null) return false;
        return x.isEnd;                
    }

    private Node search(Node node, String word, int d){
        if(d == word.length()) return node;
        char c = word.charAt(d);
        return node.next[c-'a'] == null?null:search(node.next[c-'a'],word,d+1);
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node x = search(root,prefix,0);
        if(x == null) return false;
        return true;        
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
// @lc code=end

