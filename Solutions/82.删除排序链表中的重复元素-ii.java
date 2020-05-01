/*
 * @lc app=leetcode.cn id=82 lang=java
 *
 * [82] 删除排序链表中的重复元素 II
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
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        while((pre.next != null) && (pre.next.next != null)){
            if(pre.next.val != pre.next.next.val)
                pre = pre.next;
            else{
                pre.next = delete(pre.next);
            }
        }
        return dummy.next;
    }
    public ListNode delete(ListNode node){
        ListNode pre = node;
        while((pre.next != null) && (pre.val == pre.next.val))
            pre = pre.next;
        return pre.next;
    }
}
// @lc code=end

