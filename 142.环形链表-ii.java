/*
 * @lc app=leetcode.cn id=142 lang=java
 *
 * [142] 环形链表 II
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
    public ListNode detectCycle(ListNode head) {
        if(head == null) return null;
        Set<ListNode> set = new HashSet<>();
        while(head != null){
            if(set.contains(head)) return head;
            else set.add(head);
            head = head.next;
        }
        return null;
    }
}
// @lc code=end

