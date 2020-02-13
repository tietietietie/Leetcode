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
        //设置哑节点
        ListNode helper = new ListNode(Integer.MIN_VALUE);
        //cur表示待插入点，pre表示插入位置
        ListNode cur = head;
        ListNode pre = helper;
        while(cur != null){
            ListNode next = cur.next;
            //插入点的next一定是大于等于cur.val，而且是第一个大于等于val的数
            while(pre.next != null && pre.next.val < cur.val){
                pre = pre.next;
            }
            cur.next = pre.next;
            pre.next = cur;
            pre = helper;
            cur = next;
        }
        return helper.next;
    }
}
// @lc code=end

