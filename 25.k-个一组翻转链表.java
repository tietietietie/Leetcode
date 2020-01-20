/*
 * @lc app=leetcode.cn id=25 lang=java
 *
 * [25] K 个一组翻转链表
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
    public ListNode reverseKGroup(ListNode head, int k) {
        Stack<ListNode> stkNode = new Stack<ListNode>();
        ListNode preNode = head;
        while(preNode != null){
            stkNode.push(head);
            preNode = preNode.next;            
        }
        if(stkNode.size() < k)
            return head;
        else{
            ListNode top = stkNode.peek();
            while(stkNode.size() > 1){
                ListNode i = stkNode.pop();
                i.next = stkNode.peek();                
            }
            ListNode i = stkNode.pop();
            i.next = reverseKGroup(i.next,k);
            return top;
        }    
    }
}
// @lc code=end

