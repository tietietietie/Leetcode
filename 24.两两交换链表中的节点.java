/*
 * @lc app=leetcode.cn id=24 lang=java
 *
 * [24] 两两交换链表中的节点
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(-1, head);
        ListNode pre   = dummy;
        while(pre.next != null && pre.next.next != null) {
            ListNode cur = pre.next, next = pre.next.next;
            pre.next = next;
            cur.next = next.next;
            next.next = cur;
            pre = cur;
        }
        return dummy.next;
    }
}
// @lc code=end

