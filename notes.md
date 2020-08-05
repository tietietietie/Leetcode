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

## [LCP 13. 寻宝](https://leetcode-cn.com/problems/xun-bao/)

* 寻宝路径为 S ---> O ---> M1 ---> O ---> M2 ---> O ... ---> Mn ---> T
* 预处理，使用bfs，得到每一个机关到任意节点的最短路径，时间复杂度为O(nb * m * n)
* 预处理，求出任意两机关间的最短距离（距离是指从已激活的机关i出发，经过某一石头堆k，到达未激活的机关j的最短距离），显然距离可以通过遍历石头堆得到，时间复杂度问O(nb * nb * ns)

* 经过预处理，可以得到从任意机关出发，到达另一机关/出发点（S--->O--->M)/终点的最短距离dist[i]\[j]
* 定义mask表示已经激活的机关和未激活的机关（位表示，状态压缩），显然mask的范围为[1, 1 << nb -1]
* dp[mask]\[i] = min(dp[mask xor 1 << i]\[j] + dist[i]\[j])
  * 到达某一状态mask时处于已激活的机关i,则他的上一状态为mask xor (1 << i)，其中J为mask中已经已经激活的机关，可以由mask & (1 << j) ！= 0判断
  * 由于mask的上一状态 mask xor 1 << i 一定是小于mask的，所以dp中的mask可以直接从小到大迭代。
* dp的时间复杂度为O(2^nb * nb * nb)

```java
class Solution {
    private int[][] directions = new int[][]{{1,0}, {0,1}, {-1,0}, {0, -1}};
    int m, n;
    public int minimalSteps(String[] maze) {
        m = maze.length;
        n = maze[0].length();
        //统计所有的机关(buttons)和石头堆的坐标，以及开始位置和结束位置的坐标
        ArrayList<int[]> buttons = new ArrayList<>();
        ArrayList<int[]> stones  = new ArrayList<>();
        int sx = -1, sy = -1, tx = -1, ty = -1;
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++) {
                if(maze[i].charAt(j) == 'M')
                    buttons.add(new int[]{i, j});
                if(maze[i].charAt(j) == 'O')
                    stones.add(new int[]{i, j});
                if(maze[i].charAt(j) == 'S') {
                    sx = i;
                    sy = j;
                }
                if(maze[i].charAt(j) == 'T') {
                    tx = i;
                    ty = j;
                }
            }
        
        int nb = buttons.size(), ns = stones.size();
        //计算start到所有石头堆/终点/起点的最短路径
        int[][] startDist = bfs(maze, sx, sy);
        if(nb == 0)
            return startDist[tx][ty];
        
        int[][][] dd = new int[nb][][];
        for(int i = 0; i < nb; i++)
            dd[i] = bfs(maze, buttons.get(i)[0], buttons.get(i)[1]);
        
        //dist[i][nb]表示从 S ---> Oi ---> Mi的最短距离 
        //dist[i][nb+1]表示 Mi ---> T的最短距离
        int[][] dist = new int[nb][nb+2];
        for(int i = 0; i < nb; i++)
            Arrays.fill(dist[i], -1);
        
        for(int i = 0; i < nb; i++) {
            int temp = -1;
            for(int k = 0; k < ns; k++) {
                int stoneX = stones.get(k)[0], stoneY = stones.get(k)[1];
                if(startDist[stoneX][stoneY] != -1 && dd[i][stoneX][stoneY] != -1 
                   && (startDist[stoneX][stoneY] + dd[i][stoneX][stoneY] < temp || temp == -1))
                    temp = startDist[stoneX][stoneY] + dd[i][stoneX][stoneY];
            }
            dist[i][nb] = temp;
            
            for(int j = i+1; j < nb; j++) {
                temp = -1;
                for(int k = 0; k < ns; k++) {
                    int stoneX = stones.get(k)[0], stoneY = stones.get(k)[1];
                    if(dd[j][stoneX][stoneY] != -1 && dd[i][stoneX][stoneY] != -1 
                       && (dd[j][stoneX][stoneY] + dd[i][stoneX][stoneY] < temp || temp == -1))
                        temp = dd[j][stoneX][stoneY] + dd[i][stoneX][stoneY];
                }
                dist[i][j] = temp;
                dist[j][i] = temp;
            }

            
            dist[i][nb+1] = dd[i][tx][ty];
        }
        
        for(int i = 0; i < nb; i++)
            if(dist[i][nb] == -1 || dist[i][nb+1] == -1)
                return -1;
        
        //dp[mask][i]表示在状态mask下，位于机关i时，最小步数
        int[][] dp = new int[1 << nb][nb];
        for(int mask = 0; mask < (1 << nb); mask++)
            Arrays.fill(dp[mask], -1);
        for(int i = 0; i < nb; i++)
            dp[1 << i][i] = dist[i][nb];
        
        for(int mask = 1; mask < (1 << nb); mask++)
            for(int i = 0; i < nb; i++) {
                if((mask & (1 << i)) != 0) {
                    for(int j = 0; j < nb; j++) 
                        if((mask & (1 << j)) == 0) {
                            int next = mask | (1 << j);
                            if(dp[next][j] == -1 || dp[next][j] > dp[mask][i] + dist[i][j])
                                dp[next][j] = dp[mask][i] + dist[i][j];
                        }
                }
            }
        
        int ans = -1, finalMask = (1 << nb) - 1;
        for(int i = 0; i < nb; i++)
            if(ans == -1 || ans > dp[finalMask][i] + dist[i][nb+1])
                ans = dp[finalMask][i] + dist[i][nb+1];
        
        return ans;
    }
    
    private int[][] bfs(String[] maze, int x, int y) {
        int[][] dist = new int[m][n];
        for(int i = 0; i < m; i++)
            Arrays.fill(dist[i], -1);
        LinkedList<int[]> queue = new LinkedList<>();
        dist[x][y] = 0;
        queue.offer(new int[]{x, y});
        while(!queue.isEmpty()) {
            int[] curNode = queue.poll();
            int curX = curNode[0], curY = curNode[1];
            for(int[] direction : directions) {
                int nextX = curX + direction[0], nextY = curY + direction[1];
                if(withinBound(nextX, nextY) && dist[nextX][nextY] == -1 && maze[nextX].charAt(nextY) != '#') {
                    queue.offer(new int[]{nextX, nextY});
                    dist[nextX][nextY] = dist[curX][curY] + 1;
                }
                    
            }
        }
        return dist;
    }
    
    private boolean withinBound(int x, int y) {
        return x >= 0 && x < m && y >= 0 && y < n;
    }
}
```

## [343. 整数拆分](https://leetcode-cn.com/problems/integer-break/)

### Solution 1

* dp[i]表示整数i的最大乘积拆分
  * dp[i] = max(dp[j] * (i - j), j * (i - j))
  * 注意考虑dp[j]不被拆分的情况
* O(n * n) / O(n)

```java
class Solution {
    public int integerBreak(int n) {
        int[] dp = new int[n+1];
        dp[1] = 0;
        dp[2] = 1;
        for(int i = 3; i <= n; i++)
            for(int j = 1; j <= i-1; j++) {
                dp[i] = Math.max(dp[i], Math.max(dp[j] * (i - j), j * (i - j)));
            }
        return dp[n];
    }
}
```

### Solution 2

* 贪心，如果拆分元素中有大于等于4的元素f，由于 (f - 2) * 2 = 2 * f - 2 >= f,所以拆分元素一定为1，2，3这三种元素之一，又因为 2 * 2 * 2 < 3 * 3 ，所以需要尽可能多的三
* O(1) / O(1)

```java
class Solution {
    public int integerBreak(int n) {
        if(n <= 3) return n-1;
        int a = n/3, b = n%3;
        if(b == 0) return (int)Math.pow(3,a);
        if(b == 1) return (int)Math.pow(3,a-1) * 4;
        return (int)Math.pow(3,a) * 2;
    }
}
```

## [632. 最小区间](https://leetcode-cn.com/problems/smallest-range-covering-elements-from-k-lists/)

### Solution 1

* 小顶堆，维护来自各个数组的K个数，取最小值和最大值组成[l, r]，并保证对于每一个确定的l，都有最小且合法的r
* O(nklogk)/O(k)

```java
class Solution {
    private class Node {
        //i,j表示第i个数组的第j个数组，val为值
        int i;
        int j;
        int val;
        public Node(int i, int j, int val) {
            this.i = i;
            this.j = j;
            this.val = val;
        }
    }
    
    public int[] smallestRange(List<List<Integer>> nums) {
        int n = nums.size();
        int st = 0, ed = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> o1.val - o2.val);
        for(int i = 0; i < n; i++) {
            int val = nums.get(i).get(0);
            pq.offer(new Node(i, 0, val));
            max = Math.max(max, val);
        }
        
        while(pq.size() == n) {
            Node curNode = pq.poll();
            int curI = curNode.i;
            int curJ = curNode.j;
            int min = curNode.val;
            if(max - min < ed - st) {
                st = min;
                ed = max;
            }
            if(curJ+1 < nums.get(curI).size()) {
                int nextVal = nums.get(curI).get(curJ+1);
                max = Math.max(max, nextVal);
                pq.offer(new Node(curI, curJ+1, nextVal));
            }
        }
        
        return new int[]{st, ed};
    }
}
```

## [207. 课程表](https://leetcode-cn.com/problems/course-schedule/)

### Solution 1

* 判断有向图是否有环 ---> 拓扑排序问题
* BFS，统计每个节点的入度，将**入度为0**的节点入队，并记录为visited，入度为0的相邻节点的度数减1。
* O(V + E) / O(V)

```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] adjaList = new List[numCourses];
        int[] inDegree = new int[numCourses];
        boolean[] visited = new boolean[numCourses];
        for(int i = 0; i < numCourses; i++)
            adjaList[i] = new ArrayList<>();
        for(int[] pre : prerequisites) {
            adjaList[pre[1]].add(pre[0]);
            inDegree[pre[0]]++;
        }
        
        LinkedList<Integer> queue = new LinkedList<>();
        for(int i = 0; i < numCourses; i++)
            if(inDegree[i] == 0)
                queue.offer(i);
        while(!queue.isEmpty()) {
            int cur = queue.poll();
            visited[cur] = true;
            for(int next : adjaList[cur]) {
                inDegree[next]--;
                if(inDegree[next] == 0)
                    queue.offer(next);
            }
        }
        for(int i = 0; i < numCourses; i++)
            if(!visited[i])
                return false;
        return true;
    }
}
```

### Solution 2

* DFS,其中节点有三种状态：1，已经把节点V的所有能达到的节点访问完成。(visited[node] = true) 2，OnSearch，表示该节点正在进行dfs(onSearch[node] =  true) 3，该节点没有在dfs（onSearch[node] = false)
* 如果存在环，则会有在访问节点node的next时，发现next正在search，此时存在环
* O(V + E) / O(V)

```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] adjaList = new List[numCourses];
        boolean[] onSearch = new boolean[numCourses];
        boolean[] visited  = new boolean[numCourses];
        for(int i = 0; i < numCourses; i++)
            adjaList[i] = new ArrayList<>();
        for(int[] pre : prerequisites) {
            adjaList[pre[1]].add(pre[0]);
        }
        
        for(int i = 0; i < numCourses; i++)
            if(!visited[i] && hasCycle(adjaList, i, visited, onSearch))
                return false;
        return true;
    }
    
    private boolean hasCycle(List<Integer>[] adjaList, int node, boolean[] visited, boolean[] onSearch) {
        if(visited[node]) return false;
        onSearch[node] = true;
        for(int next : adjaList[node]) {
            if(onSearch[next])
                return true;
            if(hasCycle(adjaList, next, visited, onSearch))
                return true;
        }
        onSearch[node] = false;
        visited[node] = true;
        return false;
    }
}
```

## [210. 课程表 II](https://leetcode-cn.com/problems/course-schedule-ii/)

### Solution 1

* BFS：邻接表 + 入度表
* O(V + E) / O(V)

```java
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int count = 0;
        int[] path = new int[numCourses];
        int[] inDegree = new int[numCourses];
        List<Integer>[] adjaList = new List[numCourses];
        for(int i = 0; i < numCourses; i++)
            adjaList[i] = new ArrayList<>();
        for(int[] pre : prerequisites) {
            adjaList[pre[1]].add(pre[0]);
            inDegree[pre[0]]++;
        }
        
        LinkedList<Integer> queue = new LinkedList<>();
        for(int i = 0; i < numCourses; i++)
            if(inDegree[i] == 0)
                queue.offer(i);
        while(!queue.isEmpty()) {
            int cur = queue.poll();
            path[count++] = cur;
            for(int next : adjaList[cur]) {
                inDegree[next]--;
                if(inDegree[next] == 0)
                    queue.offer(next);
            }
        }
        if(count != numCourses) return new int[0];
        return path;
    }
}
```

### Solution 2

* DFS : visited[node] = true 表示node节点为起点的图没有环，且所有相连节点都已入栈，onSearch[node] = true，表示正在对node为起点的图进行遍历，bfs返回的值为当前Node为起点的图是否有环，如果有环，则无法拓扑排序，stack.push(node)时，node的后续节点都已经入栈。
* O(V + E) / O(V)

```java
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[numCourses];
        boolean[] onSearch = new boolean[numCourses];
        List<Integer>[] adjaList = new List[numCourses];
        for(int i = 0; i < numCourses; i++)
            adjaList[i] = new ArrayList<>();
        for(int[] pre : prerequisites) 
            adjaList[pre[1]].add(pre[0]);
        
        for(int i = 0; i < numCourses; i++)
            if(!visited[i] && bfs(adjaList, i, visited, onSearch, stack))
                return new int[0];
        
        int[] path = new int[numCourses];
        for(int i = 0; i < numCourses; i++)
            path[i] = stack.pop();
        return path;
    }
    
    //return hasCycle
    private boolean bfs(List<Integer>[] adjaList, int node, boolean[] visited, boolean[] onSearch, Stack<Integer> stack) {
        if(visited[node]) return false;
        onSearch[node] = true;
        for(int next : adjaList[node]) {
            if(onSearch[next])
                return true;
            if(bfs(adjaList, next, visited, onSearch, stack))
                return true;
        }
        onSearch[node] = false;
        visited[node] = true;
        stack.push(node);
        return false;
    }
}
```

## [1171. 从链表中删去总和值为零的连续节点](https://leetcode-cn.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/)

* hashmap:(prefixSum, node)

* 第一次遍历，保存前缀和（前缀和相同时，保存最后面的节点）
* 第二次遍历，如果当前节点的前缀和在后面出现过，则直接忽略掉中间的区间，跳转到此前缀和最后出现的位置的next
* O(n) / O(n)

```java
class Solution {
    public ListNode removeZeroSumSublists(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        HashMap<Integer, ListNode> map = new HashMap<>();
        
        int sum = 0;
        for(ListNode cur = dummy; cur != null; cur = cur.next) {
            sum += cur.val;
            map.put(sum, cur);
        }
        
        sum = 0;
        for(ListNode cur = dummy; cur != null; cur = cur.next) {
            sum += cur.val;
            cur.next = map.get(sum).next;
        }
        
        return dummy.next;
    }
}
```

