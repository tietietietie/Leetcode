/*
 * @lc app=leetcode.cn id=25 lang=java
 *
 * [25] K 个一组翻转链表
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode tail = head;
        for(int i = 0; i < k; i++){
            if(tail == null)
                return head;
            else
                tail = tail.next;
        }
        ListNode newhead = reverse(head,tail);
        head.next = reverseKGroup(tail,k);
        return newhead;
    }    
    private ListNode reverse(ListNode head, ListNode tail) {
        Stack<ListNode> stkNode = new Stack<ListNode>();
        ListNode pre = head;
        while(pre != tail){
            stkNode.add(pre);
            pre = pre.next;
        }
        ListNode newhead = stkNode.peek();
        while(stkNode.size() > 1){
            ListNode i = stkNode.pop();
            i.next = stkNode.peek();
        }
        return newhead;
    }
}
// @lc code=end

