/*
 * @lc app=leetcode.cn id=61 lang=java
 *
 * [61] 旋转链表
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
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null)
            return null;
        int len = 1;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        while(head.next != null){  //head最终停在末尾
            len++;
            head = head.next;
        }
        head.next = dummy.next; //形成了闭环
        k = k%len;
        ListNode pre = dummy.next;
        for(int i = 0; i < len-k-1; i++){
            pre = pre.next;
        }
        dummy.next = pre.next;
        pre.next = null;
        return dummy.next;
    }
}
// @lc code=end

