/*
 * @lc app=leetcode.cn id=109 lang=java
 *
 * [109] 有序链表转换二叉搜索树
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
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    //快慢指针确定中间节点
    private ListNode findMiddle(ListNode head){
        ListNode preSlow = null;
        ListNode slow = head;
        ListNode fast = head;
        while(fast != null && fast.next != null){
            preSlow = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        //与左半链断开
        preSlow.next = null;
        return slow;
    }
    public TreeNode sortedListToBST(ListNode head) {
        if(head == null) return null;
        if(head.next == null) return new TreeNode(head.val);
        ListNode mid = findMiddle(head);
        TreeNode node = new TreeNode(mid.val);
        node.left = sortedListToBST(head);
        node.right = sortedListToBST(mid.next);
        return node;
    }
}
// @lc code=end

