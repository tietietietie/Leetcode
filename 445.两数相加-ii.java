/*
 * @lc app=leetcode.cn id=445 lang=java
 *
 * [445] 两数相加 II
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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverse(l1);
        l2 = reverse(l2);
        ListNode dummy = new ListNode(-1);
        ListNode pre = dummy;
        int count = 0;
        while(l1 != null && l2 != null){
            pre.next = new ListNode((l1.val+l2.val+count)%10);
            count = (l1.val+l2.val + count)/10;
            l1 = l1.next;
            l2 = l2.next;
            pre = pre.next;
        }
        while(l1 != null){
            pre.next = new ListNode((l1.val+count)%10);
            count = (l1.val+count)/10;
            l1 = l1.next;
            pre = pre.next;
        }
        while(l2 != null){
            pre.next = new ListNode((l2.val+count)%10);
            count = (l2.val+count)/10;
            l2 = l2.next;
            pre = pre.next;            
        }
        if(count == 1){
            pre.next = new ListNode(1);
            pre = pre.next;
        }
        return reverse(dummy.next);    
    }

    public ListNode reverse(ListNode head){
        ListNode pre = null;
        ListNode next = null;
        while(head != null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }
}
// @lc code=end

