/*
 * @lc app=leetcode.cn id=345 lang=java
 *
 * [345] 反转字符串中的元音字母
 */

// @lc code=start
class Solution {
    public String reverseVowels(String s) {
        HashSet<Character> set = new HashSet<>(){{add('a');add('e');add('i');add('o');add('u');
        add('A');add('E');add('I');add('O');add('U');}};
        char[] chars = s.toCharArray();
        int l = 0, r = chars.length-1;
        while(l < r){
            while(l < r && !set.contains(chars[l]))
                l++;
            while(l < r && !set.contains(chars[r]))
                r--;
            char temp = chars[l];
            chars[l] = chars[r];
            chars[r] = temp;
            r--;
            l++;
        }
        return String.valueOf(chars);
    }
}
// @lc code=end

