/*
 * @lc app=leetcode.cn id=725 lang=java
 *
 * [725] 分隔链表
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
    public ListNode[] splitListToParts(ListNode root, int k) {
        //确定root中元素个数
        int len = 0;
        ListNode pre = root;
        ListNode[] ans = new ListNode[k];
        while(pre != null){
            len++;
            pre = pre.next;
        }
        int quotient = len/k;
        int remainder = len%k;
        int width;
        pre = root;
        for(int i = 0; i < k; i++){
            width = quotient + (i<remainder?1:0);
            ans[i] = pre;
            while(width > 1){
                pre = pre.next;
                width--;
            }
            if(width == 1){
                ListNode next = pre.next;
                pre.next = null;
                pre = next;
            }
        }
        return ans;
    }
}
// @lc code=end

