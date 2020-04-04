/*
 * @lc app=leetcode.cn id=406 lang=java
 *
 * [406] 根据身高重建队列
 */

// @lc code=start
class Solution {
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people,(o1,o2) -> o1[0] == o2[0] ? o1[1]-o2[1] : o2[0]-o1[0]);
        ArrayList<int[]> queue = new ArrayList<>();
        for(int[] p : people)
            queue.add(p[1],p);
        return queue.toArray(new int[queue.size()][]);
    }
}
// @lc code=end

