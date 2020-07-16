import java.util.*;

public class Solution
{

    Node head = new Node(null, null, -1);;


    public boolean search(int target) {
        for(Node cur = head; cur != null; cur = cur.down){
            while(cur.right != null && cur.right.val < target) cur = cur.right;
            if(cur.right != null && cur.right.val == target) return true;
        }
        return false;
    }

    public void add(int num) {
        Stack<Node> stack = new Stack<>();
        for(Node cur = head; cur != null; cur = cur.down) {
            while(cur.right != null && cur.right.val < num) cur = cur.right;
            stack.push(cur);
        }
        Random rand = new Random();
        boolean randomInsert = true;
        Node downNode = null;
        while(randomInsert && !stack.isEmpty()) {
            Node insertNode = stack.pop();
            insertNode.right = new Node(insertNode.right, downNode, num);
            downNode = insertNode.right;
            randomInsert = (rand.nextInt() & 1) == 0;
        }
        if(randomInsert) head = new Node(new Node(null, downNode, num), head, -1);
    }

    public boolean erase(int num) {
        boolean erased = false;
        for(Node cur = head; cur != null; cur = cur.down) {
            while(cur.right != null && cur.right.val < num) cur = cur.right;
            if(cur.right != null && cur.right.val == num) {
                erased = true;
                cur.right = cur.right.right;
            }
        }
        return erased;
    }

    private class Node {
        Node right;
        Node down;
        int val;

        public Node(Node right, Node dowm, int val) {
            this.right = right;
            this.down = down;
            this.val = val;
        }
    }
    public static void main(String[] args) {
        Solution s = new Solution();
        s.add(1);
        s.add(2);
        s.add(3);
        s.search(0);
        s.add(4);
        s.search(1);
        s.erase(0);
        s.erase(1);
        s.search(1);
    }
}