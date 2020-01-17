/*
 * @lc app=leetcode.cn id=21 lang=java
 *
 * [21] 合并两个有序链表
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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode pre = dummy, i = l1, j = l2;
        while(i != null || j != null){
            if(j == null || (i != null && i.val <= j.val)){
                pre.next = new ListNode(i.val);
                pre = pre.next;
                i = i.next;
            }
            else{
                pre.next = new ListNode(j.val);
                pre = pre.next;
                j = j.next;
            }
        }
        return dummy.next;
    }
}
// @lc code=end

