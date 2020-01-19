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
        //定义ListNode的外部比较器
        Comparator<ListNode> cmp = new ListNodeComparator();

        //建立优先队列
        Queue<ListNode> pq = new PriorityQueue<ListNode>(cmp);
        for(ListNode l : lists){
            if(l != null)
                pq.add(l);
        }

        //合并链表
        ListNode head = new ListNode(0);
        ListNode pre = head;
        while(!pq.isEmpty()){
            pre.next = pq.poll();
            ListNode next = pre.next.next;
            if(next != null)
                pq.add(next);
            pre = pre.next;
        }

        return head.next;
    }
}
public class ListNodeComparator implements Comparator<ListNode>{
    public int compare(ListNode o1, ListNode o2){
        return o1.val - o2.val;
    }
}
// @lc code=end

