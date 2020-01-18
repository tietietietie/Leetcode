/*
 * @lc app=leetcode.cn id=23 lang=java
 *
 * [23] 合并K个排序链表
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
    public ListNode mergeKLists(ListNode[] lists) {
        int index = findSmallestNode(lists);
        if(index == -1)
            return null;
        else{
            ListNode pre = lists[index];
            lists[index] = lists[index].next;
            pre.next = mergeKLists(lists);
            return pre;
        }
    }

    private int findSmallestNode(ListNode[] lists){
        int sval = Integer.MAX_VALUE;
        int index = -1;
        for(int i = 0; i < lists.length; i++){
            if(lists[i] == null)
                continue;
            else if(lists[i].val < sval){
                sval = lists[i].val;
                index = i;
            }
        }
        return index;
    }
}
// @lc code=end

