public class Solution {
    public boolean Find(int target, int [][] array) {
        int i = array.length-1;
        int j = 0;
        while(i >= 0 && j < array[0].length){
            int temp = array[i][j];
            if(temp == target) return true;
            else if(temp < target) j++;
            else i--;
        }
        return false;
    }
}