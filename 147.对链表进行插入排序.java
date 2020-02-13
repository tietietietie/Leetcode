/*
 * @lc app=leetcode.cn id=147 lang=java
 *
 * [147] 对链表进行插入排序
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
    public ListNode insertionSortList(ListNode head) {
        if(head == null) return null;
        //设置哑节点
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;

        //cur1表示当前插入节点，pre表示cur的左侧节点
        ListNode cur1 = head;
        ListNode pre = dummy;
        while(cur1 != null){
            ListNode next = cur1.next;
            if(cur1.val >= pre.val){
                pre = cur1;
                cur1 = next;
                continue;
            }
            else{
                ListNode cur2 = dummy;
                while(cur2 != pre){
                    if(cur2.next.val > cur1.val){
                        cur1.next = cur2.next;
                        cur2.next = cur1;
                        pre.next = next;
                        cur1 = next;
                        break;
                    }
                    cur2 = cur2.next;
                }
            }
        }
        return dummy.next;
    }
}
// @lc code=end

