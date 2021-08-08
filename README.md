# **Leetcode**笔记

[TOC]

## 1.两数之和

* BF：找出所有数据对
* 错误思路：Hashmap/排序/左右指针：无法处理答案为两个相同元素
* 正确思路：Hashmap/一次遍历
* 注意：
  * 对于数组中的重复元素，hashmap只能存储最右元素的下标
* 疑问
  * 在for内使用nums.length会造成时间浪费吗？编译器对循环的优化有限吧？

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> map  = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            int num = target - nums[i];
            if(map.get(num) != null)
                return new int[]{i,map.get(num)};
            map.put(nums[i],i);
        }
        return new int[]{-1,-1};
    }
}
```



## 2.两数相加

* 错误思路：尝试在L1上做加法

* 正确思路：创建新链表/逐位迭代/空节点转换为0节点
* 注意：
  * node = node.next，当node == null，退出循环，此后node = new ListNode(val)；并不连在一起
  * carry为1，就算俩节点为空，也要创建新节点。

```java
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        int carry = 0, digit1 = 0, digit2 = 0;
        while(l1 != null || l2 != null || carry != 0) {
            if(l1 == null) {
                digit1 = 0;
            } else {
                digit1 = l1.val;
                l1 = l1.next;
            }
            if(l2 == null) {
                digit2 = 0;
            } else {
                digit2 = l2.val;
                l2 = l2.next;
            }
            cur.next = new ListNode((digit1 + digit2 + carry) % 10);
            carry = (digit1 + digit2 + carry) / 10;
            cur = cur.next;
        }
        return dummy.next;
    }
}
```

## 3.无重复字符的最长子串

* 正确思路：滑动窗口/hashmap更新区间
* 注意
  * hashmap中的下标落在了l~r之间，才会更新r
  * charAt()的时间效率是O(1)，不需要转换成char array
  * 对于一般的字符串，可以使用数组代替hashmap

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if(s.equals("")) return 0;
        int[] map = new int[128];
        Arrays.fill(map,-1);
        int ans = 1;
        for(int l = 0, r = 0; r < s.length(); r++){
            if(map[s.charAt(r)] != -1 && map[s.charAt(r)] >= l)
                l = map[s.charAt(r)] + 1;
            map[s.charAt(r)] = r;
            ans = Math.max(r-l+1,ans);
        }
        return ans;
    }
}
```

## 4.寻找两个有序数组中位数

### Solution 1：第k小的数

* 正确思路：寻找第k小的数，每次找到第k/2小的数并排除,不断缩小k值，直到k等于1。
* 注意：
  * 不能在函数体内对成员变量重新定义一次，这样会使成员变量未初始化。
  * 递归终止的三个条件

```java
class Solution {
    public int m, n;
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        m = nums1.length;
        n = nums2.length;
        return (findKth(nums1,0,nums2,0,(m+n+1)/2) + findKth(nums1,0,nums2,0,(m+n+2)/2))/2.0;
    }
    private int findKth(int[] nums1, int leftA, int[] nums2, int leftB, int k){
        if(leftA == m)
            return nums2[leftB+k-1];
        if(leftB == n)
            return nums1[leftA+k-1];
        if(k == 1)
            return Math.min(nums1[leftA],nums2[leftB]);
        int i = Math.min(m-1,leftA+k/2-1);
        int j = Math.min(n-1,leftB+k/2-1);
        if(nums1[i] <= nums2[j])
            return findKth(nums1,i+1,nums2,leftB,k+leftA-i-1);
        return findKth(nums1,leftA,nums2,j+1,k+leftB-j-1);
    }
}
```

### Solution 2：二分

* 正确思路：确定两分割点i，j，使得左右两部分长度相等，并左边最大数小于右边最小数。由于i确定后,j可以确定，所以只需要对i进行二分即可。
* 注意
  * 与以往二分不同，右边界为m，而不是m-1
  * 需要考虑边界情况，即i == 0或者 i == m（j的边界情况包含在内，证明略）
  * 需要保证m <= n

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if(nums1.length > nums2.length){
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }
        int m = nums1.length, n = nums2.length, l = 0, r = m;
        while(l <= r){
            int i = (l+r)/2;
            int j = (m+n+1)/2-i;
            if((i == 0 || nums1[i-1] <= nums2[j]) && (i == m || nums2[j-1] <= nums1[i])){
                int maxLeft = 0;
                if(i == 0) maxLeft = nums2[j-1];
                else if(j == 0) maxLeft = nums1[i-1];
                else maxLeft = Math.max(nums1[i-1],nums2[j-1]);
                if((m+n)%2 == 1) return maxLeft;
                int minRight = 0;
                if(i == m) minRight = nums2[j];
                else if(j == n) minRight = nums1[i];
                else minRight = Math.min(nums1[i],nums2[j]);
                return (maxLeft+minRight)/2.0;
            }else if(i != 0 && nums1[i-1] > nums2[j])
                r = i-1;
            else
                l = i+1;
        }
        return -1;
    }
}
```

## 5.最长回文子串

* 正确思路：中心扩展法
* 注意：
  * 可以将复杂度从n^2降低为n，但是比较复杂
  * 不需要把回文substring出来，只需要求出左右端点下标，比较长度即可
  * l~r：左闭右开，c1~c2：左开右开

```java
class Solution {
    public int l = 0, r = 0;
    public String longestPalindrome(String s) {
        for(int i = 0; i < s.length(); i++){
            longestPalindrome(s,i,i);
            longestPalindrome(s,i,i+1);
        }
        return s.substring(l,r);
    }

    private void longestPalindrome(String s, int c1, int c2){
        while(c1 >= 0 && c2 < s.length() && s.charAt(c1) == s.charAt(c2)){
            c1--;
            c2++;
        }
        if(r-l < c2-c1-1){
            l = c1+1;
            r = c2;
        }
    }
}
```

## 11.盛最多水的容器

* 正确思路：双指针
* 注意
  * 默认最大容积为宽最长的容器，要使容积变大，必须在宽度变小的同时，增大最小边。
  * 为什么不能移动最长边呢？因为移动最长边，并不能使容积变大\
  * 找到第一个大于短边的边，在向内移动的过程中，小于短边的边，也一定不会使容积变大

证明：s(i, j)表示区间[i, j]的容积，移动短边i即s(i+1, j)，则忽略了s(i, j-1), s(i, j-2)... s(i, i+1)，这些忽略的容积，都不会比s(i, j)大，也就是每次移动，我们都不会遗漏掉最短边。

```java
class Solution {
    public int maxArea(int[] height) {
        int l = 0, r = height.length-1;
        int maxArea = 0;
        while(l < r){
            maxArea = Math.max(maxArea,(r-l)*Math.min(height[l],height[r]));
            if(height[l] < height[r]){
                while(l < r && height[l+1] <= height[l])
                    l++;
                l++;
            }else{
                while(l < r && height[r-1] <= height[r])
                    r--;
                r--;
            }
        }
        return maxArea;
    }
}
```

## 15.三数之和

### Solution 1

* 正确思路：排序+双指针
* 注意
  * 需要跳过重复情况，包括nums[i-1] == nums[i]，以及l,r有多个值。
  * 最好情况也就是num
* 时间复杂度：o(n*n)，空间复杂度：o(1)

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        for(int i = 0; i < nums.length-2 && nums[i] <= 0; i++){
            if(i != 0 && nums[i-1] == nums[i]) continue;
            int l = i+1, r = nums.length-1, sum = 0-nums[i];
            while(l < r){
                if(nums[l]+nums[r] == sum){
                    ans.add(Arrays.asList(nums[i],nums[l],nums[r]));
                    while(l<r && nums[l+1] == nums[l]) l++;
                    while(l<r && nums[r-1] == nums[r]) r--;
                    l++;
                    r--;
                }else if(nums[l]+nums[r] < sum)
                    l++;
                else
                    r--;
            }
        }
        return ans;
    }
}
```

### Solution 2

* 正确思路：排序+hashmap
* 注意
  * 先排序，然后存储数字最后出现的index
  * 每次需要对i,j排除重复元素（跳过相同值）

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++)
            map.put(nums[i],i);
        for(int i = 0; i < nums.length-2 && nums[i] <= 0; i++){
            for(int j = i+1; j < nums.length-1; j++){
                int target = 0 - nums[i] - nums[j];
                if(map.containsKey(target) && map.get(target) > j){
                    ans.add(Arrays.asList(nums[i],nums[j],target));
                    j = map.get(nums[j]);
                }
            }
            i = map.get(nums[i]);
        }
        return ans;
    }
}
```

## 16.最接近的三数之和

* 正确思路：排序+双指针
* 注意
  * 可以优化重复数字的情况

```java
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int ans = nums[0]+nums[1]+nums[2];
        for(int i = 0; i < nums.length-2; i++){
            int l = i+1, r = nums.length-1;
            while(l < r){
                int sum = nums[i]+nums[l]+nums[r];
                ans = Math.abs(sum-target) >= Math.abs(ans-target) ? ans : sum;
                if(sum == target)
                    return sum;
                else if(sum > target)
                    r--;
                else
                    l++;
            }
        }
        return ans;
    }
}
```

## 18. 四数和

* 排序，遍历，双指针
* 时间复杂度：o(n^3)，空间复杂度o(1)
* 注意
  * 排除重复元素
  * l~r的最大和与最小和与sum先进行比较，可以直接排除一些情况。

```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        for(int i = 0; i < n-3; i++)
            for(int j = i+1; j < n-2; j++){
                if(i != 0 && nums[i-1] == nums[i])
                    continue;
                if(j != i+1 && nums[j-1] == nums[j])
                    continue;
                int sum = target - nums[i] - nums[j];
                if(nums[j+1]+nums[j+2] > sum || nums[n-1]+nums[n-2] < sum)
                    continue;
                int l = j+1, r = n-1;
                while(l < r){
                    if(nums[l]+nums[r] == sum){
                        ans.add(Arrays.asList(nums[i],nums[j],nums[l],nums[r]));
                        l++;
                        r--;
                        while(l < r && nums[l] == nums[l-1])
                            l++;
                        while(l < r && nums[r] == nums[r+1])
                            r--;
                    }else if(nums[l]+nums[r] < sum)
                        l++;
                    else 
                        r--;
                }
            }
        return ans;
    }
}
```

## 26. 删除有序数组重复元素

* 快慢指针：慢指针左侧：无重复元素的数组，快指针：当前判断的元素
* 时间复杂度o(n)，空间复杂度：o(1)

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int t = 0;
        for(int i = 0; i < nums.length; i++) {
            if(i == 0 || nums[i] != nums[i-1]) nums[t++] = nums[i];
        }
        return t;
    }
}	
```

## 27.移除元素

* 快慢指针：慢指针左侧：无重复元素的数组，快指针：当前判断的元素
* 时间复杂度o(n)，空间复杂度：o(1)

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int t = 0;
        for(int num : nums) {
            if(num != val) nums[t++] = num;
        }
        return t;
    }
}
```



## 31. 下一个排列

* 字典序算法：
  * 从右到左找到第一个降序对，取左侧下标a
  * 从右到左找到第一个大于nums[a]的数，记录下标b
  * 交换a,b，并使a+1~n-1升序（反转即可，因为原始数组倒序）
  * 特殊情况：如果找不到a，说明原始数组已经是完全降序（最大字典序排列），根据题目要求反转数组即可）
* 时间复杂度：o(n)，空间复杂度o(1)

```java
class Solution {
    public void nextPermutation(int[] nums) {
        int a = -1, b = -1, n = nums.length;
        for(int i = n-1; i >= 1; i--)
            if(nums[i-1] < nums[i]){
                a = i-1;
                break;
            }
        if(a == -1){
            reverse(nums,0,n-1);
            return;
        }
        for(int i = n-1; i > a; i--)
            if(nums[i] > nums[a]){
                b = i;
                break;
            }
        swap(nums,a,b);
        reverse(nums,a+1,n-1);
        return;
    }
    
    private void swap(int[] nums, int a, int b){
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
    
    private void reverse(int[] nums, int l, int r){
        while(l < r){
            swap(nums,l,r);
            l++;
            r--;
        }
    }
}
```



## 33. 搜索旋转有序数组

### Solution 1

* 二分法找到旋转点pivot，然后在两个升序数组（0~pivot-1)和（pivot~n-1）中查找target。
* 注意
  * 考虑特殊情况：空集
  * 二分法循环能够退出证明：在保证l<r的情况下，mid始终小于r，从而区间总是不断缩小的，并且能保证pivot始终在区间能，最终退出时，l == r == pivot。
  * 根据target和nums[n-1]的关系，能够确定target在哪个升序数组。
* 时间复杂度：o(logn)，空间复杂度：o(1)

```java
class Solution {
    public int search(int[] nums, int target) {
        if(nums == null || nums.length == 0) return -1;
        int l = 0, r = nums.length-1, n = nums.length;
        while(l < r){
            int mid = (l+r)/2;
            if(nums[mid] <= nums[n-1]) r = mid;
            else l = mid+1;
        }
        int pivot = l;
        if(target <= nums[n-1])
            return binarySearch(nums,pivot,n-1,target);
        return binarySearch(nums,0,pivot-1,target);
    }
    
    private int binarySearch(int[] nums, int l, int r, int target){
        while(l <= r){
            int mid = (l+r)/2;
            if(nums[mid] == target) return mid;
            else if(nums[mid] > target) r = mid-1;
            else l = mid+1;
        }
        return -1;
    }
}
```

### Solution 2

* 二分法：先判断mid在左侧递增区间还是右侧递增区间，再根据target, nums[r] nums[mid]的关系，缩小l, r
* 时间复杂度：o(n)，空间复杂度o(1)。

```java
class Solution {
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while(l < r) {
            int mid = (l + r) / 2;
            if(nums[mid] > nums[r]) {
                if(nums[mid] >= target && target > nums[r]) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            } else {
                if(nums[mid] < target && target <= nums[r]) {
                    l = mid + 1;
                } else {
                    r = mid;
                }
            }
        }
        if(nums[l] != target) return -1;
        return l;
    }
}
```

## 35.搜索插入位置

* 二分法，循环退出时，l为大于target的第一个数，r为小target的第一个数。
* 时间复杂度：O(logn)，空间复杂度：o(1)

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        int l = 0, r = nums.length-1;
        while(l <= r){
            int mid = (l+r)/2;
            if(nums[mid] == target) return mid;
            else if(nums[mid] > target) r = mid-1;
            else l = mid+1;
        }
        return l;
    }
}
```

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        //找大于等于target的第一个数位置
        int l = 0, r = nums.length - 1;
        while(l < r) {
            int mid = (l + r) / 2;
            if(nums[mid] < target) l = mid + 1;
            else r = mid;
        }
        if(nums[l] < target) return l + 1;
        return l;
    }
}
```



## 39.组合总和

* 回溯法：排序数组，由于是组合问题和无限背包问题，需要设置start节点，start的子节点只能在start~n-1中选择，选择并递归后，记得撤销选择。
* 如何防止重复：随着DFS递归深度变大，每个节点能选择的路径是不断减小的，这由start保证。
* 时间复杂度：o(n^target)，空间复杂度：o(target)

```java
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if(candidates == null || candidates.length == 0) return ans;
        List<Integer> path = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates,0,target,path,ans);
        return ans;
    }
    private void dfs(int[] candidates, int start, int resident, List<Integer> path, List<List<Integer>> ans){
        if(resident == 0){
            ans.add(new ArrayList<>(path));
            return;
        }
        for(int i = start; i < candidates.length; i++){
            if(candidates[i] > resident) break;
            else{
                path.add(candidates[i]);
                dfs(candidates,i,resident-candidates[i],path,ans);
                path.remove(path.size()-1);
            }
        }
    }
}
```

1. DFS：类似于定长DFS，candidates[idx]有两种状态：选择和不被选择

```java
class Solution {
    List<Integer> path = new ArrayList<>();
    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        dfs(candidates, 0, 0, target);
        return ans;
    }

    private void dfs(int[] candidates, int idx, int sum, int target) {
        if(sum == target) {
            ans.add(new ArrayList<>(path));
            return;
        }

        if(sum + candidates[idx] <= target) {
            path.add(candidates[idx]);
            dfs(candidates, idx, sum + candidates[idx], target);
            path.remove(path.size() - 1);
        }

        if(idx + 1 < candidates.length) {
            dfs(candidates, idx + 1, sum, target);
        }
    }
}
```





## 40.组合总和Ⅱ

* 回溯法：
  * 重复值问题：路径选择时，重复的数值只能选一次
  * 有限背包问题：下一层的路径选择只能从 i+1~n-1
* 时间复杂度：min(2^n,n^target)，空间复杂度：o(target)

```java
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if(candidates == null || candidates.length == 0) return ans;
        List<Integer> path = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates,0,target,path,ans);
        return ans;
    }
    private void dfs(int[] candidates, int start, int resident, List<Integer> path, List<List<Integer>> ans){
        if(resident == 0){
            ans.add(new ArrayList<>(path));
            return;
        }
        for(int i = start; i < candidates.length; i++){
            if(candidates[i] > resident) break;
            else if(i != start && candidates[i] == candidates[i-1]) continue;
            else{
                path.add(candidates[i]);
                dfs(candidates,i+1,resident-candidates[i],path,ans);
                path.remove(path.size()-1);
            }
        }
    }
}
```

1. 定长DFS，每个节点有两种可能：选和不被选。
2. 去重：当前数和上一个数相同，且上一个数没被选择，当前数不能被选。

```java 
class Solution {
    List<Integer> path = new ArrayList<>();
    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        dfs(candidates, 0, 0, target, false);
        return ans;
    }

    private void dfs(int[] candidates, int idx, int sum, int target, boolean lastAdd) {
        if(sum == target) {
            ans.add(new ArrayList<>(path));
            return;
        }
        if(idx == candidates.length) return;
        //选择candidates[idx]
        if(sum + candidates[idx] <= target) {
            if(idx == 0 || candidates[idx] != candidates[idx - 1] || lastAdd) {
                path.add(candidates[idx]);
                dfs(candidates, idx + 1, sum + candidates[idx], target, true);
                path.remove(path.size() - 1);
            }
        }
        //不选择candidates[idx]
        dfs(candidates, idx + 1, sum, target, false);
    }
}
```



## 41.缺失的第一个正数

* 用负数表示idx + 1出现过。
* 对结果产生影响的，只有[1, N]之间的数，所以对于小于等于0的数，都可以替换为N+1。
* 注意，当数组全部为负数，说明1~n的正数全部存在，返回n+1.
* 时间复杂度：o(n)，空间复杂度：o(1)。

```java
class Solution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        if(n == 0) return 1;
        for(int i = 0; i < n; i++)
            if(nums[i] <= 0)
                nums[i] = n+1;
        for(int i = 0; i < n; i++){
            int index = Math.abs(nums[i])-1;
            if(index >= n) continue;
            if(nums[index] > 0)
                nums[index] = -1 * nums[index];
        }
        for(int i = 0; i < n; i++)
            if(nums[i] > 0)
                return i+1;
        return n+1;
    }
}
```

1. 将[1, N]之间的数，置换到正确位置，如果nums[idx] != idx + 1，则说明idx + 1没有出现过

```java
class Solution {
    public int firstMissingPositive(int[] nums) {
        for(int i = 0; i < nums.length; i++) {
            while(nums[i] >= 1 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                int tmp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = tmp;
            }
        }
        for(int i = 0; i < nums.length; i++)
            if(nums[i] != i + 1)
                return i + 1;
        return nums.length + 1;
    }
}
```

## 19.删除链表的倒数第N个节点

* 快慢指针：设置dummy节点，快指针先移动n个节点，随后快慢指针同时移动，直到快指针到达链表末尾
* 时间复杂度：o(n)，空间复杂度o(1)。

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null) return null;
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode slow = dummy, fast = dummy;
        while(n != 0){
            fast = fast.next;
            n--;
        }
        while(fast.next != null){
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }
}
```

## 21.合并两排序链表

* 递归：遍历问题经常会使用到递归，每次比较当前头节点l1.val和l2.val，选择较小值作为这两条链表合并后的头节点，并对头节点的下一节点递归。
* 时间复杂度：o(n)，空间复杂度：o(max(len1,len2))

```java
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        if(l1.val <= l2.val){
            l1.next = mergeTwoLists(l1.next,l2);
            return l1;
        }
        l2.next = mergeTwoLists(l1,l2.next);
        return l2;
    }
}
```

## 23.合并K个排序链表

### Solution 1

* 优先队列，能快速找到当前k个链表最小头节点。
* 时间复杂度：nlogk，空间复杂度：o(k)

```java
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists == null || lists.length == 0) return null;
        PriorityQueue<ListNode> pq = new PriorityQueue<>((o1,o2) -> o1.val-o2.val);
        for(ListNode node : lists)
            if(node != null)
                pq.offer(node);
        ListNode dummy = new ListNode();
        ListNode curNode = dummy;
        while(!pq.isEmpty()){
            curNode.next = pq.poll();
            curNode = curNode.next;
            if(curNode.next != null)
                pq.offer(curNode.next);
        }
        return dummy.next;
    }
}
```

### Solution 2

* 分治法：合并k条链表，可以链表两两合并，得到k/2条链表，k/2条链表再两两合并...最终得到一条链表
* 时间复杂度：nlogk，空间复杂度o(k)

```java
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length == 0) return null;
        return mergeKLists(lists,0,lists.length-1);
    }
    
    private ListNode mergeKLists(ListNode[] lists, int l, int r){
        if(l == r) return lists[l];
        int mid = (l+r)/2;
        ListNode l1 = mergeKLists(lists,l,mid);
        ListNode l2 = mergeKLists(lists,mid+1,r);
        return mergeTwoLists(l1,l2);
    }
    
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        if(l1.val <= l2.val){
            l1.next = mergeTwoLists(l1.next,l2);
            return l1;
        }
        l2.next = mergeTwoLists(l1,l2.next);
        return l2;
    }
}
```

## 24.两两交换链表节点

* 递归：当前节点指向下两个节点地递归返回链表，当前节点地下一个节点指向当前节点。
* 时间复杂度：o(n)，空间复杂度：o(n)

```java
class Solution {
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode n = head.next;
        head.next = swapPairs(head.next.next);
        n.next = head;
        return n;
    }
}
```

## 25.K个一组旋转链表

### Solution 1

* 递归：旋转前k个节点，然后将末尾节点指向剩余的旋转后的节点。
  * 注意这里用的旋转方法，使得旋转后的末尾节点指向了null，不过幸好curNode保存了剩余节点，利用递归更新
  * pre:已完成旋转的头节点，，cur待旋转节点，next，下一个待旋转节点
* 时间复杂度o(n)，空间复杂度：o(n/k)

```java
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode curNode = head;
        for(int i = 0; i < k; i++){
            if(curNode == null) return head;
            curNode = curNode.next;
        }
        ListNode newHead = reverse(head,k);
        head.next = reverseKGroup(curNode,k);	
        return newHead;
    }
    
    private ListNode reverse(ListNode head, int k){
        ListNode cur = head;
        ListNode pre = null;
        ListNode next = null;
        while(k != 0){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
            k--;
        }
        return pre;
    }
}
```

### Solution 2

* 迭代：使用三个指针pre,start,next，可以实现在链表中旋转k个节点
  * pre表示start位置之前，不用旋转（或者已经旋转了）的节点，start表示未旋转前的起始节点，next表示start后面，还没被旋转的第一个节点。
  * 首先，保存next后面的第一个节点（用start.next指向），然后将next插入到pre后面，此时next旋转完成，指向新的待旋转节点（start.next）
* 时间复杂度：o(n)，空间复杂度：o(1)
* 此方法可以旋转链表中的任意一小节的节点（指定旋转长度和旋转起始位置）

```java
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        int len = 0;
        for(ListNode cur = head; cur != null; cur = cur.next) len++;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        while(len >= k){
            ListNode start = pre.next;
            ListNode nex = start.next;
            for(int i = 0; i < k-1; i++){
                start.next = nex.next;
                nex.next = pre.next;
                pre.next = nex;
                nex = start.next;
            }
            pre = start;
            len -= k;
        }
        return dummy.next;
    }
}
```

## 61.旋转链表

* 在指定位置旋转链表
  * 确定链表尾节点和链表长度
  * 计算旋转点位置并确定旋转点
  * 利用dummy节点/链表尾节点/旋转点，进行链表旋转
* 时间复杂度：o(n)，空间复杂度：o(1)

```java
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null || head.next == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode rotateNode = dummy, tail = dummy;
        //确定尾节点和链表长度
        int len = 0;
        while(tail.next != null){
            tail = tail.next;
            len++;
        }
        //确定旋转节点
        k = len - k%len;
        for(int i = 0; i < k; i++)
            rotateNode = rotateNode.next;
        //进行旋转
        tail.next = dummy.next;
        dummy.next = rotateNode.next;
        rotateNode.next = null;
        return dummy.next;
    }
}
```

## 82.删除链表重复元素Ⅱ

### Solution 1

* 迭代：pre表示已经删除重复元素的尾节点，cur表示待判断节点，当cur为重复元素时，把cur及重复元素删除，更新cur，当cur不是重复元素时，更新pre和cur。
* 时间复杂度：o(n)，空间复杂度：o(1)

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy, cur = head;
        while(cur != null && cur.next != null){
            if(cur.next.val == cur.val){
                while(cur.next != null && cur.val == cur.next.val)
                    cur = cur.next;
                cur = cur.next;
                pre.next = cur;
            }else{
                pre = cur;
                cur = cur.next;
            }
        }
        return dummy.next;
    }
}
```

### Solution 2

* 递归：判断当前头节点是否为重复元素，是则删除重复元素，并判断删除后的下一元素，否则保留当前元素，判断下一元素
* 时间复杂度：O(n)，空间复杂度：o(n)

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null) return null;
        if(head.next != null && head.next.val == head.val){
            while(head.next != null && head.next.val == head.val)
                head = head.next;
            return deleteDuplicates(head.next);
        }
        head.next = deleteDuplicates(head.next);
        return head;
    }
}
```

## 83.删除链表重复元素

* 递归：如果当前链表头部有重复元素，则删除到只剩最后一个重复元素，不断递归。
* 时间复杂度：o(n)，空间复杂度:O(n)

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null) return null;
        while(head.next != null && head.val == head.next.val)
            head = head.next;
        head.next = deleteDuplicates(head.next);
        return head;
    }
}
```

## 160.两链表交叉节点

* 指针相遇：当指针a的路径为a+c+b，指针b的路径为b+c+a时，指针最终相遇相交点
* 注意：此题中到指针为null，则移动至另一链表的头节点，要把null当作节点来看，不能移动两步。

* 时间复杂度：O(len1+len2)，空间复杂度：O(1)

```java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode curA = headA, curB = headB;
        while(curA != curB){
            curA = curA == null ? headB : curA.next;
            curB = curB == null ? headA : curB.next;
        }
        return curA;
    }
}
```

## 206.反转链表

### Solution 1

* 迭代，pre指向已经反转好的链表头节点，next指向下一个需要反转的节点，cur指向当前正在反转的节点
* 时间复杂度：O(n)，空间复杂度：O(1)

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode cur = head, pre = null, next = null;
        while(cur != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
```

### Solution 2

* 递归，每次递归返回已经递归函数的头节点，由于递归完head.next后，head.next为反转链表的尾节点，将head添加到head.next后面，然后将此head的next设置为null，作为新的尾节点。
* 时间复杂度：O(n)，空间复杂度：O(n)

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}
```

## 445.两数相加（二）

### Solution 1

* 反转链表，遇到null用0节点代替。
* 由尾到头构建链表
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        l1 = reverseList(l1);
        l2 = reverseList(l2);
        ListNode head = null;
        int carry = 0;
        while(l1 != null || l2 != null || carry != 0){
            int a = l1 == null ? 0 : l1.val;
            int b = l2 == null ? 0 : l2.val;
            int value = (a+b+carry) % 10;
            carry = (a+b+carry) / 10;
            ListNode newHead = new ListNode(value);
            newHead.next = head;
            head = newHead;
            if(l1 != null) l1 = l1.next;
            if(l2 != null) l2 = l2.next;
        }
        return head;
    }
    
    private ListNode reverseList(ListNode head){
        ListNode pre = null, cur = head, next = null;
        while(cur != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
```

### Soluton 2

* 将l1和l2中的所有数入栈，栈顶元素为低位元素，栈中元素取完，用0代替
* 由尾向头构建链表
* 时间复杂度：O(n)，空间复杂度:O(n)

```java
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        Stack<Integer> digits1 = new Stack<>();
        Stack<Integer> digits2 = new Stack<>();
        while(l1 != null){
            digits1.push(l1.val);
            l1 = l1.next;
        }
        while(l2 != null){
            digits2.push(l2.val);
            l2 = l2.next;
        }
        ListNode head = null;
        int carry = 0;
        while(!digits1.isEmpty() || !digits2.isEmpty() || carry != 0){
            int a = digits1.isEmpty() ? 0 : digits1.pop();
            int b = digits2.isEmpty() ? 0 : digits2.pop();
            int val = (a + b + carry) % 10;
            carry = (a + b + carry) / 10;
            ListNode newHead = new ListNode(val);
            newHead.next = head;
            head = newHead;
        }
        return head;
    }
}
```

## 234.判断链表是否为回文

### Solution 1

* 折半+反转
* 时间复杂度：o(n)，空间复杂度：o(1)

```java
class Solution {
    public boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null) return true;
        ListNode slow = head, fast = head;
        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode l2 = slow.next;
        l2 = reverseList(l2);
        while(l2 != null && head.val == l2.val){
            head = head.next;
            l2 = l2.next;
        }
        return l2 == null;
    }
    
    private ListNode reverseList(ListNode head){
        ListNode pre = null, cur = head, next = null;
        while(cur != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
```

### Solution 2

* 递归，其中frontNode存储与当前节点对称的节点
* 时间复杂度：O(n)，空间复杂度：O(n)

```java
class Solution {
    private ListNode frontNode;
    
    public boolean isPalindrome(ListNode head) {
        frontNode = head;
        return recursivelyCheck(head);
    }
    
    private boolean recursivelyCheck(ListNode curNode){
        if(curNode == null) return true;
        if(!recursivelyCheck(curNode.next)) return false;
        if(curNode.val != frontNode.val) return false;
        frontNode = frontNode.next;
        return true;
    }
}
```

## 725.将链表分割成k等份

* 计算出每份长度，遍历后切割.
* 时间复杂度：O(n+k)，因为K很大，需要循环很多次创建null。空间复杂度：O(k)

```java
class Solution {
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode cur = root;
        int len = 0;
        while(cur != null){
            len++;
            cur = cur.next;
        }
        int base = len/k, remainder = len%k;
        cur = root;
        ListNode[] ans = new ListNode[k];
        for(int i = 0; i < k; i++){
            int count = i < remainder ? base+1 : base;
            if(count == 0) ans[i] = null;
            else{
                ans[i] = cur;
                for(int j = 0; j < count-1; j++)
                    cur = cur.next;
                ListNode temp = cur.next;
                cur.next = null;
                cur = temp;
            }
        }
        return ans;
    }
}
```

## 328.奇偶链表

* 由于oddCur.next为evenCur，所以执行oddCur.next = oddCur.next.next时，不会出现指针丢失
* 时间复杂度O(n)，空间复杂度O(1)·

```java
class Solution {
    public ListNode oddEvenList(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode evenHead = head.next;
        ListNode oddCur = head, evenCur = evenHead;
        while(evenCur != null && evenCur.next != null){
            oddCur.next = oddCur.next.next;
            oddCur = oddCur.next;
            evenCur.next = evenCur.next.next;
            evenCur = evenCur.next;
        }
        oddCur.next = evenHead;
        return head;
    }
}
```

## 104.二叉树深度

### Solution 1

* DFS
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public int maxDepth(TreeNode root) {
        return dfs(root);
    }
    private int dfs(TreeNode root){
        if(root == null) return 0;
        int leftDepth  = dfs(root.left);
        int rightDepth = dfs(root.right);
        return Math.max(leftDepth,rightDepth) + 1;
    }
}
```

### Solution 2

* BFS
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public int maxDepth(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        int depth = 0;
        if(root != null) queue.offer(root);
        while(!queue.isEmpty()){
            depth++;
            int size = queue.size();
            for(int i = 0; i < size; i++){
                TreeNode cur = queue.poll();
                if(cur.left  != null) queue.offer(cur.left);
                if(cur.right != null) queue.offer(cur.right);
            }
        }
        return depth;
    }
}
```

## 110.判断平衡二叉树

* DFS
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public boolean isBalanced(TreeNode root) {
        int ans = dfs(root);
        return ans == -1 ? false : true;
    }
    
    private int dfs(TreeNode root){
        if(root == null) return 0;
        int leftHeight  = dfs(root.left);
        int rightHeight = dfs(root.right);
        if(leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight-rightHeight) > 1) return -1;
        return Math.max(leftHeight,rightHeight)+1;
    }
}
```

## 543.二叉树的直径

* DFS/最大高度：一某一节点node为根的最长路径为leftHeight+rightHeight，由于任何一条路径有且仅有一个根节点，遍历每个节点，找出以它为根的最长路径，比较。
* 时间复杂度O(n)，空间复杂度O(N)

```java
class Solution {
    private int ans;
    public int diameterOfBinaryTree(TreeNode root) {
        ans = 0;
        dfs(root);
        return ans;
    }
    private int dfs(TreeNode root){
        if(root == null) return 0;
        int leftHeight  = dfs(root.left);
        int rightHeight = dfs(root.right);
        ans = Math.max(leftHeight+rightHeight,ans);
        return Math.max(leftHeight,rightHeight)+1;
    }
}
```

## 226.反转二叉树

* 递归
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if(root == null) return null;
        TreeNode leftNode  = invertTree(root.left);
        TreeNode rightNode = invertTree(root.right);
        root.left = rightNode;
        root.right = leftNode;
        return root;
    }
}
```

也可以用stack版DFS，以及BFS实现

## 617.合并二叉树

* 递归，如果为空树，则返回另一棵树
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if(t1 == null && t2 == null) return null;
        if(t1 == null) t1 = new TreeNode(0);
        if(t2 == null) t2 = new TreeNode(0);
        TreeNode t = new TreeNode(t1.val + t2.val);
        t.left  = mergeTrees(t1.left,t2.left);
        t.right = mergeTrees(t1.right,t2.right);
        return t;
    }
}
```

## 112.路径和

* 递归，判断当前子树是否为叶子节点，不过不是，判断左右子树是否有路径和为目标值
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null) return false;
        if(root.left == null && root.right == null) return sum == root.val;
        return hasPathSum(root.left,sum-root.val) || hasPathSum(root.right,sum-root.val);
    }
}
```

## 437.路径和Ⅲ

### Solution 1

* 回溯，path保存路径，求当前节点和为sum的路径数量
* 时间复杂度O(n^2)，空间复杂度O(n)

```java
class Solution {
    private int ans;
    public int pathSum(TreeNode root, int sum) {
        int[] path = new int[1000];
        ans = 0;
        dfs(root,path,0,sum);
        return ans;
    }
    private void dfs(TreeNode root, int[] path, int level, int sum){
        if(root == null) return;
        path[level] = root.val;
        int curSum = 0;
        for(int i = level; i >= 0; i--){
            curSum += path[i];
            if(curSum == sum) ans++;
        }
        dfs(root.left,path,level+1,sum);
        dfs(root.right,path,level+1,sum);
    }
}
```

### Solution 2

* 递归，当前树的路径和，等于从根节点出发的路径和+左子树路径和+右子树路径和
* 时间复杂度O(n^2)，空间复杂度O(n)
* 时间复杂度相同，为什么时间差这么多。

```java
class Solution {
    public int pathSum(TreeNode root, int sum) {
        if(root == null) return 0;
        return pathFrom(root,sum) + pathSum(root.left,sum) + pathSum(root.right,sum);
    }
    
    private int pathFrom(TreeNode root, int sum){
        if(root == null) return 0;
        return (root.val == sum ? 1 : 0) + pathFrom(root.left,sum-root.val) + pathFrom(root.right,sum-root.val);
    }
}
```

### Solution 3

* 递归/前缀和，判断以当前节点结尾的路径和数量，已经左右子树的路径和数量
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public int pathSum(TreeNode root, int sum) {
        HashMap<Integer,Integer> preSums = new HashMap<>();
        preSums.put(0,1);
        return dfs(root,preSums,0,sum);
    }
    
    private int dfs(TreeNode root, HashMap<Integer,Integer> preSums, int curSum, int target){
        if(root == null) return 0;
        curSum += root.val;
        int ans = preSums.getOrDefault(curSum-target,0);
        preSums.put(curSum,preSums.getOrDefault(curSum,0)+1);
        ans += dfs(root.left, preSums,curSum,target);
        ans += dfs(root.right,preSums,curSum,target);
        preSums.put(curSum,preSums.get(curSum)-1);
        return ans;
    }
}
```

## 101.镜像树

### Solution 1

* 递归，判断两树根节点值，以及树1的左子树和树2的右子树对称，树1的右子树和树2的左子树对称
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        return isMirror(root,root);
    }
    
    private boolean isMirror(TreeNode root1, TreeNode root2){
        if(root1 == null && root2 == null) return true;
        if(root1 == null || root2 == null) return false;
        return (root1.val == root2.val) && isMirror(root1.left,root2.right) && isMirror(root1.right,root2.left);
    }
}
```

### Solution 2

* stack，将镜像的两棵树依次入栈，每次出栈两棵树，判断是否镜像
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if(root == null) return true;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode root1 = stack.pop();
            TreeNode root2 = stack.pop();
            if(root1 == null && root2 == null) continue;
            if(root1 == null || root2 == null) return false;
            if(root1.val != root2.val) return false;
            stack.push(root1.left);
            stack.push(root2.right);
            stack.push(root1.right);
            stack.push(root2.left);
        }
        return true;
    }
}
```

## 572.子树

* 递归，当前t是否为s的子树，包括1）t是以s为根的子树。2）t是s的左子树的子树。3）t是s的右子树的子树
* 时间复杂度O(n^2),空间复杂度O(n)
* 不要看错题目，情况1为，t和s完全相同

```java
class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(t == null) return true;
        if(s == null) return false;
        return isSubtreeFrom(s,t) || isSubtree(s.left,t) || isSubtree(s.right,t);
    }
    
    private boolean isSubtreeFrom(TreeNode s, TreeNode t){
        if(t == null && s == null) return true;
        if(s == null || t == null) return false;
        if(s.val != t.val) return false;
        return isSubtreeFrom(s.left,t.left) && isSubtreeFrom(s.right,t.right);
    }
}
```

## 111.最浅深度

### Solution 1

* DFS
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public int minDepth(TreeNode root) {
        if(root == null) return 0;
        int leftMin  = minDepth(root.left);
        int rightMin = minDepth(root.right);
        return (leftMin == 0 || rightMin == 0) ? leftMin + rightMin + 1 : Math.min(leftMin,rightMin) + 1;
    }
}
```

### Soluton 2

* BFS
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public int minDepth(TreeNode root) {
        if(root == null) return 0;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i = 0; i < size; i++){
                TreeNode cur = queue.poll();
                if(cur.left == null && cur.right == null) return depth+1;
                if(cur.left  != null) queue.offer(cur.left);
                if(cur.right != null) queue.offer(cur.right);
            }
            depth++;
        }
        return -1;
    }
}
```

## 404.左叶子之和

* DFS
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public int sumOfLeftLeaves(TreeNode root) {
        if(root == null) return 0;
        int ans = 0;
        if(root.left != null && root.left.left == null && root.left.right == null) ans += root.left.val;
        ans += sumOfLeftLeaves(root.left);
        ans += sumOfLeftLeaves(root.right);
        return ans;
    }
}
```

## 687.最长等值路径

* DFS，返回值为以当前节点起始的最大等值深度，显然当前节点为根的最长等值路径，为左子树的返回值加上右子树的返回值（当左右节点的值等于当前节点）
* 时间复杂度：O(N)，空间复杂度O(N)

```java
class Solution {
    private int ans;
    public int longestUnivaluePath(TreeNode root) {
        ans = 0;
        longestUnivalueDepthFrom(root);
        return ans;
    }
    
    private int longestUnivalueDepthFrom(TreeNode root){
        if(root == null) return 0;
        int leftDepth = 0, rightDepth = 0;
        leftDepth = longestUnivalueDepthFrom(root.left);
        rightDepth = longestUnivalueDepthFrom(root.right);
        if(root.left != null && root.left.val != root.val) leftDepth = 0;
        if(root.right != null && root.right.val != root.val) rightDepth = 0;
        ans = Math.max(ans,leftDepth+rightDepth);
        return Math.max(leftDepth,rightDepth)+1;
    }
}
```

## 124.最长路径和

* DFS，返回值为以当前节点起始的最大值路径。
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    private int ans;
    public int maxPathSum(TreeNode root) {
        ans = Integer.MIN_VALUE;
        dfs(root);
        return ans;
    }
    
    private int dfs(TreeNode root){
        if(root == null) return 0;
        int leftSum  = dfs(root.left);
        int rightSum = dfs(root.right);
        leftSum  = leftSum  > 0 ? leftSum  : 0;
        rightSum = rightSum > 0 ? rightSum : 0;
        ans = Math.max(ans, leftSum + rightSum + root.val);
        return Math.max(leftSum, rightSum) + root.val;
    }
}
```

## 198.打家劫舍Ⅰ

* dp\[i]\[0]:表示第i个房子选择打劫，能获得的最大财富，dp\[i]\[1]:表示第i的房子选择不打劫，能获得的最大财富
* 时间复杂度：O(n)，空间复杂度：O(1)

```java
class Solution {
    public int rob(int[] nums) {
        if(nums.length == 0) return 0;
        int pre0 = nums[0], pre1 = 0;
        for(int i = 1; i < nums.length; i++){
            int cur0 = pre1 + nums[i];
            int cur1 = Math.max(pre0, pre1);
            pre0 = cur0;
            pre1 = cur1;
        }
        return Math.max(pre0, pre1);
    }
}
```

## 213.打家劫舍Ⅱ

* 与前一题的状态转移方程相同，初始情况较复杂
  * 第一栋房子不打劫，则从第二栋房子开始选择打劫与不打劫
  * 第一栋房子打劫，则第二栋一定不能打劫，从第三栋房子开始选择打劫与不打劫，返回最后一栋房子不被打劫的最大值
* 时间复杂度O(n)，空间复杂度O(1)

```java
class Solution {
    public int rob(int[] nums) {
        if(nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];
        if(nums.length == 2) return Math.max(nums[0], nums[1]);
        int pre0 = nums[2], pre1 = 0;
        for(int i = 3; i < nums.length; i++){
            int cur0 = pre1 + nums[i];
            int cur1 = Math.max(pre0, pre1);
            pre0 = cur0;
            pre1 = cur1;
        }
        int ans1 = pre1 + nums[0];
        pre0 = nums[1]; 
        pre1 = 0;
        for(int i = 2; i < nums.length; i++){
            int cur0 = pre1 + nums[i];
            int cur1 = Math.max(pre0, pre1);
            pre0 = cur0;
            pre1 = cur1;
        }
        int ans2 = Math.max(pre0, pre1);
        return Math.max(ans1, ans2);
    }
}
```

* 也可以这样想：首尾的状态有如下两种，因为首尾元素不能同时被打劫，所以必须有一个元素不能被打劫
  * 首元素不被打劫
  * 尾元素不被打劫

```java
class Solution {
    public int rob(int[] nums) {
        if(nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];
        return Math.max(rob(nums,0,nums.length-2), rob(nums,1,nums.length-1));
    }
    
    private int rob(int[] nums, int l, int r){
        int pre0 = nums[l], pre1 = 0;
        for(int i = l+1; i <= r; i++){
            int cur0 = pre1 + nums[i];
            int cur1 = Math.max(pre0, pre1);
            pre0 = cur0;
            pre1 = cur1;
        }
        return Math.max(pre0, pre1);
    }
}
```

## 337.打家劫舍Ⅲ

* DFS，返回值，ans[0]，表示不打劫根节点的最大财富，ans[1]，表示打劫根节点的最大财富
* 时间复杂度O(N)，空间复杂度O(n)

```java
class Solution {
    public int rob(TreeNode root) {
        int[] ans = dfs(root);
        return Math.max(ans[0], ans[1]);
    }
    
    private int[] dfs(TreeNode root){
        if(root == null) return new int[]{0, 0};
        int[] leftAns  = dfs(root.left);
        int[] rightAns = dfs(root.right);
        int ans0 = leftAns[1] + rightAns[1] + root.val;
        int ans1 = Math.max(leftAns[0], leftAns[1]) + Math.max(rightAns[0], rightAns[1]);
        return new int[]{ans0, ans1};
    }
}
```

## 671.二叉树第二小元素

* DFS，返回值，当前子树的第二小元素
  * 如果叶子节点为null，则不存在第二小元素，返回-1
  * 如果叶子节点非null，分为两种情况
    * 左右叶子与根节点的值相等，此时在左右子树接着寻找第二小元素
    * 左右叶子与根节点的值不等，一定有一个子树的节点值等于根节点，在那棵子树上找第二小元素
  * 总结：在等于root.val的子树上找第二小元素，不等于root.val的元素一定是大于他的元素，作为替补保存即可。
* 时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public int findSecondMinimumValue(TreeNode root) {
        if(root.left == null) return -1;
        int l = root.left.val  == root.val ? findSecondMinimumValue(root.left)  : root.left.val;
        int r = root.right.val == root.val ? findSecondMinimumValue(root.right) : root.right.val;
        return (l == -1 || r == -1) ? Math.max(l,r) : Math.min(l,r);
    }
}
```

## 637.二叉树层平均值

* BFS，用sum记录每层节点的和
* 时间复杂度O(n)，空间复杂度O(m)，m为每层最大节点数

```java
class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> ans = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            double sum = 0.0;
            for(int i = 0; i < size; i++){
                TreeNode cur = queue.poll();
                sum += cur.val;
                if(cur.left  != null) queue.offer(cur.left);
                if(cur.right != null) queue.offer(cur.right);
            }
            ans.add(sum/size);
        }
        return ans;
    }
}
```

## 513.二叉树的最左下节点

* BFS，从右到左遍历，最后遍历的节点即为左下角节点。
* 时间复杂度O(n)，空间复杂度O(m)

```java
class Solution {
    public int findBottomLeftValue(TreeNode root) {
        TreeNode cur = null;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            cur = queue.poll();
            if(cur.right != null) queue.offer(cur.right);
            if(cur.left  != null) queue.offer(cur.left);
        }
        return cur.val;
    }
}
```

## 94.二叉树中序遍历

### Solution 1

* DFS，inorder
* O(n)/O(n)

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        inorder(root,ans);
        return ans;
    }
    
    private void inorder(TreeNode root, List<Integer> ans){
        if(root == null) return;
        inorder(root.left,ans);
        ans.add(root.val);
        inorder(root.right,ans);
    }
}
```

### Solution 2

* stack，在当前树入栈后，其所有的左子树都得入栈，最后栈顶得到当前树的最左节点
* O(n)/O(n)

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while(cur != null || !stack.isEmpty()){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            ans.add(cur.val);
            cur = cur.right;
        }
        return ans;
    }
}
```

### Solution 3

* 找当前节点cur前驱节点pre，将pre的下一节点连在cur，cur往前一步移动，知道没有前驱节点
* 不要成环，每此移动完cur，需要将cur.left变为null
* O(n)/O(1)

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        TreeNode cur = root;
        while(cur != null){
            if(cur.left == null){
                ans.add(cur.val);
                cur = cur.right;
                continue;
            }
            TreeNode pre = cur.left;
            while(pre.right != null)
                pre = pre.right;
            pre.right = cur;
            cur = cur.left;
            pre.right.left = null;
        }
        return ans;
    }
}
```

## 144.二叉树前序遍历

### Solution 1

* DFS
* O(n)/O(n)

```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        preorder(root,ans);
        return ans;
    }
    
    private void preorder(TreeNode root, List<Integer> ans){
        if(root == null) return;
        ans.add(root.val);
        preorder(root.left,ans);
        preorder(root.right,ans);
    }
}
```

### Solution 2

* stack,注意入栈顺序，右子树先入栈，左子树后入栈
* O(N)/O(N)

```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if(root == null) return ans;
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode cur = stack.pop();
            ans.add(cur.val);
            if(cur.right != null) stack.push(cur.right);
            if(cur.left  != null) stack.push(cur.left);
        }
        return ans;
    }
}
```

### Solution 3

* Morris解法，找到cur.right的前驱节点。(注意防止成环)
* O(n)/O(1)

```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        TreeNode cur = root;
        while(cur != null){
            ans.add(cur.val);
            if(cur.left == null){
                cur = cur.right;
            }else{
                TreeNode pre = cur.left;
                while(pre.right != null)
                    pre = pre.right;
                pre.right = cur.right;
                cur.right = null;
                cur = cur.left;
            }
        }
        return ans;
    }
}
```

## 145.二叉树后序遍历

### Solution 1

* DFS
* O(n)/O(n)

```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        postorder(root,ans);
        return ans;
    }
    
    private void postorder(TreeNode root, List<Integer> ans){
        if(root == null) return;
        postorder(root.left, ans);
        postorder(root.right, ans);
        ans.add(root.val);
    }
}
```

### Solution 2

* stack，根节点入栈后添加一个null节点，用来标识，如果此时stack.peek()为null，表明到达了一个根节点，可以pop，否则，需要先遍历左右子树。
* Stack的难点在于，位于stack.peek()的元素该不该pop呢，如果左右子树已经完成遍历，可以pop，否则，得先将左右子树遍历完成
* O(n)/O(n)

```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if(root == null) return ans;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode cur = stack.peek();
            if(cur == null){
                stack.pop();
                ans.add(stack.peek().val);
                stack.pop();
                continue;
            }
            stack.push(null);
            if(cur.right != null) stack.push(cur.right);
            if(cur.left != null) stack.push(cur.left);
        }
        return ans;
    }
}
```

### Solution 3

* stack，用pre来标记上一次访问的节点，如果上次访问的节点刚好是右节点，说明此节点的右子树已经访问，pop，否则需要先访问右子树。
* stack.peek()的右子树是否遍历完成？出栈 ： 右子树入栈
* O(n)/O(n)

```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        TreeNode pre = null;
        while(cur != null || !stack.isEmpty()){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.peek();
            if(cur.right == null || cur.right == pre){
                stack.pop();
                ans.add(cur.val);
                pre = cur;
                cur = null;
            }else{
                cur = cur.right;
            }
        }
        return ans;
    }
}
```

### Solution 4

* stack，按照中 ---> 右 --->左的顺序遍历，然后反转
* O(n)/O(n)

```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if(root == null) return ans;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            root = stack.pop();
            ans.add(root.val);
            if(root.left  != null) stack.push(root.left);
            if(root.right != null) stack.push(root.right);
        }
        Collections.reverse(ans);
        return ans;
    }
}
```

## 699.修剪BST

* 递归，根据BST的性质进行修剪
* O(n)/O(n)

```java
class Solution {
    public TreeNode trimBST(TreeNode root, int L, int R) {
        if(root == null) return null;
        if(root.val > R){
            return trimBST(root.left,L,R);
        }else if(root.val < L){
            return trimBST(root.right,L,R);
        }
        root.left  = trimBST(root.left,L,R);
        root.right = trimBST(root.right,L,R);
        return root;
    }
}
```

## 230.BST的第k小元素

### Solution 1

* inorder
* O(n)/O(n)

```java
class Solution {
    private int count;
    public int kthSmallest(TreeNode root, int k) {
        count = 0;
        return inorder(root,k);
    }
    
    private int inorder(TreeNode root, int k){
        if(root == null) return -1;
        int left = inorder(root.left,k);
        if(left != -1) return left;
        count++;
        if(count == k) return root.val;
        else return inorder(root.right, k);
    }
}
```

### Solution 2

* 记录每个节点的子节点个数
* 创建新树:O(n)，插入/删除：O(h)，查找O(h)，h为二叉树的高度

```java
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        TreeNode countRoot = buildCountTree(root);
        return kthSmallest(root,countRoot,k);
    }
    
    private TreeNode buildCountTree(TreeNode root){
        if(root == null) return null;
        TreeNode countNode = new TreeNode();
        countNode.left  = buildCountTree(root.left);
        countNode.right = buildCountTree(root.right);
        countNode.val = 1 + (countNode.left == null ? 0 : countNode.left.val)
            + (countNode.right == null ? 0 : countNode.right.val);
        return countNode;
    }
    
    private int kthSmallest(TreeNode root, TreeNode count, int k){
        int curPos = count.left == null ? 1 : count.left.val + 1;
        if(curPos >  k) return kthSmallest(root.left,count.left,k);
        else if(curPos < k) return kthSmallest(root.right,count.right,k-curPos);
        return root.val;
    }
}
```

## 538.BST转换为累加树

* 不使用全局变量，中序遍历，其中curSum存储着当前已经遍历的比root.val大的元素，返回值表示当前整棵树遍历完后的累加值。
* O(n)/O(n)

```java
class Solution {
    public TreeNode convertBST(TreeNode root) {
        inorder(root,0);
        return root;
    }

    private int inorder(TreeNode root, int curSum){
        if(root == null) return curSum;
        int rightSum = inorder(root.right, curSum);
        root.val += rightSum;
        int leftSum = inorder(root.left, root.val);
        return leftSum;
    }
}
```

## 235.BST最近公共祖先

* 性质：两节点的最近公共祖先node，满足：唯一一个两节点出现在node的左右子树（或者node == p || node == q)
* O(h)/O(1)

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while((root.val - p.val) * (root.val - q.val) > 0)
            root = root.val > q.val ? root.left : root.right;
        return root;
    }
}
```

## 236.二叉树的最近公共祖先

* 如果某节点的左子树能找到p,q中的一个，右节点能找到p,q中的一个，则此节点为LCA
* 如果左子树返回了非null，而右子树为Null,则说明LCA一定是左子树的返回值
* O(n)/O(n)

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q) return root;
        TreeNode left  = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if(left != null && right != null) return root;
        return left == null ? right : left;
    }
}
```

## 108.有序数组转BST

* 递归
* O(n)/O(logn)

```java
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        int l = 0, r = nums.length-1;
        return sortedArrayToBST(nums, l, r);
    }
    
    private TreeNode sortedArrayToBST(int[] nums, int l, int r){
        if(l > r) return null;
        int mid = (l + r) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left  = sortedArrayToBST(nums, l, mid-1);
        root.right = sortedArrayToBST(nums, mid+1, r);
        return root;
    }
}
```

>也可以用迭代法，看[这里](https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/)

## 109.有序链表转BST

* 递归
* O(n)/O(logn)

```java
class Solution {
    private ListNode node;
    public TreeNode sortedListToBST(ListNode head) {
        int size = 0;
        ListNode cur = head;
        while(cur != null){
            size++;
            cur = cur.next;
        }
        int l = 0, r = size-1;
        node = head;
        return inorder(l,r);
    }
    
    private TreeNode inorder(int l, int r){
        if(l > r) return null;
        int mid = (l + r) / 2;
        TreeNode left = inorder(l, mid-1);
        TreeNode root  = new TreeNode(node.val);
        node = node.next;
        TreeNode right = inorder(mid+1, r);
        root.left  = left;
        root.right = right;
        return root;
    }
}
```

## 635.两数之和（BST）

### Solution 1

* 有序数组 + 左右指针
* O(n)/O(n)

```java
class Solution {
    public boolean findTarget(TreeNode root, int k) {
        ArrayList<Integer> sorted = new ArrayList<>();
        inorder(root, sorted);
        int l = 0, r = sorted.size()-1;
        while(l < r){
            int sum = sorted.get(l) + sorted.get(r);
            if(sum == k) return true;
            else if(sum < k) l++;
            else r--;
        }
        return false;
    }
    
    private void inorder(TreeNode root, ArrayList<Integer> sorted){
        if(root == null) return;
        inorder(root.left, sorted);
        sorted.add(root.val);
        inorder(root.right, sorted);
    }
}
```

### Solution 2

* Hashset
* O(n)/O(n)

```java
class Solution {
    HashSet hs=new HashSet();
    public boolean findTarget(TreeNode root, int k) {
        if(root==null) return false;
        else if(hs.contains(root.val))return true;
        hs.add(k-root.val);
        return findTarget(root.left,k)||findTarget(root.right,k);
    }
}
```

## 8.字符串转换整数atoi

### Solution 1

* 翻译题目，考虑如下情况
  * 去除前面空格
  * 判断是否正负开头
  * 转换为整数
* O(n)/O(n)

```java
class Solution {
    public int myAtoi(String str) {
        if(str.equals("")) return 0;
        char[] chars = str.toCharArray();
        int start = 0;
        while(start < chars.length && chars[start] == ' ')
            start++;
        if(start == chars.length)
            return 0;
        if(chars[start] == '-')
            return convertToInteger(chars, start+1, true);
        if(chars[start] == '+')
            return convertToInteger(chars, start+1, false);
        return convertToInteger(chars, start, false);
        
    }

    private int convertToInteger(char[] chars, int start, boolean isNagtive){
        long ans = 0;
        while(start < chars.length && chars[start] >= '0' && chars[start] <= '9'){
            int digit = chars[start] - '0';
            ans = ans * 10 + digit;
            start++;
            if(isNagtive && -ans < Integer.MIN_VALUE)
                return Integer.MIN_VALUE;
            if(!isNagtive && ans > Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
        }
        return isNagtive ? (int)-ans : (int)ans;
    }
}
```

### Solution 2

* DFA，状态转换如下：

|        | start | number | sign | end  |
| ------ | ----- | ------ | ---- | ---- |
| start  | start | number | sign | end  |
| number | end   | number | end  | end  |
| sign   | end   | number | end  | end  |
| end    | end   | end    | end  | end  |

* O(n)/O(n)

```java
class Solution {
    public int myAtoi(String str) {
        DFA dfa = new DFA();
        char[] chars = str.toCharArray();
        for(char c : chars){
            dfa.jump(c);
        }
        return dfa.positive ? dfa.ans : -dfa.ans;
    }
}

public class DFA{
    int ans;
    boolean positive;
    int curState;
    int[][] map;
    public DFA(){
        ans = 0;
        map = new int[4][];
        curState = 0;
        positive = true;
        map[0] = new int[]{0,1,2,3};
        map[1] = new int[]{3,1,3,3};
        map[2] = new int[]{3,1,3,3};
        map[3] = new int[]{3,3,3,3};
    }

    /*
        "start":0
        "number":1
        "sign":2
        "end":3
    */
    private int getCol(char c){
        if(c == ' ') return 0;
        if(c >= '0' && c <= '9') return 1;
        if(c == '+' || c == '-') return 2;
        return 3;
    }

    public void jump(char c){
        curState = map[curState][getCol(c)];
        if(curState == 1){
            int digit = c - '0';
            if(ans > Integer.MAX_VALUE/10 || (ans == Integer.MAX_VALUE/10 && digit > 7)){
                ans = positive ?  Integer.MAX_VALUE : Integer.MIN_VALUE;
                curState = 3;
            }  
            else
                ans = ans * 10 + digit;   
        }
        if(curState == 2)
            positive = c == '+' ? true : false;
    }
}
```

## 530.BST最小绝对差

* inorder
* O(N)/O(N)

```java
class Solution {
    int ans;
    TreeNode pre;
    public int getMinimumDifference(TreeNode root) {
        ans = Integer.MAX_VALUE;
        pre = null;
        inorder(root);
        return ans;
    }
    
    private void inorder(TreeNode root){
        if(root == null) return;
        inorder(root.left);
        if(pre != null) ans = Math.min(ans, root.val - pre.val);
        pre = root;
        inorder(root.right);
    }
}
```

> 可以使用int[]{pre, ans}来封装全局变量，在递归中传递

## 501.BST众数

* inorder, curCount：当前数出现的次数，maxCount：已遍历数的最多次数， preNode：前一节点
* 注意：不能用list.toArray(new int[size])来转成数组。
* O(n)/o(n)

```java
class Solution {
    int maxCount, curCount;
    TreeNode preNode;
    public int[] findMode(TreeNode root) {
        curCount = 0;
        maxCount = 0;
        ArrayList<Integer> ans = new ArrayList<>();
        inorder(root, ans);
        int[] ansArr = new int[ans.size()];
        for(int i = 0; i < ans.size(); i++)
            ansArr[i] = ans.get(i);
        return ansArr;
    }
    
    private void inorder(TreeNode root, ArrayList<Integer> ans){
        if(root == null) return;
        inorder(root.left, ans);
        if(preNode != null && preNode.val == root.val)
            curCount++;
        else
            curCount = 1;
        if(curCount == maxCount) ans.add(root.val);
        else if(curCount > maxCount){
            maxCount = curCount;
            ans.clear();
            ans.add(root.val);
        }
        preNode = root;
        inorder(root.right, ans);
    }
}
```

## 208.实现trie

* 多叉树的实现
* 查找效率取决于单词长度

```java
class Trie {
    TrieNode root;
    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        insert(word, root, 0);
    }
    
    private void insert(String word, TrieNode root, int idx){
        if(idx >= word.length()) return;
        char c = word.charAt(idx);
        if(root.children[c-'a'] == null)
            root.children[c-'a'] = new TrieNode();
        if(idx == word.length()-1) root.children[c-'a'].isEnd = true;
        insert(word, root.children[c-'a'], idx+1);
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        return search(word, root, 0);
    }
    
    private boolean search(String word, TrieNode node, int idx){
        if(idx == word.length() || node == null) return false;
        char c = word.charAt(idx);
        if(node.children[c-'a'] == null) return false;
        if(idx == word.length()-1 && node.children[c-'a'].isEnd) return true;
        return search(word, node.children[c-'a'], idx+1);
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return startsWith(prefix, root, 0);
    }
    
    private boolean startsWith(String prefix, TrieNode node, int idx){
        if(node == null) return false;
        char c = prefix.charAt(idx);
        if(node.children[c-'a'] == null) return false;
        if(idx == prefix.length()-1) return true;
        return startsWith(prefix, node.children[c-'a'], idx+1);
    }
    
    private class TrieNode{
        boolean isEnd;
        TrieNode[] children;
        public TrieNode(){
            isEnd = false;
            children = new TrieNode[26];
        }
    }
}
```

## 1373.寻找键值和最大的BST

* postorder，判断root的两子树是否为BST，如果是，可以判断root是否为BST，以及他的最大值/最小值/键值和
* O(n)/O(h)

```java
class Solution {
    int ans;
    public int maxSumBST(TreeNode root) {
        ans = 0;
        postorder(root);
        return ans;
    }
    // res[]{isBST, max, min, sum}
    private int[] postorder(TreeNode root){
        if(root == null) return new int[]{1, Integer.MIN_VALUE, Integer.MAX_VALUE, 0};
        int[] res1 = postorder(root.left);
        int[] res2 = postorder(root.right);
        if(res1[0] == 0 || res2[0] == 0 || res1[1] > root.val || res2[2] < root.val) 
            return new int[]{0, 0, 0, 0};
        int sum = root.val + res1[3] + res2[3];
        int max = root.right == null ? root.val : res2[1];
        int min = root.left  == null ? root.val : res1[2];
        ans = Math.max(ans, sum);
        return new int[]{1,max, min, sum};
    }
}
```

## 剑指04.二维数据查找

* 右上角开始查找，每次排除一行/一列
* O(m+n)/O(1

```java
class Solution {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int n = matrix.length;
        if(n == 0) return false;
        int m = matrix[0].length;
        if(m == 0) return false;
        int i = 0, j = m-1;
        while(i < n && j >= 0){
            if(matrix[i][j] == target)
                return true;
            else if(matrix[i][j] > target)
                j--;
            else
                i++;
        }
        return false;
    }
}
```

## 剑指07.重构二叉树

* 递归，其中l，r表示当前二叉树的前序遍历结果，visited表示当前中序遍历数组已经有多少个节点被访问，利用visited和根节点在Inorder[]中的下标，可以得到左右子树的长度，从而得到下一递归的参数
* O(n)/O(n)

```java
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < inorder.length; i++)
            map.put(inorder[i], i);
        return buildTree(preorder, 0, preorder.length-1, 0, map);
    }
    
    private TreeNode buildTree(int[] preorder, int l, int r, int visited, HashMap<Integer, Integer> map){
        if(l > r) return null;
        TreeNode root = new TreeNode(preorder[l]);
        int idx = map.get(preorder[l]);
        root.left  = buildTree(preorder, l+1, l+idx-visited, visited, map);
        root.right = buildTree(preorder, l+idx-visited+1, r, 1+idx, map);
        return root;
    }
}
```

## 76.最小覆盖子串

### Solution 1

* hashmap + 滑动窗口
  * 滑动窗口：[l,r)，l,r从空子串开始，r向右移动，知道找到满足条件的子串，接着l向右移动，得到满足条件的最小子串。
  * check()函数，用于判断当前子串是否满足条件，tFreq,sFreq保存字符出现次数。
* O(128 * n)/O(1)

```java
class Solution {
    public String minWindow(String s, String t) {
        int[] sFreq = new int[128];
        int[] tFreq = new int[128];
        int l = 0, r = 0, ansl = -1, ansr = -1, sLen = s.length();
        int minLen = sLen + 1;
        for(int i = 0; i < t.length(); i++)
            tFreq[t.charAt(i)]++;
        while(r < sLen){
            char c = s.charAt(r);
            sFreq[c]++;
            r++;
            while(check(sFreq, tFreq)){
                if(r - l < minLen){
                    ansl = l;
                    ansr = r;
                    minLen = r-l;
                }
                char leftChar = s.charAt(l);
                sFreq[leftChar]--;
                l++;
            }
        }
        if(minLen == sLen+1) return "";
        return s.substring(ansl, ansr);
    }
    
    private boolean check(int[] sFreq, int[] tFreq){
        for(int i = 0; i < 128; i++)
            if(sFreq[i] < tFreq[i])
                return false;
        return true;
    }
}
```

### Solution 2

* hashmap + 滑动窗口
* distance：表示当前子串还差几个目标字符，才能达到要求，tFreq[c]表示当前子串还差多少个目标字符，显然，当r出现了目标字符，且该目标字符还差时，就可以减小distance，当刚好不差目标字符，但是l右移排除了一个目标字符，distance++。
* 非目标字符不会对distance产生影响，因为非目标字符的tFreq永远不会大于0。
* O(n)/O(1)

```java
class Solution {
    public String minWindow(String s, String t) {
        int[] tFreq = new int[128];
        int l = 0, r = 0, ansl = -1, ansr = -1, sLen = s.length();
        int minLen = sLen + 1;
        int distance = t.length();
        for(int i = 0; i < t.length(); i++)
            tFreq[t.charAt(i)]++;
        while(r < sLen){
            char c = s.charAt(r);
            if(tFreq[c] > 0)
                distance--;
            tFreq[c]--;
            r++;
            while(distance == 0){
                if(r-l < minLen){
                    ansl = l;
                    ansr = r;
                    minLen = r-l;
                }
                char leftChar = s.charAt(l);
                if(tFreq[leftChar] == 0)
                    distance++;
                tFreq[leftChar]++;
                l++;
            }
        }
        if(minLen == sLen+1) return "";
        return s.substring(ansl, ansr);
    }
}
```

## 85.最大矩形

### Solution 1

* 单调栈
* O(mn)/o(n)

```java
class Solution {
    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        if(m == 0) return 0;
        int n = matrix[0].length;
        if(n == 0) return 0;
        int ans = 0;
        int[] heights = new int[n];
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(matrix[i][j] == '1')
                    heights[j]++;
                else
                    heights[j] = 0;
            }
            ans = Math.max(ans, maxArea(heights));
        }
        return ans;
    }
    
    private int maxArea(int[] heights){
        int n = heights.length;
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for(int i = 0; i < n; i++){
            while(stack.peek() != -1 && heights[stack.peek()] > heights[i]){
                maxArea = Math.max(maxArea, heights[stack.pop()] * (i - stack.peek() -1));
            }
            stack.push(i);
        }
        while(stack.peek() != -1)
            maxArea = Math.max(maxArea, heights[stack.pop()] * (n - stack.peek() -1));
        return maxArea;
    }
}
```

### Solution 2

* DP，求每一个高度i的左边第一个小于它的元素位置，以及右边第一个小于它元素的位置。
* O(mn)/o(n)

```java
class Solution {
    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        if(m == 0) return 0;
        int n = matrix[0].length;
        if(n == 0) return 0;
        int ans = 0;
        int[] heights = new int[n];
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(matrix[i][j] == '1')
                    heights[j]++;
                else
                    heights[j] = 0;
            }
            ans = Math.max(ans, maxArea(heights));
        }
        return ans;
    }
    
    private int maxArea(int[] heights){
        int n = heights.length;
        int[] leftMin  = new int[n];
        int[] rightMin = new int[n];
        int maxArea = 0;
        for(int i = 0; i < n; i++){
            int j = i-1;
            while(j != -1 && heights[j] >= heights[i])
                j = leftMin[j];
            leftMin[i] = j;
        }
        for(int i = n-1; i >= 0; i--){
            int j = i+1;
            while(j != n && heights[j] >= heights[i])
                j = rightMin[j];
            rightMin[i] = j;
        }
        for(int i = 0; i < n; i++)
            maxArea = Math.max(maxArea, heights[i] * (rightMin[i] - leftMin[i] - 1));
        return maxArea;
    }
}
```

## 974.和可被K整除的子数组

* 前缀和，同余定理
  * 已知前缀和prefix[i]，和prefix[j]，则区间和[j, i+1]可以表示为prefix[j] - prefix[i]
  * 如果两个数x, y mod z的余数相等，则 (x - y) mod z == 0，即(x - y)可以被z整除
  * 特殊情况，余数可能为负数，如 12 % 5 = 2, -3 % 5 = -3, 需要将负数转换为正数
  * 保留余数可以用数组(能用数组就不要用Map)
  * 如果prefix[i]能够被K整除，则应该也算入次数，此时需初始化cnt[0] = 1。
* O(n)/O(k)

```java
class Solution {
    public int subarraysDivByK(int[] A, int K) {
        int[] cnt = new int[K];
        int sum = 0;
        int ans = 0;
        cnt[0] = 1;
        for(int i = 0; i < A.length; i++){
            sum += A[i];
            int module = (sum % K + K) % K;
            cnt[module]++;
        }
        for(int i = 0; i < K; i++)
            ans += cnt[i] * (cnt[i] -1) / 2;
        return ans;
    }
}
```

## 146.LCU缓存机制

* 双向链表 + hashmap
  * 双向链表：其中每个节点可以访问前一节点，后一节点，还保存着(key, value)。并提供删除节点，头部添加，尾部删除操作。
  * hashmap，其中key为key,而value为代表这个（key,value)的node
* O(1)/O(capacity)

```java
class LRUCache {
    HashMap<Integer, Node> map;
    DoubleList list;
    int capacity;

    public LRUCache(int capacity) {
        map = new HashMap<>();
        list = new DoubleList();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        Node node = map.get(key);
        if(node == null) return -1;
        list.remove(node);
        list.addFirst(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        Node node = new Node(key, value);
        if(map.get(key) != null){
            Node preNode = map.get(key);
            list.remove(preNode);
            list.addFirst(node);
            map.put(key, node);
            return;
        }
        if(list.size == capacity)
            map.remove(list.removeLast().key);
        map.put(key, node);
        list.addFirst(node);
    }
}

class Node {
    int key;
    int val;
    Node pre;
    Node next;
    public Node(int key, int val){
        this.key = key;
        this.val = val;
    }
}

class DoubleList {
    Node head;
    Node tail;
    int size;
    
    public DoubleList() {
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.pre  = head;
        size = 0;
    }
    
    public void addFirst(Node node){
        Node temp = head.next;
        head.next = node;
        node.pre  = head;
        node.next = temp;
        temp.pre  = node;
        size++;
    }
    
    public void remove(Node node){
        node.pre.next = node.next;
        node.next.pre = node.pre;
        size--;
    }
    
    public Node removeLast(){
        Node node = tail.pre;
        this.remove(node);
        return node;
    }
}
```

## 394.字符串解码

* stack\<String\>，需要将正数K转换为字符串，需要将char类型转换为字符串，需要使用StringBuilder的insert方法在头部插入。
* O(n)/O(n)

```java
class Solution {
    public String decodeString(String s) {
        Stack<String> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for(int i = 0; i < chars.length; i++){
            if(chars[i] >= '0' && chars[i] <= '9'){
                StringBuilder sb = new StringBuilder();
                while(chars[i] >= '0' && chars[i] <= '9'){
                    sb.append(chars[i]);
                    i++;
                }
                stack.push(sb.toString());
            }
            if(chars[i] != ']')
                stack.push(String.valueOf(chars[i]));
            else{
                StringBuilder sb = new StringBuilder();
                while(!stack.peek().equals("["))
                    sb.insert(0, stack.pop());
                stack.pop();
                int count = Integer.parseInt(stack.pop());
                String temp = sb.toString();
                for(int j = 0; j < count-1; j++)
                    sb.append(temp);
                stack.push(sb.toString());
            }
        }
        StringBuilder ans = new StringBuilder();
        while(!stack.isEmpty())
            ans.insert(0, stack.pop());
        return ans.toString();
    }
}
```

## 剑指37.序列化二叉树

* BFS，序列化时，需要将空节点入队，反序列化时，需要按序列化一样使用queue
* O(n)/O(n)

```java
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if(root == null) return "";
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            if(node != null){
                sb.append(node.val + ",");
                queue.offer(node.left);
                queue.offer(node.right);
            }else{
                sb.append("null,");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data.equals("")) return null;
        String[] vals = data.split(",");
        TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int idx = 1;
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            if(!vals[idx].equals("null")){
                node.left = new TreeNode(Integer.parseInt(vals[idx]));
                queue.offer(node.left);
            }
            idx++;
            if(!vals[idx].equals("null")){
                node.right = new TreeNode(Integer.parseInt(vals[idx]));
                queue.offer(node.right);
            }
            idx++;
        }
        return root;
    }
}
```

## 707.设计链表

* 双向链表，size保存链表长度
* 可以根据插入/删除的位置index与head/tail的距离，选择从头遍历或者从尾遍历
* get/insert/delete:O(n)，addAtHead/addAtTail:O(1)

```java
class MyLinkedList {
    int size;
    Node head;
    Node tail;

    /** Initialize your data structure here. */
    public MyLinkedList() {
        this.size = 0;
        this.head = new Node(0);
        this.tail = new Node(0);
        head.next = tail;
        tail.pre  = head;
    }
    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if(index < 0 || index >= size) return -1;
        Node node = this.find(index);
        return node.val;
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        Node node = new Node(val);
        Node nNode = head.next;
        head.next = node;
        node.pre  = head;
        node.next = nNode;
        nNode.pre  = node;
        size++;
    }
    
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        Node node = new Node(val);
        Node pNode = tail.pre;
        pNode.next = node;
        node.pre = pNode;
        node.next = tail;
        tail.pre = node;
        size++;
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if(index < 0) {
            this.addAtHead(val);
            return;
        }
        if(index == size) {
            this.addAtTail(val);
            return;
        }
        if(index < size){
            Node node = this.find(index);
            Node nNode = new Node(val);
            Node pNode = node.pre;
            pNode.next = nNode;
            nNode.pre = pNode;
            nNode.next = node;
            node.pre = nNode;
            size++;
        }
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if(index >= 0 && index < size){
            Node node = this.find(index);
            node.pre.next = node.next;
            node.next.pre = node.pre;
            size--;
        }
    }
    
    private Node find(int index){
        if(index+1 < size - index){
            Node node = head;
            int cnt = -1;
            while(cnt != index){
                node = node.next;
                cnt++;
            }
            return node;
        }
        Node node = tail;
        int cnt = size;
        while(cnt != index){
            node = node.pre;
            cnt--;
        }
        return node;
    }
}
//双向链表节点
class Node {
    int val;
    Node pre;
    Node next;
    
    public Node(int val) {
        this.val = val;
    }
}
```

## 1371.每个元音包含偶数次的最长字符串

* 前缀和+hashmap+状态压缩
  * state:表示从0~i中，每个元音的奇偶性
  * map:表示某一state第一次出现的位置
  * state == 0 第一次出现的位置为-1，所以map[0] = 0
  * 以i结尾的最长字符串为 i + 1 - map[state]
* O(n)/O(1)

```java
class Solution {
    public int findTheLongestSubstring(String s) {
        int state = 0, ans = 0;
        int[] map = new int[32];
        Arrays.fill(map, -1);
        map[0] = 0;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == 'a') {
                state ^= 1 << 0;
            }else if(c == 'e'){
                state ^= 1 << 1;
            }else if(c == 'i'){
                state ^= 1 << 2;
            }else if(c == 'o'){
                state ^= 1 << 3;
            }else if(c == 'u'){
                state ^= 1 << 4;
            }
            if(map[state] >= 0){
                ans = Math.max(ans, i - map[state] + 1);
            }else{
                map[state] = i+1;
            }
        }
        return ans;
    }
}
```

## 1011.在D天内送达包裹的能力

* 二分法：运送包裹的能力在max~sum之间，其中max为weights[i]的最大值，sum为weights[i]的和
* 计算days，需要~O(n)的时间，当days > D是，需要增大mid，当days <= D时，需要减小mid，并且保证mid取最小的符合条件的值
* O(nlog(sum-max))/O(1)

```java
class Solution {
    public int shipWithinDays(int[] weights, int D) {
        int max = 0, ans = 0;
        for(int weight : weights){
            max = Math.max(max, weight);
            ans += weight;
        }
        int l = max, r = ans;
        while(l < r){
            int mid = (l + r) / 2;
            int days = shipWithinWeight(weights, mid);
            if(days <= D)
                r = mid;
            else
                l = mid+1;
        }
        return l;
    }
    
    private int shipWithinWeight(int[] weights, int cur){
        int days = 0, curSum = 0;
        for(int weight : weights){
            curSum += weight;
            if(curSum > cur){
                curSum = weight;
                days++;
            }
        }
        if(curSum != 0) days++;
        return days;
    }
}
```

## 120.三角形最小路径和

* 自顶向下dp
* O(n^2)/O(1)

```java
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int height = triangle.size();
        if(height == 0) return 0;
        if(height == 1) return triangle.get(0).get(0);
        List<Integer> preLevel = triangle.get(0);
        for(int i = 1; i < height; i++){
            List<Integer> curLevel = triangle.get(i);
            curLevel.set(0, curLevel.get(0) + preLevel.get(0));
            curLevel.set(i, curLevel.get(i) + preLevel.get(i-1));
            for(int j = 1; j < i; j++){
                int preMin = Math.min(preLevel.get(j-1), preLevel.get(j));
                curLevel.set(j, curLevel.get(j) + preMin);
            }
            preLevel = curLevel;
        }
        int ans = Integer.MAX_VALUE;
        for(int i = 0; i < height; i++)
            ans = Math.min(ans, preLevel.get(i));
        return ans;
    }
}
```

* 自底向上
* O(n^2)/O(1)

```java
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int height = triangle.size();
        if(height == 0) return 0;
        for(int i = height -2; i >= 0; i--)
            for(int j = 0; j <= i; j++)
                triangle.get(i).set(j, triangle.get(i).get(j)
                                    + Math.min(triangle.get(i+1).get(j), triangle.get(i+1).get(j+1)));
        return triangle.get(0).get(0);
    }
}
```

## 837.新21点

* 问题描述，当手中点数>=K后，不在摸牌，求手中点数<=N的概率
* DP，自底向上
  * x：当前手中点数， dp[x]，手中点数为x时，获胜的概率， dp[0]，手中点数为0时获胜的概率
  * 初始条件：x >= K && x <= min(N, K-1+W)时，获胜的概率为1，即dp[x] = 1。
  * 状态转移方程：dp[x] = (dp[x+1] + dp[x+2] + ... + dp[x+W]) / W。与dp[x+1]的表达式相减，可以用O(1)的时间得到dp[x]，dp[x] = dp[x+1] + (dp[x+1] - dp[x+W+1]) / W。由于dp[x+W+1]不能超过 K+W-1，所以x必须 <= k-2，所以需要先求出dp[K-1].
  * 初始条件：dp[K-1] = (dp[K] + dp[K+1] + ... + dp[K+W-1]) / W，由于之前初始化条件，可以得到dp[K-1] = min(N-K+1, W) / W。
* O(K, W)/O(K+W)

```java
class Solution {
    public double new21Game(int N, int K, int W) {
        if(K == 0) return 1.0;
        double[] dp = new double[K+W];
        for(int i = K; i <= N && i <= K+W-1; i++)
            dp[i] = 1.0;
        dp[K-1] = 1.0 * (double)Math.min(N-K+1, W) / W;
        for(int i = K-2; i >= 0; i--)
            dp[i] = dp[i+1] + (dp[i+1] - dp[i+W+1]) / W;
        return dp[0];
    }
}
```

## 611.有效三角形的个数

* 固定最大边target，找有序数组两数和大于target的对数(双指针)
* O(n^2)/O(logn)

```java
class Solution {
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int ans = 0;
        for(int c = n-1; c >= 0; c--){
            int l = 0, r = c-1;
            while(l < r){
                if(nums[l] + nums[r] > nums[c]){
                    ans += r-l;
                    r--;
                }else{
                    l++;
                }
            }
        }
        return ans;
    }
}
```

## 174.地下城游戏

* dp\[i]\[j]表示骑士从点(i,j)出发到终点所需Hp值，状态转移方程：dp\[i][j] = max(1, min(dp\[i+1][j] , dp\[i][j+1]) - dungeon\[i][j])
* 初始条件：dp\[m][n-1] = dp\[m-1][n] = 1. 初始化dp全部为最大值，可以简化初始化条件。
* O(mn)/O(mn)

```java
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] dp = new int[m+1][n+1];
        
        for(int i = 0; i <= m; i++)
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        dp[m][n-1] = 1;
        dp[m-1][n] = 1;
        
        for(int i = m-1; i >= 0; i--)
            for(int j = n-1; j >= 0; j--)
                dp[i][j] = Math.max(1, Math.min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j]);
        return dp[0][0];
    }
}
```

## 147.链表插入排序

* dummy：哑节点，tail：已排好序链表的尾节点（把curNode插入到dummy ~ tail时，必须用到tail）, curNode：待插入节点，pre：插入位置（只有插入点在dummy ~ tail.pre时有效）
* O(n^2)/O(1)

```java
class Solution {
    public ListNode insertionSortList(ListNode head) {
        if(head == null) return null;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode curNode = head.next;
        ListNode tail = head;
        while(curNode != null){
            if(tail.val <= curNode.val){
                curNode = curNode.next;
                tail = tail.next;
            }else{
                ListNode pre = dummy;
                while(pre.next.val < curNode.val)
                    pre = pre.next;
                tail.next = curNode.next;
                curNode.next = pre.next;
                pre.next = curNode;
                curNode = tail.next;
            }
        }
        return dummy.next;
    }
}
```

## 126.单词接龙Ⅱ

* BFS/无向图/邻接表
* O(n^2m)/O(n^2)

```java
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> wordSet = new HashSet<>();
        for(String word : wordList)
            wordSet.add(word);
        wordSet.add(beginWord);
        if(!wordSet.contains(endWord)) return new ArrayList<>();
        
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        buildGraph(wordSet, map);
        
        List<List<String>> ans = new ArrayList<>();
        LinkedList<ArrayList<String>> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        boolean isFound = false;
        ArrayList<String> temp = new ArrayList<>();
        temp.add(beginWord);
        queue.offer(temp);
        while(!queue.isEmpty()){
            int size = queue.size();
            HashSet<String> subVisited = new HashSet<>();
            for(int i = 0; i < size; i++){
                ArrayList<String> curPath = queue.poll();
                String curNode = curPath.get(curPath.size() - 1);
                ArrayList<String> neighbors = map.get(curNode);
                for(String neighbor : neighbors) {
                    if(!visited.contains(neighbor)){
                        if(neighbor.equals(endWord)){
                            curPath.add(endWord);
                            ans.add(new ArrayList<String>(curPath));
                            curPath.remove(curPath.size() - 1);
                            isFound = true;
                        }else{
                            curPath.add(neighbor);
                            queue.offer(new ArrayList<>(curPath));
                            curPath.remove(curPath.size() - 1);
                        }
                        subVisited.add(neighbor);
                    }
                }
            }
            visited.addAll(subVisited);
            if(isFound) break;
        }
        return ans;
    }
    
    private void buildGraph(HashSet<String> wordSet, HashMap<String, ArrayList<String>> map){
        for(String word : wordSet){
            char[] chs = word.toCharArray();
            ArrayList<String> edges = new ArrayList<>();
            for(int i = 0; i < chs.length; i++)
                for(char j = 'a'; j <= 'z'; j++){
                    char c = chs[i];
                    chs[i] = j;
                    String temp = String.valueOf(chs);
                    if(!temp.equals(word) && wordSet.contains(temp))
                        edges.add(temp);
                    chs[i] = c;
                }
            map.put(word, edges);
        }
    }
}
```

## 10046.把数组翻译成字符串

* 递归略
* DP：dp[i],表示前i位数组能够转换的字符串个数，显然dp[i] = dp[i-1] 或者 dp[i] = dp[i-1] + dp[i-2];
* O(n)/O(n)，空间可优化，略

```java
class Solution {
    public int translateNum(int num) {
        String s = String.valueOf(num);
        char[] digits = s.toCharArray();
        
        int[] dp = new int[digits.length + 1];
        dp[0] = 1;
        dp[1] = 1;
        
        for(int i = 2; i <= digits.length; i++){
            int temp = (digits[i-2] - '0') * 10 + digits[i-1] - '0';
            if(temp >= 10 && temp <= 25)
                dp[i] = dp[i-1] + dp[i-2];
            else
                dp[i] = dp[i-1];
        }
        return dp[digits.length];
    }
}
```

## 9.回文数

只需要反转一半的数字进行比较

1. 如果x的位数为偶数，则循环停止条件为a. x为回文数，x == y时退出 b. x不为回文数，则x < y退出，**且x == y/10永远不会成立**
2. 如果x的位数为奇数，则循环停止条件为x < y，且如果为回文，则x == y/10成立
3. 必须考虑x是否以0结尾，不然会出现“990”是回文串

```java
class Solution {
    public boolean isPalindrome(int x) {
        if(x < 0 || (x != 0 && x % 10 == 0)) return false;
        int y = 0;
        while(x > y){
            y = y * 10 + x % 10;
            x = x / 10;
        }
        return x == y || x == y/10;
    }
}
```

可以发现，进行半数的数字反转，需要判断较多的边界情况，直接整个数字反转，思路更简洁。



## 1210.穿过迷宫的最小次数

### Solution1

* BFS,定义Int[]{i, j, state}， i，j为蛇尾坐标，state表示水平或者垂直

* 每个节点（i,j,state)可能有三种路径（右移，下移，旋转）根据是否有障碍，入队。
* step表示BFS层数，当到达（n-1, n-2, 0)这个节点，找到最短路径
* visited统一访问过的节点，避免重复访问
* O(n^2)/O(n^2)

```java
class Solution {
    public int minimumMoves(int[][] grid) {
        int n = grid.length;
        int[] start = new int[]{0 ,0, 0};
        LinkedList<int[]> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        queue.offer(start);
        int step = 0;
        
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size > 0){
                int[] cur = queue.poll();
                int i = cur[0], j = cur[1], state = cur[2];
                if(i == n-1 && j == n-2 && state == 0) 
                    return step;
                String s = i + "" + j + "" + state;
                if(!visited.contains(s)){
                    visited.add(s);
                    if(state == 0){
                        if(i < n-1 && grid[i+1][j] == 0 && grid[i+1][j+1] == 0){
                            queue.offer(new int[]{i+1, j, 0});
                            queue.offer(new int[]{i, j, 1});
                        }
                        if(j < n-2 && grid[i][j+2] == 0)
                            queue.offer(new int[]{i, j+1, 0});
                    }else {
                        if(j < n-1 && grid[i][j+1] == 0 && grid[i+1][j+1] == 0){
                            queue.offer(new int[]{i, j+1, 1});
                            queue.offer(new int[]{i, j, 0});
                        }
                        if(i < n-2 && grid[i+2][j] == 0)
                            queue.offer(new int[]{i+1, j, 1});
                    }
                }
                size--;
            }
            step++;
        }
        return -1;
    }
}
```

### Solution2

* DP
  * 状态转移方程：dp\[i]\[j]\[0] = min(dp\[i-1]\[j]\[0], dp\[i]\[j-1]\[0], dp\[i]\[j]\[1]),  dp\[i]\[j]\[1] = min(dp\[i-1]\[j]\[1], dp\[i]\[j-1]\[1], dp\[i]\[j]\[0])
  * 初始化条件：全部初始化为M = Integer.MAX_VALUE - 100000， 大于M的位置表示不可到达，显示dp\[0]\[1]\[0] = dp\[1]\[0]\[0] = -1。
  * 状态转移时，必须判断dp\[i]\[j]\[0]是否可能存在。
* O(n^2)/O(n^2)

```java
class Solution {
    public int minimumMoves(int[][] grid) {
        int n = grid.length;
        int M = Integer.MAX_VALUE - 100000;
        int[][][] dp = new int[n+1][n+1][2];
        
        for(int i = 0; i <= n; i++)
            for(int j = 0; j <= n; j++){
                dp[i][j][0] = M;
                dp[i][j][1] = M;
            }
        dp[0][1][0] = -1;
        dp[1][0][0] = -1;
        
        for(int i = 1; i <= n; i++)
            for(int j = 1; j <= n; j++){
                boolean v = false;
                boolean h = false;
                if(j < n && grid[i-1][j-1] == 0 && grid[i-1][j] == 0){
                    dp[i][j][0] = Math.min(dp[i-1][j][0], dp[i][j-1][0]) + 1;
                    h = true;
                }
                if(i < n && grid[i-1][j-1] == 0 && grid[i][j-1] == 0){
                    dp[i][j][1] = Math.min(dp[i-1][j][1], dp[i][j-1][1]) + 1;
                    v = true;
                }
                if(h && i < n && grid[i][j] == 0)
                    dp[i][j][0] = Math.min(dp[i][j][1]+1, dp[i][j][0]);
                if(v && j < n && grid[i][j] == 0)
                    dp[i][j][1] = Math.min(dp[i][j][0]+1, dp[i][j][1]);
            }
        return dp[n][n-1][0] >= M ? -1 : dp[n][n-1][0];
    }
}
```

## 221.最大正方形

* dp\[i]\[j]表示以i,j为右下角顶点的最大正方形
* 状态转移方程：dp\[i]\[j] = Math.min(dp\[i-1]\[j], dp\[i]\[j-1], dp\[i-1]\[j-1]) + 1
* O(mn)/O(mn)

```java
class Solution {
    public int maximalSquare(char[][] matrix) {
        int ans = 0;
        int m = matrix.length;
        if(m == 0) return 0;
        int n = matrix[0].length;
        if(n == 0) return 0;
        int[][] dp = new int[m+1][n+1];
        
        for(int i = 1; i <= m; i++)
            for(int j = 1; j <= n; j++){
                if(matrix[i-1][j-1] == '0'){
                    dp[i][j] = 0;
                } else{
                    dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])) + 1;
                    ans = Math.max(ans, dp[i][j]);
                }
            }
        return ans * ans;
    }
}
```

## 43.字符串乘法

* 竖式乘法：abcd * efg = d * g * 10^0 + d * f * 10^1 + ... + d * e * 10^5
  * 构造数组int[] digits，保存乘法过程中的每一位结果，num1的第i位和num2的第j位相乘，就是num1[i] * num2[j]的结果，与digits[i+j] digits[i+j+1]相加，相加过程中，**保证digits[i+j+1] < 10，也就是低位<10**
  * 最终只有最高位可能>=10，且只有最高位可能为0，需要去掉。
* O(nm)/O(m+n)

```java
class Solution {
    public String multiply(String num1, String num2) {
        if(num1.equals("0") || num2.equals("0")) return "0";
        int len1 = num1.length(), len2 = num2.length();
        int[] digits = new int[len1 + len2];
        for(int i = len1-1; i >= 0; i--)
            for(int j = len2-1; j >= 0; j--){
                char c1 = num1.charAt(i), c2 = num2.charAt(j);
                int temp = (c1 - '0') * (c2 - '0');
                int p1 = i+j, p2 = i+j+1;
                temp += digits[p2];
                digits[p2] = temp % 10;
                digits[p1] += temp / 10;
            }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if(digits[0] == 0) i++;
        while(i < len1+len2){
            sb.append(digits[i]);
            i++;
        }
        return sb.toString();
    }
}
```

## 790.[多米诺和托米诺平铺](https://leetcode-cn.com/problems/domino-and-tromino-tiling/)

* dp：状态转移方程如下

![image-20200616142044643](README.assets/image-20200616142044643.png)

* 初始状态为(0),每循环一次，会铺满一列，循环N次后，最终会把前N个格子铺满，而此时答案为dp[0]
* O(n)/O(1)

```java
class Solution {
    public int numTilings(int N) {
        int MOD = 1000000007;
        long[] dp = new long[]{1, 0, 0, 0};
        for(int i = 0; i < N; i++){
            long[] ndp = new long[4];
            ndp[0] = (dp[0] + dp[3]) % MOD;
            ndp[1] = (dp[0] + dp[2]) % MOD;
            ndp[2] = (dp[0] + dp[1]) % MOD;
            ndp[3] = (dp[0] + dp[1] + dp[2]) % MOD;
            dp = ndp;
        }
        return (int)dp[0];
    }
}
```

## 1014. 最佳观光组合

* A[j] - j + A[i] + [i] 其中 i < j，对于给定的j，求A[i] + i的最大值
* O(n)/O(1)

```java
class Solution {
    public int maxScoreSightseeingPair(int[] A) {
        int n = A.length;
        int max = A[0];
        int ans = 0;
        for(int j = 1; j < n; j++) {
            max = Math.max(max, A[j-1] + j - 1);
            ans = Math.max(ans, max + A[j] - j);
        }
        return ans;
    }
}
```

## 962.最大宽度坡

### Solution1

* 单调栈，已知i的情况下，求最远的j
  * 构建单调递减栈时，可以保证栈内所有元素为左边界候选
  * 贪心思想：从右到左，能找到栈内元素i的最远j
* O(n)/O(n)

```java
class Solution {
    public int maxWidthRamp(int[] A) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        int n = A.length;
        int ans = 0;
        for(int i = 1; i < n; i++)
            if(A[i] < A[stack.peek()])
                stack.push(i);
        for(int i = n-1; i >= 0; i--)
            while(!stack.isEmpty() && A[i] >= A[stack.peek()]){
                ans = Math.max(ans, i - stack.pop());
            }
        return ans;
    }
}
```

### Solution 2

* 排序，将index数组按照对用的A[i]值排序
  * 注意自定义比较器时，数组必须是Integer，因为自定义比较器必须是泛型
* O(nlogn)/O(n)

```java
class Solution {
    public int maxWidthRamp(int[] A) {
        int n = A.length;
        Integer[] B = new Integer[n];
        for(int i = 0; i < n; i++)
            B[i] = i;
        Arrays.sort(B, (o1, o2) -> (Integer)A[o1] - (Integer)A[o2]);
        int ans = 0, min = B[0];
        for(int j = 1; j < n; j++){
            min = Math.min(min, B[j-1]);
            ans = Math.max(ans, B[j] - min);
        }
        return ans;
    }
}
```

## [1028. 从先序遍历还原二叉树](https://leetcode-cn.com/problems/recover-a-tree-from-preorder-traversal/)

### Solution1

* DFS，其中cur表示当前遍历到S的位置，cur始终是指向curD深度的节点值的第一个字符，curD表示当前cur指向节点的深度，depth表示当前递归节点的深度，显然，当curD <= depth，不能继续向下递归，而需要找到curD的父节点（第一个，因为是dfs）
* O(n)/O(n)

```java
class Solution {
    int cur=0,curD=0;
    public TreeNode recoverFromPreorder(String S) {
        char[] nodes = S.toCharArray();
        return dfs(0,nodes);
    }
    public TreeNode dfs(int depth, char[] nodes){
        int val = 0;
        for(;cur<nodes.length&&nodes[cur]!='-';cur++)
            val=val*10+nodes[cur]-'0';
        curD = 0;
        for(;cur<nodes.length&&nodes[cur]=='-';cur++,curD++);
        TreeNode r = new TreeNode(val);
        if(curD>depth)r.left = dfs(curD,nodes);
        if(curD>depth)r.right = dfs(curD,nodes);
        return r;
    }
}
```

### Solution 2

* 递归，使用stack改写方法1
* O(n)/O(n)

```java
class Solution {
    public TreeNode recoverFromPreorder(String S) {
        Stack<TreeNode> path = new Stack<TreeNode>();
        int pointer = 0;
        while(pointer < S.length()){
            int curDepth = 0;
            while(pointer < S.length() && S.charAt(pointer) == '-'){
                curDepth++;
                pointer++;
            }
            int val = 0;
            while(pointer < S.length() && S.charAt(pointer) != '-'){
                val = val * 10 + (S.charAt(pointer) - '0');
                pointer++;
            }
            TreeNode curNode = new TreeNode(val);
            if(curDepth == path.size() && !path.isEmpty())
                path.peek().left = curNode;
            else if(path.size() > curDepth){
                while(path.size() > curDepth)
                    path.pop();
                path.peek().right = curNode;
            }
            path.push(curNode);
        }
        while(path.size() > 1)
            path.pop();
        return path.peek();
    }
}
```

## [42. 接雨水](https://leetcode-cn.com/problems/trapping-rain-water/)

* 单调栈（单调递减），能够快速找到栈顶元素的右边第一个大于它的值和左边第一个大于它的值
* 相当于按行统计，
* ![water.gif](README.assets/7d5ff9af88634d417d7925e8987b7db92d3a26766bd9078215ab63df424fa745-water.gif)
* O(n)/O(n)

```java
class Solution {
    public int trap(int[] height) {
        int n = height.length, cur = 0, ans = 0;
        Stack<Integer> stack = new Stack<>();
        while(cur < n) {
            while(!stack.isEmpty() && height[stack.peek()] < height[cur]) {
                int temp = stack.pop();
                if(stack.isEmpty()) continue;
                int min = Math.min(height[stack.peek()], height[cur]);
                int distance = cur - stack.peek() - 1;
                ans += (min - height[temp]) * distance;
            }
            stack.push(cur);
            cur++;
        }
        return ans;
    }
}
```

1. 按列统计，每一列的水，由左侧最大值，右侧最大值两者的较小值，与当前列高的差值决定

```java
class Solution {
    public int trap(int[] height) {
        int n = height.length;
        if(n <= 2) return 0;

        int[] lMax = new int[n];
        int[] rMax = new int[n];
        lMax[0] = height[0];
        rMax[n-1] = height[n-1];
        for(int i = 1; i < n; i++) lMax[i] = Math.max(height[i], lMax[i-1]);
        for(int i = n-2; i >= 0; i--) rMax[i] = Math.max(height[i], rMax[i+1]);

        int ans = 0;
        for(int i = 0; i < n; i++) ans += Math.min(lMax[i], rMax[i]) - height[i];
        return ans;
    }
}
```

## [ 16.18. 模式匹配](https://leetcode-cn.com/problems/pattern-matching-lcci/)

* 枚举a和b的长度，其中a，b的长度满足二元一次方程组：c_a * l_a + c_b * l_b = l_v，其中c_a, c_b, l_v都是已知量，l_a的取值范围为[0, l_v/c_a]，l_a和l_b均为整数
* 特殊情况：
  * 当c_a为0，则l_a可以取任意值，解决办法：令c_a始终为出现较多的那个值，从而保证在非空pattern时，c_a不可能为0
  * pattern为空，value为空的情况单独考虑
  * 当c_b为0，则令l_b为0即可。
  * pattern和value一一对应，还得判断a和b的字符串是否一样
* O(l_v / c_a * (l_v + lp)) = O(l_v / l_p * (l_v + l_p)) = O(l_v * l_v) / O(l_v)

```java
class Solution {
    public boolean patternMatching(String pattern, String value) {
        int count_a = 0, count_b = 0;
        for(int i = 0; i < pattern.length(); i++) {
            if(pattern.charAt(i) == 'a')
                count_a++;
            else
                count_b++;
        }
        
        if(count_a < count_b) {
            int temp = count_a;
            count_a = count_b;
            count_b = temp;
            char[] ca = pattern.toCharArray();
            for(int i = 0; i < ca.length; i++) 
                ca[i] = ca[i] == 'a' ? 'b' : 'a';
            pattern = new String(ca);
        }

        if(pattern.equals(""))
            return value.equals("");
        if(value.equals(""))
            return count_b == 0;
        
        int maxLen = value.length() / count_a;
        for(int len_a = 0; len_a <= maxLen; len_a++) {
            int len_b = 0;
            if(count_b != 0) {
                int resident = (value.length() - count_a * len_a) % count_b;
                if(resident != 0) continue;
                len_b = (value.length() - count_a * len_a) / count_b;
            }
            String a = "", b = "";
            int p1 = 0, p2 = 0;
            while(p1 < pattern.length()) {
                if(pattern.charAt(p1) == 'a') {
                    if(a.length() == 0) {
                        a = value.substring(p2, p2+len_a);
                    } else if (!a.equals(value.substring(p2, p2+len_a))){
                        break;
                    }
                    p1++;
                    p2 += len_a;
                } else if(pattern.charAt(p1) == 'b') {
                    if(b.length() == 0) {
                        b = value.substring(p2, p2+len_b);
                    } else if (!b.equals(value.substring(p2, p2+len_b))){
                        break;
                    }
                    p1++;
                    p2 += len_b;
                }
            }
            if(p1 == pattern.length() && p2 == value.length() && !a.equals(b))
                return true;
        }
        return false;
    }
}
```

## [140. 单词拆分 II](https://leetcode-cn.com/problems/word-break-ii/)

### Soluton 1

* DFS + memo
* O(n^3)/O(n^2)

```java
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>();
        for(String word : wordDict)
            set.add(word);
        HashMap<String, ArrayList<String>> memo = new HashMap<>();
        return dfs(s, set, memo);
    }
    
    private List<String> dfs(String s, HashSet<String> set, HashMap<String, ArrayList<String>> memo) {
        if(memo.get(s) != null) return memo.get(s);
        
        ArrayList<String> ans = new ArrayList<>();
        if(s.length() == 0) return ans;
        
        for(int i = 0; i < s.length(); i++) {
            String word = s.substring(0, i+1);
            if(set.contains(word)) {
                if(i == s.length()-1) ans.add(s);
                else {
                    List<String> list = dfs(s.substring(i+1, s.length()), set, memo);
                    for(String str : list)
                        ans.add(word + " " + str);
                }
            }
        }
        memo.put(s, ans);
        return ans;
    }
}
```

### Solution 2

* dp
  * dp[i]表示 [0,i-1]子串中的可能拆分
  * 状态转移方程 dp[i] = dp[j] + s.substring(j, i)，如果(j,i)在set中
* O(n^3)/O(n)
* 但是超时了。。Why

```java
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>();
        for(String word : wordDict)
            set.add(word);
        int n = s.length();
        ArrayList<String>[] dp = new ArrayList[n+1];
        ArrayList<String> initial = new ArrayList<>();
        initial.add("");
        dp[0] = initial;
        
        for(int i = 1; i <= n; i++) {
            ArrayList<String> list = new ArrayList<>();
            for(int j = 0; j < i; j++) {
                String word = s.substring(j, i);
                if(!set.contains(word)) continue;
                for(String str : dp[j])
                    list.add((str.equals("") ? "" : str + " ") + word);
            }
            dp[i] = list;
        }
        return dp[n];
    }
}
```

## 139 单词拆分

* dp,dp[i]表示从0~i-1的字符串能否被拆分
* O(n^2)/O(n)

```java
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>();
        for(String word : wordDict)
            set.add(word);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        
        for(int i = 1; i <= s.length(); i++) {
            for(int j = 0; j < i; j++) {
                if(dp[j] && set.contains(s.substring(j,i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
```

## [818. 赛车](https://leetcode-cn.com/problems/race-car/)

### Solution 1

* BFS
  * 节点：(position, speed)
  * 选择：加速/倒车
  * 剪枝：position >= 2 * target的节点放弃
  * visited不能存这么多节点，只存倒车的节点
  * 优化：原点处不能倒车（省了一半的时间）
* O(2^tar)/O(2^tar)

```java
class Solution {
    public int racecar(int target) {
        HashSet<String> visited = new HashSet<>();
        visited.add("0_1");
        visited.add("0_-1");
        LinkedList<Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(0, 1));
        int step = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                Pair<Integer, Integer> curNode = queue.poll();
                int nextPos = curNode.getKey() + curNode.getValue();
                int nextSpd = curNode.getValue() * 2;
                if(nextPos == target) return step+1;
                if(nextPos > 0 && nextPos < 2 * target) {
                    queue.offer(new Pair<>(nextPos, nextSpd));
                }
                int stopSpd = curNode.getValue() > 0 ? -1 : 1;
                String key = String.valueOf(curNode.getKey()) + "_" + String.valueOf(stopSpd);
                if(!visited.contains(key)) {
                    queue.offer(new Pair<>(curNode.getKey(), stopSpd));
                    visited.add(key);
                }
            }
            step++;
        }
        return -1;
    }
}
```

### Solution 2

* DP



## 224. 计算器

### Solution 1

* 反向遍历/栈
* ops保存着除了左括号以外的操作符，nums保存着数字
* 由于反转和栈，最终操作符的执行顺序是从左到右，从而避免了减少不满足结合律
* O(n)/O(n)

```java
class Solution {
    public int calculate(String s) {
        char[] chs = s.toCharArray();
        Stack<Character> ops = new Stack<>();
        Stack<Integer> nums = new Stack<>();
        
        for(int i = chs.length-1; i >= 0; i--) {
            if(chs[i] == ' ') {
                continue;
            } else if (chs[i] >= '0' && chs[i] <= '9') {
                StringBuilder sb = new StringBuilder();
                while(i >= 0 && chs[i] >= '0' && chs[i] <= '9'){
                    sb.insert(0, chs[i]);
                    i--;
                }  
                i++;
                nums.push(Integer.valueOf(sb.toString()));
            } else if (chs[i] != '(') {
                ops.push(chs[i]);
            } else {
                while(ops.peek() != ')') {
                    int arg1 = nums.pop();
                    int arg2 = nums.pop();
                    if(ops.pop() == '+')
                        nums.push(arg1 + arg2);
                    else
                        nums.push(arg1 - arg2);
                }
                ops.pop();
            }
        }
        
        int ans = nums.pop();
        while(!ops.isEmpty()) {
            if(ops.pop() == '+')
                ans += nums.pop();
            else
                ans -= nums.pop();
        }
        return ans;
    }
}
```

### Solution 2

* 栈，其中stack中栈顶两个数，保存着当前括号运算结束后，需要执行的操作。
* ans表示当前括号内，在当前位置的运算结果， sign表示符号
* O(n)/O(n)

```java
class Solution {
    public int calculate(String s) {
        char[] chs = s.toCharArray();
        Stack<Integer> stack = new Stack<>();
        int ans = 0, sign = 1;
        
        for(int i = 0; i < chs.length; i++) {
            if(chs[i] == ' ') {
                continue;
            } else if(chs[i] >= '0' && chs[i] <= '9') {
                int num = 0;
                while(i < chs.length && chs[i] >= '0' && chs[i] <= '9'){
                    num = num * 10 + chs[i] - '0';
                    i++;
                }
                i--;
                ans += num * sign;
            } else if(chs[i] == '+') {
                sign = 1;
            } else if(chs[i] == '-') {
                sign = -1;
            } else if(chs[i] == '(') {
                stack.push(ans);
                stack.push(sign);
                ans = 0;
                sign = 1;
            } else {
                ans = ans * stack.pop() + stack.pop();
            }
        }
        return ans;
    }
}
```

## [227. 基本计算器 II](https://leetcode-cn.com/problems/basic-calculator-ii/)

### Solution 1

* 反向遍历/双栈
* ops保存着优先级递增的操作符，nums存放着数字
* O(n)/O(n)

```java
class Solution {
    public int calculate(String s) {
        char[] chs = s.toCharArray();
        Stack<Character> ops = new Stack<>();
        Stack<Integer> nums  = new Stack<>();
        
        for(int i = chs.length-1; i >= 0; i--) {
            if(chs[i] == ' ') {
                continue;
            } else if(chs[i] >= '0' && chs[i] <= '9') {
                StringBuilder sb = new StringBuilder();
                while(i >= 0 && chs[i] >= '0' && chs[i] <= '9') {
                    sb.insert(0, chs[i]);
                    i--;
                }
                i++;
                nums.push(Integer.valueOf(sb.toString()));
            } else {
                while(!ops.isEmpty() && highPriority(ops.peek(), chs[i])) {
                    int arg1 = nums.pop();
                    int arg2 = nums.pop();
                    char op  = ops.pop();
                    nums.push(cal(op, arg1, arg2));
                }
                ops.push(chs[i]);
            }
        }
        
        while(!ops.isEmpty()) {
            int arg1 = nums.pop();
            int arg2 = nums.pop();
            char op  = ops.pop();
            nums.push(cal(op, arg1, arg2));
        }
        
        return nums.pop();
    }
    
    private int cal(char op, int arg1, int arg2) {
        if(op == '*') return arg1 * arg2;
        if(op == '/') return arg1 / arg2;
        if(op == '+') return arg1 + arg2;
        return arg1 - arg2;
    }
    
    private boolean highPriority(char op1, char op2) {
        if((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return true;
        return false;
    }
}
```

### Solution 2

* 正向遍历/单栈
* stack中存放有符号数，有符号数由加减法，以及**合并后的乘除法**组成
* 易错点：最后一个num入栈的情况没有考虑
* O(n)/O(n)

```java
class Solution {
    public int calculate(String s) {
        char[] chs = s.toCharArray();
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        char sign = '+';
        
        for(int i = 0; i < chs.length; i++) {
            if(chs[i] >= '0' && chs[i] <= '9')
                num = num * 10 + chs[i] - '0';
            if(chs[i] == '+' || chs[i] == '-' || chs[i] == '*' || chs[i] == '/' || i == chs.length-1) {
                if(sign == '+')
                    stack.push(num);
                else if(sign == '-')
                    stack.push(-num);
                else if(sign == '*')
                    stack.push(stack.pop() * num);
                else
                    stack.push(stack.pop() / num);
                num = 0;
                sign = chs[i];
            }  
        }
        
        int ans = 0;
        for(int i : stack)
            ans += i;
        return ans;
    }
}
```

## [209. 长度最小的子数组](https://leetcode-cn.com/problems/minimum-size-subarray-sum/)

### Solution 1

* 前缀和/二分查找
* 确定子数组的左节点后，使用二分法找到满足条件的最右节点（nums[r] - nums[l] = s）
* O(nlogn)/O(n)

```java
class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        if(nums.length == 0) return 0;
        int n = nums.length;
        int[] prefix = new int[n+1];
        for(int i = 1; i <= n; i++)
            prefix[i] = prefix[i-1] + nums[i-1];
        
        int ans = Integer.MAX_VALUE;
        for(int i = 0; i < n; i++) {
            int target = s + prefix[i];
            int bound  = Arrays.binarySearch(prefix, target);
            if(bound < 0)
                bound = -bound-1;
            if(bound <= n)
                ans = Math.min(ans, bound - i);
        }
        
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }
}
```

### Solution 2

* 滑动窗口（左闭右开）
* 相当于确定了右边界后，不断缩小左边界直到 < s，类似贪心的思想，右边界确定后，右边界左边的位置，都可以不用考虑
* O(n)/(1)

```java
class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        if(nums.length == 0) return 0;
        int l = 0, r = 0, n = nums.length, sum = 0;
        
        int ans = Integer.MAX_VALUE;
        while(r < n) {
            sum += nums[r];
            r++;
            while(sum >= s) {
                ans = Math.min(ans, r-l);
                sum -= nums[l];
                l++;
            }
        }
        
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }
}
```

## [132. 分割回文串 II](https://leetcode-cn.com/problems/palindrome-partitioning-ii/)

* DP，使用O(n^2)的时间，判断任意[i,j]子串是否为回文。另外dp[i]表示[0,i]子串的最少分割次数
* dp[i] = min(dp[i], dp[j] + 1)，需要保证[j, i]为回文
* O(n^2)/O(n^2)
* 两个dp可以合并，因为都是先确定了右边界，然后左边界往右移动

```java
class Solution {
    public int minCut(String s) {
        int n = s.length();
        if(n == 0) return 0;
        boolean[][] isPalindrome = new boolean[n][n];
        int[] dp = new int[n];
        
        for(int r = 0; r < n; r++)
            for(int l = 0; l <= r; l++)
                if(s.charAt(l) == s.charAt(r) && (r-l <= 2 || isPalindrome[l+1][r-1]))
                    isPalindrome[l][r] = true;
        
        for(int i = 0; i < n; i++) {
            if(isPalindrome[0][i]) dp[i] = 0;
            else {
                dp[i] = i+1;
                for(int j = 0; j < i; j++)
                    if(isPalindrome[j+1][i])
                        dp[i] = Math.min(dp[i], dp[j]+1);
            }
        }
        return dp[n-1];
    }
}
```

## [214. 最短回文串](https://leetcode-cn.com/problems/shortest-palindrome/)

### Solution 1

* 找到以字符串最左侧字符开始的最长回文子串
* 将原字符串反转，然后比较[0, n-i] 和反转字符串[i, n]是否相等，相等则说明子串[0, n-i]为回文
* O(n^2)/O(n)
```java
class Solution {
    public String shortestPalindrome(String s) {
        int n = s.length();
        String rev = new StringBuilder(s).reverse().toString();
        for(int i = 0; i < n; i++)
            if(s.substring(0, n-i).equals(rev.substring(i)))
                return rev.substring(0, i) + s;
        return "";
    }
}
```

### Solution 2

* KMP: NFA解法的KMP算法中，有一步需要构建**前缀**prefix**不包括本身**的**最长相同前后缀长度**，时间复杂度为O(M),具体解法如下
  * 首先令len = prefix[i-1]，如果此时M[len]的元素刚好等于M[i]，则说明[0.i]前缀的最长相同前后缀长度，为[0,i-1]前缀的长度加一
  * 如果不等，则[0,i]的最长相同前后缀长度，并不会超过[0, len-1]这个前缀的最长相同前后缀长度（可以证明，略）
    * 对上述过程递归更新len，直到len等于1，或者M[len]的元素刚好等于M[i]
* O(N)/O(N)

```java
class Solution {
    public String shortestPalindrome(String s) {
        int n = s.length();
        String rev = new StringBuilder(s).reverse().toString();
        String pattern = s + "#" + rev;
        int len = findLPS(pattern);
        return rev.substring(0, n-len) + s;
    }
    
    private int findLPS(String pattern) {
        int n = pattern.length();
        int[] f = new int[n];
        f[0] = 0;
        for(int i = 1; i < n; i++) {
            int len = f[i-1];
            while(len != 0 && pattern.charAt(len) != pattern.charAt(i))
                len = f[len-1];
            if(pattern.charAt(len) == pattern.charAt(i)) f[i] = len+1;
            else f[i] = len;
        }
        return f[n-1];
    }
}
```

## 378.有序矩阵中的第K小的元素

### Solution 1

* 维护一个长度为三的数组的小顶堆（其中存储着矩阵的值，及其坐标）
* 将每一行的第一个元素入堆，出队，找出最小元素，然后将其对应行的右边元素入队，出队，找出第二小元素，如此循环，直到找到第k小元素
* O(klogn)/O(n)

```java
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        //每一行的第一个数添加到PQ
        for(int i = 0; i < n; i++)
            pq.offer(new int[]{matrix[i][0], i, 0});
        //第i次出队，都能得到第i小的数,循环k-1次
        for(int i = 1; i < k; i++) {
            int[] temp = pq.poll();
            if(temp[2] != n-1)
                pq.offer(new int[]{matrix[temp[1]][temp[2]+1], temp[1], temp[2]+1});
        }
        return pq.poll()[0];
    }
}
```

### Solution 2

* 二分法，答案的取值范围在matrix\[0][0] ~ matrix\[n-1]\[n-1]之间，取中间值mid，可以确定小于等于mid的元素数量cnt，如果cnt >= k，则答案在l ~ mid之间（就算等于k，也会取满足条件的最小值，这样**这个值一定在矩阵中**）
* O(nlog(r-l))/O(1)

```java
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int l = matrix[0][0], r = matrix[n-1][n-1];
        while(l < r) {
            int mid = (l+r) / 2;
            int cnt = count(matrix, mid);
            if(cnt >= k) r = mid;
            else l = mid+1;
        }
        return l;
    }
    
    private int count(int[][] matrix, int mid) {
        int i = matrix.length-1, j = 0, cnt = 0;
        while(i >= 0 && j < matrix.length) {
            if(matrix[i][j] <= mid) {
                j++;
                cnt += i+1;
            }else {
                i--;
            }
        }
        return cnt;
    }
}
```

## 面试题17.13 恢复空格

### Solution 1

* dp
  * dp[i]：表示[0, i-1]字串未识别字符数量，显然，dp[i]最大值为dp[i-1] + 1即增加的第i个字符未被识别
  * 如果存在一字串[j, i-1]存在于字典，则dp[i] = dp[j]
* O(n* n)/ O(n)

```java
class Solution {
    public int respace(String[] dictionary, String sentence) {
        HashSet<String> dict = new HashSet<>(Arrays.asList(dictionary));
        int n = sentence.length();
        int[] dp = new int[n+1];
        
        for(int i = 1; i <= n; i++) {
            dp[i] = dp[i-1] + 1;
            for(int j = 0; j < i; j++)
                if(dict.contains(sentence.substring(j, i)))
                    dp[i] = Math.min(dp[i], dp[j]);
        }
        
        return dp[n];
    }
}
```

### Solution 2

* trie + dp
  * 如方法1中，需要判断[j, i-1]字串是否在字典中，而j从大到小变换，使用字典树，可以节省时间，因为如果某一子串[j, i-1]不存在于字典树时，j继续减小的字串都不会存在于字典。
* O(n*n)/O(m * 26) m小于字典中总字符数（有公共前缀）

```java
class Solution {
    public int respace(String[] dictionary, String sentence) {
        Trie trie = new Trie();
        for(String word : dictionary) trie.insert(word);
        int n = sentence.length();
        int[] dp = new int[n+1];
        
        for(int i = 1; i <= n; i++) {
            dp[i] = dp[i-1] + 1;
            for(int j : trie.search(sentence, i))
                dp[i] = Math.min(dp[i], dp[j]);
        }
        
        return dp[n];
    }
    
    private class Trie {
        private TrieNode root;
        
        public Trie() {
            root = new TrieNode();
        }
        
        private void insert(String word) {
            TrieNode cur = root;
            for(int i = word.length()-1; i >= 0; i--) {
                int c = word.charAt(i) - 'a';
                if(cur.children[c] == null) cur.children[c] = new TrieNode();
                cur = cur.children[c];
            }
            cur.isWord = true;
        }
        
        private ArrayList<Integer> search(String sentence, int i) {
            ArrayList<Integer> ans = new ArrayList<>();
            TrieNode cur = root;
            for(int j = i-1; j >= 0; j--) {
                int c = sentence.charAt(j) - 'a';
                if(cur.children[c] == null) break;
                cur = cur.children[c];
                if(cur.isWord) ans.add(j);
            }
            return ans;
        }
    }
    
    private class TrieNode {
        public boolean isWord;
        public TrieNode[] children;
        
        public TrieNode() {
            isWord = false;
            children = new TrieNode[26];
        }
    }
}
```

## [309. 最佳买卖股票时机含冷冻期](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)

* dp
  * dp[i]\[0]表示第i天手中没有股票时，能获得的最大利润
  * dp[i]\[1]表示第i天手中持有股票时，能获得的最大利润
  * dp[i]\[0] = max(dp[i-1]\[0], dp[i-1]\[0] + prices[i]) 今天没股票的两种情况：昨天没股票或者昨天有股票今天卖掉
  * dp[i]\[1] = max(dp[i-1]\[1], dp[i-2]\[1] - prices[i]) 今天有股票的两种情况：昨天有股票或者前天没有股票今天买股票（因为今天买股票的话，由于冷冻期，前天一定是没有股票的）
* O(n)/O(n)

```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if(n < 2) return 0;
        int[][] dp = new int[n+1][2];
        dp[0][0] = 0;
        dp[0][1] = Integer.MIN_VALUE;
        dp[1][0] = 0;
        dp[1][1] = -prices[0];
        
        for(int i = 2; i <= n; i++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i-1]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-2][0] - prices[i-1]);
        }
        
        return dp[n][0];
    }
}
```

## [315. 计算右侧小于当前元素的个数](https://leetcode-cn.com/problems/count-of-smaller-numbers-after-self/)

### Solution 1

* 从右到左插入排序，二分查找
* O(n*n)/O(n) 因为插入元素需要O(n)的时间复杂度

```java
class Solution {
    public List<Integer> countSmaller(int[] nums) {
        LinkedList<Integer> ans  = new LinkedList<>();
        List<Integer> list = new ArrayList<>();
        int n = nums.length;
        if(n == 0) return ans;
        
        for(int i = n-1; i >= 0; i--) {
            int idx = binarySearch(list, nums[i]);
            ans.offerFirst(idx);
            list.add(idx, nums[i]);
        }
        
        return ans;
    }
    
    private int binarySearch(List<Integer> list, int tar) {
        int l = 0, r = list.size() - 1;
        while(l < r) {
            int mid = (l + r) / 2;
            if(list.get(mid) < tar)
                l = mid + 1;
            else
                r = mid;
        }
        
        if(l == r && list.get(l) < tar) return l + 1;
        return l;
    }
}
```

### Solution 2

* 归并排序，因为在归并两排序子区间[l, mid] 和 [mid+1, r]过程中，能够确定右区间中，小于左区间元素nums[i]的元素个数，从而可以统计出每一个元素nums[i]， 右侧小于nums[i]的元素个数
* 也可以这样理解，归并过实际上会对每一个nums[i]的右侧小于nums[i]的元素左移，记录左移元素的个数即是答案
* O(nlogn)/O(n)

```java
class Solution {
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        int n = nums.length;
        if(n == 0) return ans;
        Pair<Integer, Integer>[] arr = new Pair[n];
        Pair<Integer, Integer>[] aux = new Pair[n];
        int[] counts = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = new Pair<>(i, nums[i]);
            aux[i] = new Pair<>(i, nums[i]);
        }
        mergeSort(arr, aux, 0, n-1, counts);
        for(int i = 0; i < n; i++)
            ans.add(counts[i]);
        return ans;
    }
    
    private void mergeSort(Pair<Integer, Integer>[] arr, Pair<Integer, Integer>[] aux, int l, int r, int[] counts) {
        if(l >= r) return;
        int mid = (l + r) / 2;
        mergeSort(arr, aux, l, mid, counts);
        mergeSort(arr, aux, mid+1, r, counts);
        if(arr[mid].getValue() > arr[mid+1].getValue())
            merge(arr, aux, l, mid, r, counts);
    }
    
    private void merge(Pair<Integer, Integer>[] arr, Pair<Integer, Integer>[] aux, int l, int mid, int r, int[] counts) {
        for(int i = l; i <= r; i++)
            aux[i] = arr[i];
        int i = l, j = mid + 1, k = l;
        while(i <= mid || j <= r) {
            if(j > r || (i <= mid && aux[i].getValue() <= aux[j].getValue())) {
                arr[k++] = aux[i];
                counts[aux[i].getKey()] += j - mid - 1;
                i++;
            } else {
                arr[k++] = aux[j++];
            }
        }
    }
}
```

### Solution 3

* Fenwick tree（树状数组），可以处理元素不断变化的数组的前缀和问题（更新元素logn, 求和logn）[参考视频](https://www.bilibili.com/video/BV1EW411d75F?from=search&seid=6516510607417910092)
* 问题转换为统计nums[i]右侧小于nums[i]的元素频率和，将数组**离散化**后，统计nums[i]-1的前缀和。
* O(nlogn)/O(s) s为独立元素个数

```java
class Solution {
    public List<Integer> countSmaller(int[] nums) {
        LinkedList<Integer> ans = new LinkedList<>();
        int n = nums.length;
        if(n == 0) return ans;
        discretize(nums);
        FenwickTree tree = new FenwickTree(n + 1);
        
        for(int i = n-1; i >= 0; i--) {
            tree.update(nums[i], 1);
            ans.addFirst(tree.query(nums[i] - 1));
        }
        
        return ans;
    }
    
    private void discretize(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for(int i = 0; i < nums.length; i++)
            set.add(nums[i]);
        Integer[] ranks = set.toArray(new Integer[0]);
        Arrays.sort(ranks);
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < ranks.length; i++)
            map.put(ranks[i], i + 1);
        for(int i = 0; i < nums.length; i++)
            nums[i] = map.get(nums[i]);
    }
    
    private class FenwickTree {
        int[] arr;
        
        public FenwickTree(int n) {
            arr = new int[n];
        }
        
        private int lowbit(int x) {return x & (-x);}
        
        private void update(int i, int delta) {
            while(i < arr.length) {
                arr[i] += delta;
                i += lowbit(i);
            }
        }
        
        private int query(int i) {
            int sum = 0;
            while(i > 0) {
                sum += arr[i];
                i -= lowbit(i);
            }
            return sum;
        }
    }
}
```

### Solution 4

* BST,构建BST的过程中，存储val重复出现次数，以及小于val的元素个数
* O(n*n)/O(n)

```java
class Solution {
    public List<Integer> countSmaller(int[] nums) {
        LinkedList<Integer> ans = new LinkedList<>();
        int n = nums.length;
        if(n == 0) return ans;
        BST tree = new BST(nums[n-1]);
        ans.addFirst(0);
        for(int i = n-2; i >= 0; i--) {
            ans.addFirst(tree.insert(nums[i]));
        }
        return ans;
    }
    
    private class BST {
        TreeNode root;
        
        public BST(int num) {
            root = new TreeNode(num);
        }
        
        private int insert(int num) {
            return insert(root, num);
        }
        
        private int insert(TreeNode root, int num) {
            if(root.val == num) {
                root.dup++;
                return root.leftCount;
            }
            
            if(root.val < num) {
                if(root.right == null) {
                    root.right = new TreeNode(num);
                    return root.dup + root.leftCount;
                } else {
                    return root.dup + root.leftCount + insert(root.right, num);
                }
            }
            
            root.leftCount++;
            if(root.left == null) {
                root.left = new TreeNode(num);
                return 0;
            }
            return insert(root.left, num);
        }
    }
    
    private class TreeNode {
        private int val;
        private int dup;
        private int leftCount;
        private TreeNode left;
        private TreeNode right;
        
        public TreeNode(int num) {
            this.val = num;
            this.dup = 1;
            this.leftCount = 0;
        }
    }
}
```

## [45. 跳跃游戏 II](https://leetcode-cn.com/problems/jump-game-ii/)

* 贪心：确定第step步能到的最远位置nextMax，当i == curMax，表示到达了第step步的最远位置，i + 1是需要加一步才能到达的
* curMax：当前step步能到达的最远位置，nextMax：step + 1步能到达的最远位置
* O(n)/O(1)

```java
class Solution {
    public int jump(int[] nums) {
        int curMax = 0, nextMax = 0, step = 0;
        for(int i = 0; i < nums.length - 1; i++) {
            nextMax = Math.max(nextMax, nums[i] + i);
            if(i == curMax) {
                step++;
                curMax = nextMax;
            }
        }
        return step;
    }
}
```

## [96. 不同的二叉搜索树](https://leetcode-cn.com/problems/unique-binary-search-trees/)

### Solution 1

* 记忆化递归
* O(n * n)/O(n * n)

```java
class Solution {
    public int numTrees(int n) {
        int[] memo = new int[n+1];
        return numTrees(0, n-1, memo);
    }
    
    private int numTrees(int l, int r, int[] memo) {
        if(l == r || l > r) return 1;
        if(memo[r-l+1] != 0) return memo[r-l+1];
        int ans = 0;
        for(int i = l; i <= r; i++) {
            ans += numTrees(l, i-1, memo) * numTrees(i+1, r, memo);
        }
        memo[r-l+1] = ans;
        return ans;
    }
}
```

### Solution 2

* dp  dp[i]：表示长度为i的递增数组所能组成的BST， dp[i] += dp[j] * dp[i-j] 
* O(n * n ) / O(n)

```java
class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        
        for(int i = 1; i <= n; i++) 
            for(int j = 0; j < i; j++)
                dp[i] += dp[j] * dp[i-1-j];
        
        return dp[n]; 
    }
}
```

## [1206. 设计跳表](https://leetcode-cn.com/problems/design-skiplist/)

* head：始终在左上角
* 多级链表，从上到小，链表逐渐密集，最底层存储着全部的有序数字
* 节点：包括右指针和下指针和值，其中右指针指向不小于它的数，下指针指向下一层链表相同的数。
* 如何增加层数：随机增加，如果inser过程中向上insert时，超过了最顶层，则会增加一层，并更新head
* 如何存储副本：随机向上存储，向上复制一个副本的概率为1/2
* 插入/搜索/删除的平均时间复杂度为O(logn)，空间复杂度O(n)

```java
class Skiplist {

    Node head;
    
    public Skiplist() {
        head = new Node(null, null, -1);
    }
    
    public boolean search(int target) {
        for(Node cur = head; cur != null; cur = cur.down){
            while(cur.right != null && cur.right.val < target) cur = cur.right;
            if(cur.right != null && cur.right.val == target) return true;
        }
        return false;
    }
    
    public void add(int num) {
        Stack<Node> stack = new Stack<>();
        for(Node cur = head; cur != null; cur = cur.down) {
            while(cur.right != null && cur.right.val < num) cur = cur.right;
            stack.push(cur);
        }
        Random rand = new Random();
        boolean randomInsert = true;
        Node downNode = null;
        while(randomInsert && !stack.isEmpty()) {
            Node insertNode = stack.pop();
            insertNode.right = new Node(insertNode.right, downNode, num);
            downNode = insertNode.right;
            randomInsert = (rand.nextInt() & 1) == 0;
        }
        if(randomInsert) head = new Node(new Node(null, downNode, num), head, -1);
    }
    
    public boolean erase(int num) {
        boolean erased = false;
        for(Node cur = head; cur != null; cur = cur.down) {
            while(cur.right != null && cur.right.val < num) cur = cur.right;
            if(cur.right != null && cur.right.val == num) {
                erased = true;
                cur.right = cur.right.right;
            }
        }
        return erased;
    }
    
    private class Node {
        Node right;
        Node down;
        int val;
        
        public Node(Node right, Node down, int val) {
            this.right = right;
            this.down = down;
            this.val = val;
        }
    }
}
```

## [785. 判断二分图](https://leetcode-cn.com/problems/is-graph-bipartite/)

* dfs：stack中存储着已经着色，但是还没有判断其冲突的节点（必须与邻接点颜色不同），color分为三种情况，未着色（0）/白（1）/黑（-1）
* 每一次着色，都把其放在stack
* O(n)/O(n)

```java
class Solution {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        
        for(int i = 0; i < n; i++) {
            if(color[i] == 0) {
                Stack<Integer> stack = new Stack<>();
                color[i] = 1;
                stack.push(i);

                while(!stack.isEmpty()) {
                    int node = stack.pop();
                    int[] neighbors = graph[node];
                    for(int neighbor : neighbors) {
                        if(color[neighbor] == color[node]) 
                            return false;
                        else if(color[neighbor] == 0) {
                            stack.push(neighbor);
                            color[neighbor] = -color[node];
                        }
                    }
                }
            }
        }
        
        return true;
    }
}
```

## [剑指 Offer 40. 最小的k个数](https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof/)

### Solution 1

* 大顶堆，维护最小的k个数（堆内元素为k+1时，poll最大的元素）
* O(nlogk)/O(k)

```java
class Solution {
    public int[] getLeastNumbers(int[] arr, int k) {
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for(int num : arr) {
            maxPQ.offer(num);
            if(maxPQ.size() > k)
                maxPQ.poll();
        }
        int[] ans = new int[k];
        for(int i = 0; i < k; i++)
            ans[i] = maxPQ.poll();
        return ans;
    }
}
```

### Solution 2

* 快排：每次partition能够找到最小的pivot +1 个数，如果 pivot + 1 == k则停止排序
* 时间复杂度期望为O(n)，最坏为O(n * n)证明略，递归深度O(logn)

```java
class Solution {
    public int[] getLeastNumbers(int[] arr, int k) {
        if(k == 0) return new int[]{};
        int l = 0, r = arr.length-1;
        while(true) {
            int pivot = partition(arr, l, r);
            if(pivot == k-1) break;
            else if(pivot > k-1) r = pivot - 1;
            else l = pivot + 1;
        }
        return Arrays.copyOfRange(arr, 0, k);
    }
    
    private int partition(int[] arr, int l, int r) {
        int tar = arr[l], i = l, j = r;
        while(i < j) {
            while(i < j && arr[j] >= tar) j--;
            arr[i] = arr[j];
            while(i < j && arr[i] <= tar) i++;
            arr[j] = arr[i];
        }
        arr[i] = tar;
        return i;
    }
}
```

## 312.戳气球

* 区间dp，其中dp\[i][j]表示开区间(i, j)的最大硬币数，需要在原数组上添加两个边界气球(值为1)且不能被戳破。
* 状态转移方程：dp\[i][j] = dp\[i][k] + dp\[k][j] + vals[i] * vals[k] * vals[j] k表示**最后一个**戳破的气球， 因为只有最后一个戳破，才能保证(i, k)的区间的边界是不变的。  k : [i+1, j-1]
* O(n^3)/O(n^2)

```java
class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] vals = new int[n+2];
        vals[0] = 1;
        vals[n+1] = 1;
        for(int i = 1; i <= n; i++)
            vals[i] = nums[i-1];
        int[][] dp = new int[n+2][n+2];
        
        for(int len = 3; len <= n+2; len++)
            for(int i = 0; i+len-1 <= n+1; i++) {
                int j = i + len - 1;
                for(int k = i+1; k <= j-1; k++) {
                    int coins = vals[k] * vals[i] * vals[j] + dp[i][k] + dp[k][j];
                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        
        return dp[0][n+1];
    }
}
```

## [60. 第k个排列](https://leetcode-cn.com/problems/permutation-sequence/)

### Solution 1

* 树的遍历，可以按照一定的顺序，将全排列看成如下树的节点，而且每一层节点表示的子树，其叶子节点数都能快速算出。
* level表示第几位，每一层节点能够确定一位。
* O(n^2)/O(n)

```java
class Solution {
    public String getPermutation(int n, int k) {
        int[] counts = new int[n+1];
        for(int i = 1; i <= n; i++)
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
}
```

### Solution 2

* solution1其实是一次确定了每一位的值，而每一位的值根据counts可以快速算出来
* O(n^2)/O(n)

```java
class Solution {
    public String getPermutation(int n, int k) {
        int[] counts = new int[n];
        counts[n-1] = 1;
        for(int i = n-2; i >= 0; i--)
            counts[i] = counts[i+1] * (n-i-1);
        ArrayList<Integer> unused = new ArrayList<>();
        for(int i = 1; i <= n; i++)
            unused.add(i);
        StringBuilder sb = new StringBuilder();
        k--;
        
        for(int i = 0; i < n; i++) {
            int idx = k / counts[i];
            k -= idx * counts[i];
            sb.append(unused.get(idx));
            unused.remove(idx);
        }
        
        return sb.toString();
    }
}
```

## [1520. 最多的不重叠子字符串](https://leetcode-cn.com/problems/maximum-number-of-non-overlapping-substrings/)

* 计算每个字符出现范围的最小子字符串  ---> 扩展区间，可以得到所有符合要求的子字符串集合 ---> 对该集合进行筛选，去掉包裹小区间的大区间（右边界排序）(因为此时两区间只有不相交和包裹两种情况)
* O(26n)/O(1)

```java
class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();
        //处理每个字符的区间
        int[][] intervals = new int[26][2];
        for(int i = 0; i < 26; i++) {
            intervals[i][0] = -1;
            intervals[i][1] = -1;
        }   
        for(int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            if(intervals[c][0] == -1)
                intervals[c][0] = i;
            intervals[c][1] = i;
        }
        
        //处理不合格区间
        List<int[]> looseIntervals = new ArrayList<>();
        for(int i = 0; i < 26; i++) {
            if(intervals[i][0] == -1) continue;
            int l = intervals[i][0], r = intervals[i][1];
            boolean keepLeft = true;
            for(int j = l; j <= r; j++) {
                int c = s.charAt(j) - 'a';
                if(intervals[c][0] < l) {
                    keepLeft = false;
                    break;
                }
                r = Math.max(r, intervals[c][1]);
            }
            if(keepLeft) looseIntervals.add(new int[]{l, r});
        }
        
        //处理宽松区间，按右区间排序，可以排除外部大区间
        List<String> ans = new ArrayList<>();
        Collections.sort(looseIntervals, (o1, o2) -> o1[1] - o2[1]);
        int curR = -1;
        for(int[] interval : looseIntervals) {
            if(interval[0] > curR)
                ans.add(s.substring(interval[0], interval[1] + 1));
            curR = interval[1];
        }
        return ans;
    }
}
```



## 340 最多含有K个不同字符的最长字串

### Solution1

* 滑动窗口，统计窗口内的不同字符个数，超过K个就左移左边界

```java
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        //统计字符最右出现的位置
        int[] map = new int[128];
        Arrays.fill(map, -1);
        int count = 0, l = 0, r = 0, ans = 0;
        while(r < s.length()) {
            char c = s.charAt(r);
            if(map[c] < l) count++;
            map[c] = r;
            if(count > k) {
                int minIdx = Integer.MAX_VALUE;
                for(int i = 0; i < 128; i++) {
                    if(map[i] >= l) minIdx = Math.min(minIdx, map[i] + 1);
                }
                l = minIdx;
                count--;
            }
            ans = Math.max(ans, r - l + 1);
            r++;
        }
        return ans;
    }
}
```

改进：map存放字符频率，l右移过程中，减少字符频率。

```java
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        //统计字符最右出现的位置
        int[] map = new int[128];
        int count = 0, l = 0, r = 0, ans = 0;
        while(r < s.length()) {
            char c = s.charAt(r++);
            if(map[c] == 0) count++;
            map[c]++;
            if(count > k) {
                while(l <= r) {
                    map[s.charAt(l)]--;
                    if(map[s.charAt(l++)] == 0) {
                        count--;
                        break;
                    }
                }
            }
            ans = Math.max(ans, r - l);
        }
        return ans;
    }
}
```



## 218 天际线问题

### Solution1: 扫描线 + TreeMap

1. 用ArrayList<int[]>存储建筑的{边界， 高度}List，其中左边界boundary[1] < 0标识。 （不能使用边界标识，要排序！）
2. 用TreeMap统计高度和频率，遇到左边界，对应频率加1，遇到右边界，频率减1。（TreeMap和普通Map一样，但能快速得到最值）
3. TreeMap能用logN的时间复杂度，找到最大值（默认最小值，需要传入比较器）。

CornerCase: 

* 当多个建筑的左边界，右边界重合，应该先遍历哪个边界，来更新高度？ 先遍历最大高度的左边界，再遍历高度最小的右边界。因为如果先遍历高度最小的左边界，会造成该边界的maxHeight不断更新。先遍历高度最大的右边界，假设此时的高度，刚好为频率为1的最大高度，则最大高度更新，第二大高度的右边界，刚好是此时频率为1的最大高度，最大高度cnt - 1，最大高度又要更新，依次类推，造成边界最大高度多次更新。

* 当在distance\[i][0]位置，所有高度都被remove，此时的tMap.firstKey()可能为null，报错，所以需要在一开始tMap.put(0, 1)；

```java
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        //遍历所有边界，得到{边界，高度}List
        ArrayList<int[]> boundaries = new ArrayList<>();
        for(int[] building : buildings) {
            boundaries.add(new int[]{building[0], -building[2]});
            boundaries.add(new int[]{building[1], building[2]});
        }
        Collections.sort(boundaries, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]);
        //和普通map一样，但能快速得到最值
        TreeMap<Integer, Integer> tMap = new TreeMap<>(Collections.reverseOrder());
        tMap.put(0, 1);
        int preHeight = 0;
        List<List<Integer>> ans = new ArrayList<>();
        for(int[] boundary : boundaries) {
            if(boundary[1] < 0) {
                tMap.put(-boundary[1], tMap.getOrDefault(-boundary[1], 0) + 1);
            } else {
                int cnt = tMap.get(boundary[1]);
                if(cnt == 1) {
                    tMap.remove(boundary[1]);
                } else {
                    tMap.put(boundary[1], cnt - 1);
                }
            }
            if(tMap.firstKey() != preHeight) {
                ans.add(Arrays.asList(boundary[0], tMap.firstKey()));
                preHeight = tMap.firstKey();
            }
        }
        return ans;
    }
}
```

### Solution 2: 扫描线 + 优先队列

1. 得到左右边界下标集合boundaries，这里是产生轮廓的位置。
2. 维护一个优点队列，元素为int[]{右边界， 高度}。依次遍历boundaries，和当前建筑idx。
3. 当一个建筑的[l, r)包含boundary，则会对高度产生影响，如果l <= boundary，入队，是可能对高度产生影响的建筑。拿栈顶元素前，将r <= boundary的区间出队。由于建筑是按左边界排序，所有i < idx的元素，也已经入队。

Corner Case:

* 判断pq是否为空，空则说明目前没有建筑对boundary造成影响，此时位于一个建筑群的末端，(boundary, 0)为轮廓。

```java
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        //所有左右边界, TreeSet为排序集合
        Set<Integer> boundaries = new TreeSet<>((o1, o2) -> o1 - o2);
        for(int[] building : buildings) {
            boundaries.add(building[0]);
            boundaries.add(building[1]);
        }
        List<List<Integer>> ans = new ArrayList<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (o1, o2) -> o2[1] - o1[1]);
        int idx = 0, n = buildings.length, preHeight = 0;
        for(int boundary : boundaries) {
            while(idx < n && buildings[idx][0] <= boundary) {
                pq.offer(new int[]{buildings[idx][1], buildings[idx][2]});
                idx++;
            }
            while(!pq.isEmpty() && pq.peek()[0] <= boundary) pq.poll();
            if(pq.isEmpty()) {
                ans.add(Arrays.asList(boundary, 0));
                preHeight = 0;
            } else if(pq.peek()[1] != preHeight) {
                ans.add(Arrays.asList(boundary, pq.peek()[1]));
                preHeight = pq.peek()[1];
            }
        }
        return ans;
    }
}
```

###  Solution3: 二分

问题转换为，如何将左轮廓和右轮廓合并

* 得到左轮廓和右轮廓后，遍历其轮廓(边界， 高度)，新的轮廓在这两者的边界子集中产生，从左至右依次遍历两者边界。
* 维护左右轮廓当前高度，取当前边界处，两者最大值。

```java
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        if(buildings.length == 0){
            return  new ArrayList<>();
        }
        return merge(buildings, 0, buildings.length - 1);
    }
    
    private List<List<Integer>> merge(int[][] buildings, int start, int end) {
        if(start > end) return new ArrayList<>();
        if(start == end){
            List<List<Integer>> skyline = new ArrayList<>();
            skyline.add(Arrays.asList(buildings[start][0],buildings[start][2]));
            skyline.add(Arrays.asList(buildings[start][1],0));
            return skyline;
        }
        int mid = (start + end) / 2;
        List<List<Integer>> leftSkyline  = merge(buildings,start,mid);
        List<List<Integer>> rightSkyline = merge(buildings,mid+1,end);
        List<List<Integer>> curSkyline = new ArrayList<>();
        int leftHeight = 0, rightHeight = 0, curHeight = 0, i = 0, j = 0;
        while(i < leftSkyline.size() || j < rightSkyline.size()){
            if(i == leftSkyline.size()){
                curSkyline.add(rightSkyline.get(j));
                j++;
                continue;
            }
            if(j == rightSkyline.size()){
                curSkyline.add(leftSkyline.get(i));
                i++;
                continue;
            }
            int leftX  = leftSkyline.get(i).get(0);
            int rightX = rightSkyline.get(j).get(0);
            int curX = leftX < rightX ? leftX : rightX;
            if(leftX < rightX){
                leftHeight = leftSkyline.get(i).get(1);
                i++;
            }else if(leftX > rightX){
                rightHeight = rightSkyline.get(j).get(1);
                j++;
            }else{
                leftHeight = leftSkyline.get(i).get(1);
                rightHeight = rightSkyline.get(j).get(1);
                i++;
                j++;
            }
            int maxHeight = Math.max(leftHeight,rightHeight);
            if(maxHeight != curHeight){
                curSkyline.add(Arrays.asList(curX,maxHeight));
                curHeight = maxHeight;
            }
        }
        return curSkyline;
    }
}
```

### Solution4: 离散化 + 线段树

问题转换为：先离散化边界，更新线段树区间的最值，然后求区间每个点的最值。因为涉及区间更新，用懒更新线段树。

* 写离散化和线段树的模板
* 更新区间的最值：注意区间更新时，是左闭右开区间，但线段树都是双闭区间的更新，更新时，只需更新[l, r-1]区间即可。（这样并不会对结果产生影响，有preHeight帮助，在r-1的位置，不会产生轮廓）
* 遍历边界，求最值

线段树太长了，不知道有没有简单的写法😭

```java
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        //离散化：1. treeset排序+去重 2. 用arr和map分别存起来
        TreeSet<Integer> set = new TreeSet<>();
        for(int[] building : buildings) {
            set.add(building[0]);
            set.add(building[1] - 1);
            set.add(building[1]);
        }
        int[] discreArr = new int[set.size()];
        HashMap<Integer, Integer> map = new HashMap<>();
        int idx = 0;
        for(int num : set) {
            discreArr[idx] = num;
            map.put(num, idx++);
        }


        int[] arr = new int[discreArr.length];
        //线段树
        SegmentTree st = new SegmentTree(arr);
        for(int[] building : buildings) {
            st.updateLazyInTree(map.get(building[0]), map.get(building[1] - 1), building[2]);
        }
        boolean begin = true;
        int preHeight = 0;
        List<List<Integer>> ans = new ArrayList<>();
        for(int i = 0; i < discreArr.length; i++) {
            int h = st.query(i, i);
            if(begin && h != 0) {
                ans.add(Arrays.asList(discreArr[i], h));
                begin = false;
            } else if(begin == false && h != preHeight) {
                ans.add(Arrays.asList(discreArr[i], h));
            }
            preHeight = h;
        }
        return ans;
    }
}

class SegmentTree {
    int[] arr, tree, lazy;

    public SegmentTree(int[] arr) {
        this.arr = arr;
        tree = new int[arr.length * 4];
        lazy = new int[arr.length * 4];
        buildTree(tree, arr, 0, 0, arr.length - 1);
    }

    private void buildTree(int[] tree, int[] arr, int node, int l, int r) {
        if(l == r) {
            tree[node] = arr[l];
            return;
        }
        int lNode = 2 * node + 1, rNode = 2 * node + 2, mid = (l + r) / 2;
        buildTree(tree, arr, lNode, l, mid);
        buildTree(tree, arr, rNode, mid + 1, r);
        tree[node] = Math.max(tree[lNode], tree[rNode]);
    }

    public void updateLazyInTree(int l, int r, int val) {
        updateLazyInTree(0, 0, arr.length - 1, l, r, val);
    }

    private void updateLazyInTree(int node ,int s, int e, int l, int r, int val) {
        if(s >= l && e <= r) {
            tree[node] = Math.max(tree[node], val);
            lazy[node] = Math.max(lazy[node], val);
            return;
        }
        pushDown(node, s, e);
        int lNode = 2 * node + 1, rNode = 2 * node + 2, mid = (s + e) / 2;
        if(r <= mid) {
            updateLazyInTree(lNode, s, mid, l, r, val);
            return;
        } else if (l > mid) {
            updateLazyInTree(rNode, mid + 1, e, l, r, val);
            return;
        }
        updateLazyInTree(lNode, s, mid, l, mid, val);
        updateLazyInTree(rNode, mid + 1, e, mid + 1, r, val);
    }

    private void pushDown(int node, int s, int e) {
        if(s < e) {
            int lNode = 2 * node + 1, rNode = 2 * node + 2, mid = (s + e) / 2;
            tree[lNode] = Math.max(tree[lNode], lazy[node]);
            tree[rNode] = Math.max(tree[rNode], lazy[node]);
            lazy[lNode] = Math.max(lazy[lNode], lazy[node]);
            lazy[rNode] = Math.max(lazy[rNode], lazy[node]);
            lazy[node] = -1;
        }
    }

    public int query(int l, int r) {
        return query(0, 0, arr.length - 1, l, r);
    }

    private int query(int node, int s, int e, int l, int r) {
        if(s >= l && e <= r) {
            return tree[node];
        }
        pushDown(node, s, e);
        int lNode = 2 * node + 1, rNode = 2 * node + 2, mid = (s + e) / 2;
        if(r <= mid) {
            return query(lNode, s, mid, l, r);
        } else if(l > mid) {
            return query(rNode, mid + 1, e, l ,r);
        }
        return Math.max(query(lNode, s, mid, l, mid), query(rNode, mid + 1, e, mid + 1, r));
    }
}
```



## 6.Z字型变换

模拟按什么顺序，在numRows个字符串后添加字符。

StringBuilder比String快很多

```java
class Solution {
    public String convert(String s, int numRows) {
        StringBuilder[] strArr = new StringBuilder[numRows];
        for(int i = 0; i < numRows; i++) strArr[i] = new StringBuilder();
        int idx = 0;
        while(idx < s.length()) {
            for(int i = 0; i < numRows; i++) {
                if(idx == s.length()) break;
                strArr[i].append(s.charAt(idx++));
            }
            for(int i = numRows - 2; i >= 1; i--) {
                if(idx == s.length()) break;
                strArr[i].append(s.charAt(idx++));
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < numRows; i++) sb.append(strArr[i]);
        return sb.toString();
    }
}
```





## 7.整数反转

关键点在于，怎么判断x反转后，有没有越界

证明： rev <= Integer.MAX_VALUE. ---> rev * 10 + digit <= MAX_VALUE/10 * 10 + 7. ---> (rev - MAX_VALUE/10) * 10 <= 7 - digit

因为digit是x的最高位，Integer.MAX_VALUE最高位为2，所以digit <= 2，所以当rev <= MAX_VALUE/10时，不等式一定满足的。

同理，rev >= MIN_VALUE/10，不会越界。

```java
class Solution {
    public int reverse(int x) {
        int rev = 0;
        while(x != 0) {
            if(rev < Integer.MIN_VALUE / 10 || rev > Integer.MAX_VALUE / 10)
                return 0;
            rev = rev * 10 + x % 10;
            x /= 10;
        }
        return rev;
    }
}
```

也可以用StringBuilder方法，需要用**Integer.parseInt(s)**和**try -catch**判断越界



## 8.字符串转换整数

判断字符串ans乘10后是否越界，不推荐使用ans > Integer.MAX_VALUE / 10 ? 好像并不是充要条件。还是用lon g比较靠谱

```java
class Solution {
    public int myAtoi(String s) {
        //删除前置的空格，判断符号，读取右边界得到，从低位到高位构造整数
        s = s.trim();
        if(s.length() == 0) return 0;
        if(s.charAt(0) != '+' && s.charAt(0) != '-' && (s.charAt(0) < '0' || s.charAt(0) > '9')) return 0;
        int flag = 1;
        if(s.charAt(0) == '-') flag = -1;
        if(s.charAt(0) == '+' || s.charAt(0) == '-') s = s.substring(1);
        int idx = 0;
        long ans = 0;
        while(idx < s.length() && s.charAt(idx) >= '0' && s.charAt(idx) <= '9') {
            ans = ans * 10 + (long)(s.charAt(idx++) - '0');
            if((ans * (long)flag) > (long)Integer.MAX_VALUE) return Integer.MAX_VALUE;
            if((ans * (long)flag) < (long)Integer.MIN_VALUE) return Integer.MIN_VALUE;
        }
        return (int)(ans * flag);
    }
}
```

## 10. 正则表达式匹配

dp，注意p.charAt(j) == '*'时，且前一个字符匹配成功时，dp\[i][j] = dp\[i-1][j] || dp\[i][j-2]; 表示如果\*表示一个或多个p.charAt(j-1)，则可以认为dp\[i][j] = ddp\[i-1][j]，否则可以认为\*代表了0个上一个字符，即dp\[i][j-2]

```java
class Solution {
    public boolean isMatch(String s, String p) {
        //dp[i][j]表示[0, i)和p[0, j]是否匹配
        //初始化dp
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for(int j = 1; j <= n; j++) {
            if(p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j-2];
            }
        }
        for(int i = 1; i <= m; i++)
            for(int j = 1; j <= n; j++) {
                if(s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
                    dp[i][j] = dp[i-1][j-1];
                }
                if(p.charAt(j-1) == '*') {
                    if(s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.') {
                        dp[i][j] = dp[i-1][j] || dp[i][j-2];
                    } else {
                        dp[i][j] = dp[i][j-2];
                    }
                }
            }
        return dp[m][n];
    }
}
```



## 14. 最长公共前缀

根据第一个字符串，判断最长公共前缀

注意越界判断

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if(strs.length == 0) return "";
        for(int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for(int j = 1; j < strs.length; j++) {
                if(i >= strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }
}
```



## 17.电话号码的字母组合

定长DFS模板题，注意路径直接可以用数组char[] path保存。

```java
class Solution {
    public List<String> letterCombinations(String digits) {
        int n = digits.length();
        if(n == 0) return new ArrayList<>();
        char[] path = new char[n];
        List<String> ans = new ArrayList<>();
        String[] phone = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        dfs(phone, digits, 0, path, ans);
        return ans;
    }
    
    private void dfs(String[] phone, String digits, int index, char[] path, List<String> ans) {
        if(index == digits.length()) {
            ans.add(new String(path));
            return;
        }
        char c = digits.charAt(index);
        for(int i = 0; i < phone[c-'0'].length(); i++) {
            path[index] = phone[c-'0'].charAt(i);
            dfs(phone, digits, index+1, path, ans);
        }  
    }
}
```



## 20. 有效的括号

```java
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stk = new Stack<>();
        for(char c : s.toCharArray()) {
            if(c == '[' || c == '{' || c == '(') {
                stk.push(c);
            } else if(c == ']' && !stk.isEmpty() && stk.peek() == '[') {
                stk.pop();
            } else if(c == '}' && !stk.isEmpty() && stk.peek() == '{') {
                stk.pop();
            } else if(c == ')' && !stk.isEmpty() && stk.peek() == '(') {
                stk.pop();
            } else {
                return false;
            }
        }
        return stk.isEmpty();
    }
}
```



## 21. 合并两个有序链表

```java
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        if(l1.val <= l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        }
        l2.next = mergeTwoLists(l1, l2.next);
        return l2;
    }
}
```



## 22. 括号生成

1. 定长DFS，可以用char[]表示路径。
2. 遍历过程中，保证生成有效括号的充要条件：左括号数量 >= 右括号数量。换句话说，只要保证此条件，并且左括号数量 <= n，递归终止时，一定是有效括号。

```java
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        dfs(0, 0, n, new char[2 * n], ans);
        return ans;
    }

    private void dfs(int lCount, int rCount, int n, char[] path, List<String> ans) {
        int idx = lCount + rCount;
        if(idx == 2 * n) {
            ans.add(new String(path));
            return;
        }
        if(lCount >= rCount && lCount < n) {
            path[idx] = '(';
            dfs(lCount + 1, rCount, n, path, ans);
        }
        if(lCount > rCount) {
            path[idx] = ')';
            dfs(lCount, rCount + 1, n, path, ans);
        }
    }
}
```



## 28. 实现strStr()

KMP模板题

1. 构造next数组，next[]含义是，在[0, i]的子串，不包含自身的最长相等前后缀长度。作用是匹配时s1[i] != s1[j]，可以快速移动j
2. 构造next数组从下标为1开始构造，找到某一下标j，使得s2[j] == s2[i]，不等，则**j = next[j-1]**（可以证明next[i]的最长前后缀长度一定在next[j-1]中，证明略）。
3. 匹配时，不等则移动s2的待匹配下标j至**next[j-1]**，匹配相等则j++

```java
class Solution {
    public int strStr(String haystack, String needle) {
        if(needle.length() == 0) return 0;
        return kmp(haystack, needle);
    }

    private int kmp(String str1, String str2) {
        int[] next = new int[str2.length()];
        for(int i = 1; i < str2.length(); i++) {
            int j = next[i-1];
            while(j != 0 && str2.charAt(i) != str2.charAt(j)) {
                j = next[j-1];
            }
            if(str2.charAt(j) == str2.charAt(i)) next[i] = j + 1;
        }

        int j = 0;
        for(int i = 0; i < str1.length(); i++) {
            while(j != 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j-1];
            }
            if(str1.charAt(i) == str2.charAt(j)) {
                j++;
                if(j == str2.length()) return i - j  + 1;
            }
        }

        return -1;
    }
}
```

## 32. 最长有效括号

### Solution1: DP

dp[i]表示以i结尾的最长有效括号，显然有效括号必须以右括号结束。当dp[i]为右括号时，有两种情况：

1. dp[i-1]为左括号，则dp[i] = dp[i-2] + 2
2. dp[i-1]为右括号，则需要考虑下标为i - 2 - dp[i-1]是否为左括号，是左括号，则dp[i] = dp[i-1] + 2 + **dp[i-2-dp[i-1]]**

```java
class Solution {
    public int longestValidParentheses(String s) {
        int n = s.length();
        if(n == 0) return 0;
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 0;
        int ans = 0;
        for(int i = 2; i <= n; i++) {
            if(s.charAt(i-1) == ')') {
                if(s.charAt(i-2) == '(') {
                    dp[i] = dp[i-2] + 2;
                } else if(i - 2 - dp[i-1] >= 0 && s.charAt(i - 2 - dp[i-1]) == '(') {
                    dp[i] = dp[i-1] + dp[i-dp[i-1]-2] + 2;
                }
                ans = Math.max(ans, dp[i]);
            }
        }
        return ans;
    }
}
```

### Solution2: stack

1. 栈底保存最新未匹配的右括号
2. 遇到右括号，出栈，并且根据stk.peek()获得当前右括号的最长有效括号

```java
class Solution {
    public int longestValidParentheses(String s) {
        //stack: stack栈底储存上一个未被匹配的')'下标，其余存储'('的下标
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int ans = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                stack.push(i);
            } else{
                stack.pop();
                if(stack.isEmpty()) {
                    //此右括号没有被匹配上，更新栈底
                    stack.push(i);
                }else {
                    ans = Math.max(ans, i - stack.peek());
                }
            }
        }
        return ans;
    }
}
```



## 37. 解数独

1. DFS：从上到下，从左到右穷举所有"."的可能结果，到达棋盘最末端即返回成功。
2. DFS返回true：表示(i, j)位置填入num，是一种可能解，返回false，表示(i, j)无论填什么，都无法构成有效解，需要之前节点重新选择

1. 如果保存已经填入的结果：boolean\[][] rows, cols, cubes
2. 如何计算(i, j)在某一cube内？(i / 3) * 3 + j / 3

```java
class Solution {
    boolean[][] rows, cols, cubes;
    char[][] board;
    public void solveSudoku(char[][] board) {
        rows = new boolean[9][10];
        cols = new boolean[9][10];
        cubes = new boolean[9][10];
        this.board = board;
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                if(board[i][j] != '.') {
                    int digit = board[i][j] - '0';
                    rows[i][digit] = cols[j][digit] = cubes[cptCube(i, j)][digit] = true;
                }
        dfs(0, 0);
    }

    private boolean dfs(int i, int j) {
        //快速移动到下一个节点
        while(i < 9 && board[i][j] != '.') {
            i = j == 8 ? i + 1 : i;
            j = j == 8 ? 0 : j + 1;
        }
        if(i == 9) return true;
        //穷举每个节点路径
        for(int num = 1; num <= 9; num++) {
            if(rows[i][num] || cols[j][num] || cubes[cptCube(i, j)][num]) continue;
            rows[i][num] = cols[j][num] = cubes[cptCube(i, j)][num] = true;
            board[i][j] = (char)('0' + num);
            if(dfs(i, j)) return true;
            board[i][j] = '.';
            rows[i][num] = cols[j][num] = cubes[cptCube(i, j)][num] = false;
        }
        return false;
    }

    private int cptCube(int i, int j) {
        return (i / 3) * 3 + j / 3;
    }
}
```



## 44. 通配符匹配

dp

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        for(int i = 1; i <= n; i++) {
            if(p.charAt(i-1) != '*')
                break;
            dp[0][i] = true;
        }
        
        for(int i = 1; i <= m; i++)
            for(int j = 1; j <= n; j++) {
                if(s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '?')
                    dp[i][j] = dp[i-1][j-1];
                else if(p.charAt(j-1) == '*')
                    dp[i][j] = dp[i-1][j] || dp[i][j-1];
                else
                    dp[i][j] = false;
            }
        
        return dp[m][n];
    }
}
```



## 46.全排列

1. 定长DFS，用visited数组标记已访问元素很容易。
2. 可以直接在nums上进行当前位置start与[start, len-1]元素交换，使用常数空间。
3. 注意Java中int[]转List

```java
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        dfs(nums, ans, 0);
        return ans;
    }

    private void dfs(int[] nums, List<List<Integer>> ans, int start) {
        if(start == nums.length) {
            ans.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
            return;
        }

        for(int i = start; i < nums.length; i++) {
            swap(nums, start, i);
            dfs(nums, ans, start + 1);
            swap(nums, start, i);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
```

## 47. 全排列II

1. 关键点在于如何去重：先排序数组，然后在idx位置填入数字时，保证不填入重复数字。
2. i > 0 && nums[i] == nums[i-1] && !visited[i-1]：保证从左到右，只填入第一个未被填入数字。
3. 三个连续数字，一次经过[未填入，未填入，未填入] [填入，未填入，未填入] [填入，填入，未填入] [填入，填入，填入]。
4. 不能简单的利用nums[i] == nuts[i-1]，还需要判断visited数组。

```java
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        Arrays.sort(nums);
        permuteGenerate(nums, visited, path, ans);
        return ans;
    }
    
    private void permuteGenerate(int[] nums, boolean[] visited, ArrayList<Integer> path, List<List<Integer>> ans) {
        if(path.size() == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }
        
        for(int i = 0; i < nums.length; i++) {
            if(i > 0 && nums[i] == nums[i-1] && !visited[i-1]) {
                continue;
            }
            if(!visited[i]) {
                visited[i] = true;
                path.add(nums[i]);
                permuteGenerate(nums, visited, path, ans);
                visited[i] = false;
                path.remove(path.size()-1);
            }
        }
    }
}
```

## 48.旋转图像

1. 先上下翻转图像，再沿主对角线对称翻转图像.

```java
class Solution {
    public void rotate(int[][] matrix) {
        //坐标关系matrix_temp[j][n-i-1] = matrix[i][j];
        //分解为先上下翻转，在对角线翻转
        int n = matrix.length;
        for(int i = 0; i < n / 2; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n-1-i][j];
                matrix[n-1-i][j] = tmp; 
            }
        }
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < i; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
    }
}
```

## 49.字母异位词分组

1. 将异位单词转换为相同的key，放在map里面，从而实现分组。
   1. 单词字典序构造key
   2. 统计频率构造key

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for(String str : strs) {
            String tmp = convert(str);
            List<String> list = map.computeIfAbsent(tmp, k -> new ArrayList<String>());
            list.add(str);
        }
        return new ArrayList<List<String>>(map.values());
    }

    private String convert(String str) {
        char[] chs = str.toCharArray();
        Arrays.sort(chs);
        return new String(chs);
    }
}
```

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for(String str : strs) {
            String tmp = convert(str);
            List<String> list = map.computeIfAbsent(tmp, k -> new ArrayList<String>());
            list.add(str);
        }
        return new ArrayList<List<String>>(map.values());
    }

    private String convert(String str) {
        int[] counts = new int[26];
        for(char c : str.toCharArray()) {
            counts[c-'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 26; i++) {
            if(counts[i] != 0) {
                sb.append((char)(i + 'a'));
                sb.append(counts[i]);
            }
        }
        return sb.toString();
    }
}
```



## 51.N皇后

1. 定长DFS（递归终止深度是N）+ 回溯（每次递归，放置棋子+撤销棋子)
2. 使用布尔数组记录纵线，斜线上是否放置有棋子，从而判断(i, j)是否能放棋子。
3. 如何表示不同方向斜线：i+j 和i-j，为了保证大于等于0，i-j+n-1

```java
class Solution {
    boolean[] cols, dia1, dia2;
    int[] queens;
    int n;

    public List<List<String>> solveNQueens(int n) {
        cols = new boolean[n];
        dia1 = new boolean[2 * n];
        dia2 = new boolean[2 * n];
        queens = new int[n];
        this.n = n;
        List<List<String>> ans = new ArrayList<>();
        dfs(0, ans);
        return ans;
    }

    private void dfs(int row, List<List<String>> ans) {
        for(int i = 0; i < n; i++) {
            if(canPlace(row, i)) {
                placeQueen(row, i);
                if(row == n - 1) {buildBoard(ans);}
                else dfs(row + 1, ans);
                removeQueen(row, i);
            }
        }
    }

    private boolean canPlace(int i, int j) {
        return !cols[j] && !dia1[i+j] && !dia2[i-j+n-1];
    }

    private void placeQueen(int i, int j) {
        cols[j] = true;
        dia1[i+j] = true;
        dia2[i-j+n-1] = true;
        queens[i] = j;
    }

    private void removeQueen(int i, int j) {
        cols[j] = false;
        dia1[i+j] = false;
        dia2[i-j+n-1] = false;
    }

    private void buildBoard(List<List<String>> ans) {
        List<String> board = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            char[] chs = new char[n];
            Arrays.fill(chs, '.');
            chs[queens[i]] = 'Q';
            board.add(new String(chs));
        }
        ans.add(board);
    }
}
```



## 52.N皇后

与51题一样

```java
class Solution {
    boolean[] cols, dia1, dia2;
    int[] queens;
    int n, ans;
    public int totalNQueens(int n) {
        cols = new boolean[n];
        dia1 = new boolean[2 * n];
        dia2 = new boolean[2 * n];
        queens = new int[n];
        this.n = n;
        this.ans = 0;
        dfs(0);
        return ans;
    }

    private void dfs(int row) {
        for(int i = 0; i < n; i++) {
            if(canPlace(row, i)) {
                placeQueen(row, i);
                if(row == n - 1) {ans++;}
                else dfs(row + 1);
                removeQueen(row, i);
            }
        }
    }

    private boolean canPlace(int i, int j) {
        return !cols[j] && !dia1[i+j] && !dia2[i-j+n-1];
    }

    private void placeQueen(int i, int j) {
        cols[j] = true;
        dia1[i+j] = true;
        dia2[i-j+n-1] = true;
        queens[i] = j;
    }

    private void removeQueen(int i, int j) {
        cols[j] = false;
        dia1[i+j] = false;
        dia2[i-j+n-1] = false;
    }
}
```



## 54.螺旋矩阵

1. 维护四条边，每循环一次，消除一圈。
2. 消除过程中无法构成矩形，退出循环。

```java
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        int u = 0, d = matrix.length - 1, l = 0, r = matrix[0].length - 1;
        List<Integer> ans = new ArrayList<>();
        while(true) {
            for(int i = l; i <= r; i++) ans.add(matrix[u][i]);
            if(++u > d) break;
            for(int i = u; i <= d; i++) ans.add(matrix[i][r]);
            if(--r < l) break;
            for(int i = r; i >= l; i--) ans.add(matrix[d][i]);
            if(--d < u) break;
            for(int i = d; i >= u; i--) ans.add(matrix[i][l]);
            if(++l > r) break;
        }
        return ans;
    }
}
```



## 55. 跳跃游戏

* r为行动右边界，i为当前位置

```java
class Solution {
    public boolean canJump(int[] nums) {
        int r = 0;
        for(int i = 0; i < nums.length; i++) {
            if(r < i) return false;
            r = Math.max(r, i + nums[i]);
        }
        return true;
    }
}
```



