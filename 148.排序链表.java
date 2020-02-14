/*
 * @lc app=leetcode.cn id=148 lang=java
 *
 * [148] 排序链表
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

    public ListNode sortList(ListNode head) {
        return sort(head);
    }

    private ListNode sort(ListNode head){
        if(head == null || head.next == null) return head;
        ListNode mid = findMid(head);
        ListNode head2 = mid.next;
        mid.next = null;
        head = sort(head);
        head2 = sort(head2);
        return merge(head,head2);
    }

    private ListNode findMid(ListNode head){
        ListNode fast = head.next, slow = head;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    private ListNode merge(ListNode head1, ListNode head2){
        ListNode helper = new ListNode(0);
        ListNode cur1 = head1, cur2 = head2, cur = helper;
        while(cur1 != null && cur2 != null){
            if(cur1.val < cur2.val){
                cur.next = cur1;
                cur1 = cur1.next;
                cur = cur.next;
            }
            else{
                cur.next = cur2;
                cur2 = cur2.next;
                cur = cur.next;
            }
        }
        if(cur1 == null){
            cur.next = cur2;
        }
        if(cur2 == null){
            cur.next = cur1;
        }
        return helper.next; 
    }
}
// @lc code=end

