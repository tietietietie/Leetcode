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
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode i = head, j = head.next;
        while(j != null){
            i.next = j.next;
            j.next = i;
            i = i.next;
            j = j.next.next;
        }
        if(head.next != null)
            return head.next;
        else
            return head;
    }
}
// @lc code=end

