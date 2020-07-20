import java.util.*;

public class Solution
{
    public String getPermutation(int n, int k) {
        int[] counts = new int[n+1];
        for(int i = 1; i < n; i++)
            counts[i] = countRoot(i, n);
        boolean[] visited = new boolean[n+1];
        int node = 1, level = 1;
        StringBuilder sb = new StringBuilder();
        while(level <= n) {
            if(k > counts[level]) {
                for(int i = node+1; i <= n; i++) {
                    if(!visited[i]) {
                        node = i;
                        break;
                    }
                }
                k -= counts[level];
            }else {
                sb.append(node);
                visited[node] = true;
                level++;
                node = getNextNode(n, visited);
            }
        }
        return sb.toString();
    }

    private int countRoot(int level, int n) {
        int count = 1;
        while(n - level > 0) {
            count = count * (n-level);
            level++;
        }
        return count;
    }

    private int getNextNode(int n, boolean[] visited) {
        for(int i = 1; i <= n; i++)
            if(!visited[i])
                return i;
        return -1;
    }
    public static void main(String[] args) {
        Solution s = new Solution();
        s.getPermutation(3, 3);
    }
}