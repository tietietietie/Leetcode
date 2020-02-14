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
        if(head == null || head.next == null) return head;
        //快慢指针法确定中间点位置，并隔断
        ListNode fast = head.next, slow = head;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        ListNode head2 = slow.next;
        slow.next = null;
        //先递归排序前后两链表
        head = sortList(head);
        head2 = sortList(head2);
        //合并链表
        ListNode helper = new ListNode(-1);
        ListNode cur = helper;
        while(head != null && head2 != null){
            if(head.val < head2.val){
                cur.next = head;
                head = head.next;
            }else{
                cur.next = head2;
                head2 = head2.next;
            }
            cur = cur.next;
        }
        cur.next = (head == null ? head2 : head);
        return helper.next;
    }
}
// @lc code=end

