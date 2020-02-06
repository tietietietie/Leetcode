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
    //此处head表示当前遍历到的ListNode节点
    private ListNode head;
    //计算链表长度
    private int getListLength(ListNode head){
        int c = 0;
        while(head != null){
            c++;
            head = head.next;
        }
        return c;
    }

    //中序遍历创建平衡二叉树
    private TreeNode inorder(int l, int r){
        if(l > r) return null;
        int mid = (l+r)/2;
        TreeNode left = inorder(l,mid-1);
        TreeNode node = new TreeNode(this.head.val);
        this.head = this.head.next;
        node.left = left;
        TreeNode right = inorder(mid+1,r);
        node.right = right;
        return node;

    }
    public TreeNode sortedListToBST(ListNode head) {
        int len = getListLength(head);
        this.head = head;
        return inorder(0,len-1);
    }  
}
// @lc code=end

