class Solution {
    public String longestPalindrome(String s) {
        if(s.equals(""))
            return "";
        String reverse = new StringBuffer(s).reverse().toString();
        int[][] arr = new int[s.length()][s.length()];
        int maxlen = 1, pleft = 0, pright = 0;
        for(int i = 0; i < s.length(); i++)
        {
                if(s.charAt(i) == reverse.charAt(0))
                arr[i][0] = 1;
            else 
                arr[i][0] = 0;
        }
        for(int j  = 0; j < s.length(); j++)
        {
            if(s.charAt(0) == reverse.charAt(j))
                arr[0][j] = 1;
            else 
                arr[0][j] = 0;
        }
        for(int i = 1; i < s.length(); i++)
            for(int j = 1; j < s.length(); j++)
            {
                if(s.charAt(i) == reverse.charAt(j))
                    arr[i][j] = arr[i-1][j-1]+1;
                else
                    arr[i][j] = 0;
                if(arr[i][j] > maxlen && j == s.length() + arr[i][j] - i -2)
                {
                    maxlen = arr[i][j];
                    pright = i;
                    pleft = i-maxlen+1;
                }
            }
        return s.substring(pleft,pright+1);       
        
    }

    public static void main(String[] args) {
        String a = "";
        Solution s = new Solution();
        System.out.println(s.longestPalindrome(a));
    }
}