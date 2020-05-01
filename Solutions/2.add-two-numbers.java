/*
 * @lc app=leetcode id=2 lang=java
 *
 * [2] Add Two Numbers
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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l = new ListNode((l1.val + l2.val) % 10);
        int carry = (l1.val + l2.val) / 10;
        ListNode temp = l;
        int sum;
        for(ListNode i = l1.next, j = l2.next; i != null || j != null || carry != 0; i = i.next, j = j.next) //为什么只能一个ListNode
        {
            if(i == null)
                i = new ListNode(0);
            if (j == null)
                j = new ListNode(0);
            sum = (carry + i.val + j.val) % 10;
            carry = (carry + i.val + j.val) / 10;
            temp.next = new ListNode(sum);
            temp = temp.next;
        }
        return l;
    }
}
// @lc code=end

