/*
 * @lc app=leetcode.cn id=328 lang=java
 *
 * [328] 奇偶链表
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
    public ListNode oddEvenList(ListNode head) {
        if(head == null)
            return null;
        ListNode evenHead = head.next;
        ListNode oddPre = head;
        ListNode evenPre = head.next;
        while(evenPre != null && evenPre.next != null){
            oddPre.next = oddPre.next.next;
            oddPre = oddPre.next;
            evenPre.next = evenPre.next.next;
            evenPre = evenPre.next;
        }
        oddPre.next = evenHead;
        return head; 
    }
}
// @lc code=end

