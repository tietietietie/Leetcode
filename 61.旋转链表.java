/*
 * @lc app=leetcode.cn id=61 lang=java
 *
 * [61] 旋转链表
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
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null)
            return null;
        int len = 0;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        List<Integer> a = new ArrayList<>();
        while(head != null){
            len++;
            a.add(head.val);
            head = head.next;
        }
        k = k%len;
        int[] ans = new int[len];
        for(int i = 0; i < len; i++){
            ans[(i+k)%len] = a.get(i);
        }
        ListNode ihead = dummy.next;
        for(int i =0; i < len; i++){
            ihead.val = ans[i];
            ihead = ihead.next;            
        }
        return dummy.next;
    }
}
// @lc code=end

