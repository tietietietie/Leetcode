/*
 * @lc app=leetcode.cn id=19 lang=java
 *
 * [19] 删除链表的倒数第N个节点
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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int len = 0;
        ListNode pre = head;
        while(pre != null){
            len++;
            pre = pre.next;
        }
        if(len == n){
            head = head.next;
            return head;
        }
        ListNode target = head;
        for(int i = 1; i < len-n; i++){
            target = target.next;
        }
        target.next = (target.next).next;
        return head;
    }
}
// @lc code=end

