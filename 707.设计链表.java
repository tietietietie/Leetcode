/*
 * @lc app=leetcode.cn id=707 lang=java
 *
 * [707] 设计链表
 */

// @lc code=start
public class ListNode{
    int val;
    ListNode next;
    ListNode(int x) {val = x;}
}

class MyLinkedList {
    private ListNode dummy;
    /** Initialize your data structure here. */
    public MyLinkedList() {
        dummy = new ListNode(-1);
    }
    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        int cnt = -1;
        ListNode cur = dummy;
        while(cnt < index && cur != null){
            cnt++;
            cur = cur.next;
        }
        return cur == null?-1:cur.val;
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        addAtIndex(0,val);
    }
    
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        addAtIndex(2001,val);
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        ListNode pre = dummy;
        int cnt = -1;
        while(cnt < index-1 && pre.next != null){
            cnt++;
            pre = pre.next;
        }
        ListNode temp = pre.next;
        ListNode head = new ListNode(val);
        pre.next = head;
        head.next = temp;
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if(index < 0) return;
        ListNode pre = dummy;
        int cnt = -1;
        while(cnt < index-1 && pre.next != null){
            cnt++;
            pre = pre.next;
        }
        if(pre.next != null){
            pre.next = pre.next.next;
        }
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
// @lc code=end

