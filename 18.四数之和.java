/*
 * @lc app=leetcode.cn id=18 lang=java
 *
 * [18] 四数之和
 */

// @lc code=start
class Solution {
    public List<List<Integer>> fourSum(int[] num, int target) {
        Arrays.sort(num);
        List<List<Integer>> res = new LinkedList<>();
        //多加了层循环
        for (int j = 0; j < num.length - 3; j++) {
            //防止重复的
            if (j == 0 || (j > 0 && num[j] != num[j - 1]))
                for (int i = j + 1; i < num.length - 2; i++) {
                    //防止重复的，不再是 i == 0 ，因为 i 从 j + 1 开始
                    if (i == j + 1 || num[i] != num[i - 1]) {
                        int lo = i + 1, hi = num.length - 1, sum = target - num[j] - num[i];
                        while (lo < hi) {
                            if (num[lo] + num[hi] == sum) {
                                res.add(Arrays.asList(num[j], num[i], num[lo], num[hi]));
                                while (lo < hi && num[lo] == num[lo + 1])
                                    lo++;
                                while (lo < hi && num[hi] == num[hi - 1])
                                    hi--;
                                lo++;
                                hi--;
                            } else if (num[lo] + num[hi] < sum)
                                lo++;
                            else
                                hi--;
                        }
                    }
                }
        }
        return res;
    }   
}
// @lc code=end

