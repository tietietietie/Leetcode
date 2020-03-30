/*
 * @lc app=leetcode.cn id=345 lang=java
 *
 * [345] 反转字符串中的元音字母
 */

// @lc code=start
class Solution {
    public String reverseVowels(String s) {
        Stack<Character> vowels = new Stack<>();
        LinkedList<Integer> indexs = new LinkedList<>();
        HashSet<Character> set = new HashSet<>()                                       {{add('a');add('e');add('i');add('o');add('u');add('A');add('E');add('I');add('O');add('U');}};
        char[] chars = s.toCharArray();
        for(int i = 0; i < chars.length; i++){
            if(set.contains(chars[i])){
                vowels.push(chars[i]);
                indexs.offer(i);
            }
        }
        while(!vowels.isEmpty()){
            chars[indexs.poll()] = vowels.pop();
        }
        return String.valueOf(chars);
    }
}
// @lc code=end

