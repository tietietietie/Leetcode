## [剑指 Offer 11. 旋转数组的最小数字](https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof/)

- 二分，每次能排除一半的区间，有两个注意的点
  - mid必须与nums[r]比较大小，为了同一旋转和未旋转情况，当nums[mid] > nums[r]，说明最小值一定在mid右侧，当nums[mid] < nums[r]，说明最小值一定在mid左侧或者mid本身
  - 无法处理重复值的情况，nums[mid] == nums[r]，此时Mid可能在上升区间或者下降区间，此时令r--
- O(logn)/O(1)

```java
class Solution {
    public int minArray(int[] numbers) {
        int l = 0, r = numbers.length - 1;
        if(numbers[l] <= numbers[r]) return numbers[0];
        while(l < r) {
            int mid = (l + r) / 2;
            if(numbers[mid] > numbers[l]) l = mid + 1;
            else r = mid;
        }
        return numbers[l];
    }
}
```

## [1521. 找到最接近目标值的函数值](https://leetcode-cn.com/problems/find-a-value-of-a-mysterious-function-closest-to-target/)

### Solution 1

* 如果固定一个数num，他与任意多个数按位与产生的结果是很少的，也就是可以用一个set把结果保存下来
* 对于区间[i r]求和，其值可以从[i, r-1]的结果快速求得（因为固定r-1按位与的结果很少）
* O(20n)/O(20)

```java
class Solution {
    public int closestToTarget(int[] arr, int target) {
        HashSet<Integer> pre = new HashSet<>();
        pre.add(arr[0]);
        int ans = Integer.MAX_VALUE;
        for(int r = 1; r < arr.length; r++) {
            HashSet<Integer> cur = new HashSet<>();
            cur.add(arr[r]);
            for(int num : pre) {
                ans = Math.min(ans, Math.abs(num - target));
                cur.add(arr[r] & num);
            }
            pre = cur;
        }s
        for(int num : pre)
            ans = Math.min(ans, Math.abs(num - target));
        return ans;
    }
}
```

### Solution 2

* 滑动窗口 + 线段树
  * 单调性：func(arr,l,r+1)<=func(arr,l,r)<=func(arr,l+1,r)
  * 线段树求[l, r] logn
* O(logn)/o(n)

## [887. 鸡蛋掉落](https://leetcode-cn.com/problems/super-egg-drop/)

### Solution 1

* dp[K]\[N] :表示K个鸡蛋，N层楼，确定任意F所需要的最小操作次数
  * dp[K]\[N] = min(max(dp[K-1]\[x-1], dp\[K][N-x])) + 1 (1 <= x <= N)
  * 先确定的初始值为 K == 1， 和 N <= 1的部分
  * dp\[1][N] = N, dp[K]\[0] = 0, dp\[K][1] = 1;
* 如果直接循环求解dp，则需要O(KN*N)，考虑到dp[K-1]\[x-1]单调递增，dp\[K][N-x]单调递减，相交点处两值的最大值最小，使用二分法求相交点**右侧第一个值**
  * 注意l为2开始，r为N，这样最终l的值必须>=2，从而保证l的左边是有值的。
* O(KNlogN)/O(N*N)

```java
class Solution {
    public int superEggDrop(int K, int N) {
        int[][] dp = new int[K+1][N+1];
        for(int i = 0; i <= K; i++)
            dp[i][0] = 0;
        for(int j = 0; j <= N; j++)
            dp[1][j] = j;
        
        for(int i = 2; i <= K; i++)
            for(int j = 1; j <= N; j++) {
                dp[i][j] = dp[i][j-1] + 1;
                int l = 2, r = j;
                while(l < r) {
                    int mid = (l + r) / 2;
                    if(dp[i][j-mid] > dp[i-1][mid-1]) l = mid+1;
                    else r = mid;
                }
                dp[i][j] = Math.min(Math.max(dp[i][j-l], dp[i-1][l-1]),
                                    Math.max(dp[i][j-l+1], dp[i-1][l-2])) + 1;
            }
        
        return dp[K][N];
    }
}
```

### Solution 2

* 观察dp[K]\[N] = min(max(dp[K-1]\[x-1], dp\[K][N-x])) + 1 (1 <= x <= N)，发现在确定k时，随着N增大， dp\[K][N-x]曲线上移，两曲线的交点一定右移，即最优解x的坐标是单调增的，从而一次遍历即可。
* O(KN)/O(N*N)

```java
class Solution {
    public int superEggDrop(int K, int N) {
        int[][] dp = new int[K+1][N+1];
        for(int i = 1; i <= K; i++)
            dp[i][1] = 1;
        for(int j = 0; j <= N; j++)
            dp[1][j] = j;
        
        for(int i = 2; i <= K; i++){
            int x = 1;
            for(int j = 2; j <= N; j++) {
                while(x+1 <= j && Math.max(dp[i][j-x], dp[i-1][x-1]) > Math.max(dp[i][j-x-1], dp[i-1][x]))
                    x++;
                dp[i][j] = Math.max(dp[i][j-x], dp[i-1][x-1]) + 1;
            }
        }
            
        
        return dp[K][N];
    }
}
```

### Solution 3

* 逆向思维，dp[T]\[K]表示T次操作，K个鸡蛋能确定的楼层数
  * dp[T]\[K] = dp[T-1]\[K-1]  + dp[T-1]\[K]  + 1
* 

## [392. 判断子序列](https://leetcode-cn.com/problems/is-subsequence/)

* 双指针法略，扩展问题：当s经常变化时，如何判断t是否包含子序列s
* dp[i]\[j] :表示t在位置i的右侧（包括i），字符j第一次出现的位置
  * dp[i]\[j] = i  (t[i] == j)
  * dp[i]\[j] = dp[i+1]\[j]  (t[i] != j)
* 如何判断？ ： i从0开始，判断s在j位置的字符在右侧第一次出现的位置，如果出现的位置为n，则说明不存在，如果存在，则i移动到该位置的右一位，注意i可能会移动到n-1的右一位，此时如果j刚好循环完成，则退出，否在在下次循环中一定会返回false
* O(n*26 + m) /O(n * 26)

```java
class Solution {
    public boolean isSubsequence(String s, String t) {
        int m = s.length(), n = t.length();
        int[][] dp = new int[n+1][26];
        for(int i = 0; i < 26; i++)
            dp[n][i] = n;
        
        for(int i = n-1; i >= 0; i--)
            for(int j = 0; j < 26; j++) {
                if(t.charAt(i) - 'a' == j)
                    dp[i][j] = i;
                else
                    dp[i][j] = dp[i+1][j];
            }
        
        int i = 0;
        for(int j = 0; j < m; j++) {
            if(dp[i][s.charAt(j) - 'a'] == n) return false;
            i = dp[i][s.charAt(j) - 'a'] + 1;
        }
        return true;
    }
}
```

## [410. 分割数组的最大值](https://leetcode-cn.com/problems/split-array-largest-sum/)

### Solution 1

* dp
  * dp[i]\[j] ：表示从[0, i]分割j次的子数组各自和最大值中的最小值
  * dp[i]\[j] = min(max(dp[k]\[j-1], sum(k+1, j)))，其中k从[0, i-1]
* O(n^2 * m) /O(m * n)

```java
class Solution {
    public int splitArray(int[] nums, int m) {
        int n = nums.length;
        int[] prefix = new int[n+1];
        for(int i = 1; i <= n; i++)
            prefix[i] = prefix[i-1] + nums[i-1];
        
        int[][] dp = new int[n][m+1];
        for(int i = 0; i < n; i++)
            dp[i][1] = prefix[i+1];
        
        for(int i = 0; i < n; i++)
            for(int j = 2; j <= m; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                for(int k = 0; k < i; k++) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j-1], prefix[i+1] - prefix[k+1]));
                }
            }
        
        return dp[n-1][m];
    }
}
```

### Solution 2

* 贪心 + 二分
* 使用O(n)时间可以判断某一个可能的最小值ans是否符合要求
* O(nlog(sum))/O(1)

```java
class Solution {
    public int splitArray(int[] nums, int m) {
        long l = 0, r = 0;
        for(int num : nums) {
            r += num;
            if(l < num) l = num;
        }
        while(l < r) {
            long mid = l + (r - l) / 2;
            if(check(nums, m, mid))
                r = mid;
            else
                l = mid+1;
        }
        return (int)l;
    }
    
    private boolean check(int[] nums, int m, long mid) {
        int cnt = 1, sum = 0;
        for(int i = 0; i < nums.length; i++) {
            if(sum + nums[i] <= mid)
                sum += nums[i];
            else {
                sum = nums[i];
                cnt++;
            }
        }
        return cnt <= m;
    }
}
```

## [329. 矩阵中的最长递增路径](https://leetcode-cn.com/problems/longest-increasing-path-in-a-matrix/)

### Solution 1

* 记忆化dfs，计算每一个节点(i, j)为起点的最长路径
* 不需要考虑有环的情况，也就是说不需要visited，因为路径是严格递增的
* O(m\*n)/O(m\*n)

```java
class Solution {
    private int[][] directions = new int[][]{{1,0}, {0,1}, {-1,0}, {0,-1}};
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length;
        if(m == 0) return 0;
        int n = matrix[0].length;
        if(n == 0) return 0;
        int ans = 0;
        int[][] memo = new int[m][n];
        
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++) {
                ans = Math.max(ans, dfs(i, j, m, n, matrix, memo));
            }
        return ans;
    }
    
    private int dfs(int i, int j, int m, int n, int[][] matrix, int[][] memo) {
        if(memo[i][j] != 0) return memo[i][j];
        int ans = 0;
        for(int[] dir : directions) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            if(nextI >= 0 && nextI < m && nextJ >= 0 && nextJ < n && matrix[i][j] < matrix[nextI][nextJ])
                ans = Math.max(ans, dfs(nextI, nextJ, m, n, matrix, memo));
        }
        memo[i][j] = ans+1;
        return ans+1;
    }
}
```

### Solution 2

* 有向图的拓扑排序：计算每一个节点的出度，即节点(I,j)的下一节点个数
* 将出度为0的节点入队，并将其周围的节点（出度一定大于0）的出度减一，将新产生的出度为0的节点入队，实现BFS

* O(m\*n)/O(m\*n)

```java

```

