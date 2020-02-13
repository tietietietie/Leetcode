/*
 * @lc app=leetcode.cn id=141 lang=java
 *
 * [141] 环形链表
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head){
        while(head != null && head.next != null){
            if(head.next == head) return true;
            else{
                head.next = head.next.next;
                head = head.next;
            }
        }
        return false; 
    }
}
// @lc code=end

