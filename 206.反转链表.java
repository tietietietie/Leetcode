/*
 * @lc app=leetcode.cn id=206 lang=java
 *
 * [206] 反转链表
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
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null)
            return head;
        else{
            ListNode p = reverseList(head.next);
            head.next.next = head;
            head.next = null;
            //reverseList(head.next).next = head;
            return p;
        }        
    }
}
// @lc code=end

