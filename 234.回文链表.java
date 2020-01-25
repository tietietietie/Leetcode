/*
 * @lc app=leetcode.cn id=234 lang=java
 *
 * [234] 回文链表
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
    public boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null)
            return true;
        //折半并反转后半部分链表（快慢指针法）
        ListNode slow = head, fast = head;
        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode halfRHead = reverse(slow.next);
        //比较两链表
        ListNode cmp1 = halfRHead, cmp2 = head;
        while(cmp1 != null){
            if(cmp1.val != cmp2.val)
                return false;
            cmp1 = cmp1.next;
            cmp2 = cmp2.next;
        }
        return true;
    }

    private ListNode reverse(ListNode head){
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

