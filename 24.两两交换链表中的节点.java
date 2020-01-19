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
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode prevNode = dummy;
        ListNode firstNode,secondNode;
        while((head != null) && (head.next != null)){
            firstNode = head;
            secondNode = head.next;
            //交换
            prevNode.next = secondNode;
            firstNode.next = secondNode.next;
            secondNode.next = firstNode;
            //移位
            prevNode = firstNode;
            head = firstNode.next;
        }
        return dummy.next;
    }
}
// @lc code=end

