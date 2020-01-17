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
         return removeNode(head,n)==n?head.next:head;
     }	
     public int removeNode(ListNode node,int n) {		
         if(node.next == null)  return 1;
         int m = removeNode(node.next, n);
         if(m == n) 
             node.next = node.next.next;
         return m+1;
     }
 }
// @lc code=end

