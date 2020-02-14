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
    private ListNode dummy;
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) return head;
        dummy = new ListNode(0);
        sort(head,null);
        return dummy.next;
    }

    private void sort(ListNode head, ListNode tail){
        if(head == tail || head.next == tail) return;
        ListNode mid = findMid(head,tail);
        sort(head,mid);
        sort(mid,tail);
        merge(head,mid,tail);
    }

    private ListNode findMid(ListNode head, ListNode tail){
        ListNode fast = head, slow = head;
        while(fast != tail && fast.next != tail){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    private void merge(ListNode head, ListNode mid, ListNode tail){
        ListNode helper = new ListNode(0);
        ListNode cur1 = head, cur2 = mid, cur = helper;
        while(cur1.next != mid){
            cur1 = cur1.next;
        }
        cur1.next = tail;
        cur1 = head;
        while(cur1 != tail && cur2 != tail){
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
        if(cur1 == tail){
            cur.next = cur2;
        }

        if(cur2 == tail){
            cur.next = cur1;
        }
        dummy.next = helper.next;
    }
}
// @lc code=end

