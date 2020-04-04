/*
 * @lc app=leetcode.cn id=336 lang=java
 *
 * [336] 回文对
 */

// @lc code=start
class Solution {
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> ans = new ArrayList<>();
        HashMap<String,Integer> map = new HashMap<>();
        for(int i = 0; i < words.length; i++)
            map.put(words[i],i);
        for(String word : words){
            String reverseWord = new StringBuilder(word).reverse().toString();
            int curWordIdx = map.get(word);
            if(map.get(reverseWord) != null && map.get(reverseWord) != curWordIdx)
                ans.add(Arrays.asList(curWordIdx,map.get(reverseWord)));
            for(String validPrefix : getValidPrefixes(word)){
                String reverseValidPrefix = new StringBuilder(validPrefix).reverse().toString();
                if(map.get(reverseValidPrefix) != null)
                    ans.add(Arrays.asList(curWordIdx,map.get(reverseValidPrefix)));
            }
            for(String validSuffix : getValidSuffixes(word)){
                String reverseValidSuffix = new StringBuilder(validSuffix).reverse().toString();
                if(map.get(reverseValidSuffix) != null)
                    ans.add(Arrays.asList(map.get(reverseValidSuffix),curWordIdx));
            }
        }
        return ans;
    }

    private ArrayList<String> getValidPrefixes(String word){
        int k = word.length();
        ArrayList<String> ans = new ArrayList<>();
        for(int i = 0; i < k; i++)
            if(isPalindrome(word,i,k-1))
                ans.add(word.substring(0,i));
        return ans;
    }

    private ArrayList<String> getValidSuffixes(String word){
        int k = word.length();
        ArrayList<String> ans = new ArrayList<>();
        for(int i = 0; i < k; i++)
            if(isPalindrome(word,0,i))
                ans.add(word.substring(i+1,k));
        return ans;
    }

    private boolean isPalindrome(String word, int l, int r){
        while(l < r){
            if(word.charAt(l) == word.charAt(r)){
                l++;
                r--;
            }else{
                return false;
            }
        }
        return true;
    }
}
// @lc code=end

