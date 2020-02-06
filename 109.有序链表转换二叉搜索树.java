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
    //定义一个数组
    private List<Integer> values;
    
    //链表转数组
    private void listToArray(ListNode head){
        this.values = new ArrayList<Integer>();
        while(head != null){
            this.values.add(head.val);
            head = head.next;
        }
    }

    //数组转BST
    private TreeNode arrayToBST(int left, int right){
        if(left > right) return null;
        int mid = (left+right)/2;
        TreeNode node = new TreeNode(this.values.get(mid));
        node.left = arrayToBST(left,mid-1);
        node.right = arrayToBST(mid+1,right);
        return node;
    }

    //主程序
    public TreeNode sortedListToBST(ListNode head) {
        listToArray(head);
        return arrayToBST(0,this.values.size()-1);
    }  
}
// @lc code=end

