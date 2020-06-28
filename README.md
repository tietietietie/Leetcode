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
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l = new ListNode((l1.val+l2.val)%10);
        int carry = (l1.val+l2.val)/10;
        ListNode temp = l;
        for(ListNode i = l1.next, j = l2.next; i != null || j != null || carry != 0;
        i = i.next, j = j.next){
            if(i == null)
                i = new ListNode(0);
            if(j == null)
                j = new ListNode(0);
            temp.next = new ListNode((i.val+j.val+carry)%10);
            temp = temp.next;
            carry = (i.val+j.val+carry)/10;
        }
        return l;
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

## 11.承最多水的容器

* 正确思路：双指针
* 注意
  * 默认最大容积为宽最长的容器，要使容积变大，必须在宽度变小的同时，增大最小边。
  * 为什么不能移动最长边呢？因为移动最长边，并不能使容积变大

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

## 15.三数和

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
        int s = nums.length == 0 ? 0 : 1;
        for(int num : nums)
            if(num > nums[s-1])
                nums[s++] = num;
        return s;
    }
}
```

## 27.移除元素

* 快慢指针：慢指针左侧：无重复元素的数组，快指针：当前判断的元素
* 时间复杂度o(n)，空间复杂度：o(1)

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int s = 0;
        for(int num : nums)
            if(num != val)
                nums[s++] = num;
        return s;
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



## 33.查询旋转有序数组

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

* 二分法：判断mid和target是否在同一个递增数组，如果在，则按照常规二分处理，如果在两个不同的数组，则判断target在左侧数组还是右侧数组，按照此来进行二分。
* 时间复杂度：o(n)，空间复杂度o(1)。

```java
class Solution {
    public int search(int[] nums, int target) {
        if(nums == null || nums.length == 0) return -1;
        int l = 0, r = nums.length-1, n = nums.length;
        while(l < r){
            int mid = (l+r)/2;
            if((nums[mid] - nums[n-1]) * (target - nums[n-1]) > 0){
                if(nums[mid] < target) l = mid+1;
                else r = mid;
            }else if(target > nums[n-1])
                r = mid;
            else
                l = mid+1;
        }
        if(nums[l] == target) return l;
        return -1;
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

## 39.组合总和

* 回溯法：排序数组，由于是组合问题和无限背包问题，需要设置start节点，start的子节点只能在start~n-1中选择，选择并递归后，记得撤销选择。
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

## 40.组合总和Ⅱ

* 回溯法：
  * 重复值问题：路径选择时，重复的数值只能只能选一次
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

## 41.缺失的第一个正数

* 元素nums[index]的正负号作为该该数组是否存在index的标志，首先，将数组中的非正数全部设置为n+1，对于nums[index]大于n的元素不需要考虑，小于等于n的元素，将对应index的元素值设置为负数，最后遍历整个数组，如果nums[index]处的元素为正，说明index+1没有出现过，返回第一个正数下标。
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

## 218.奇偶链表

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

## 8.atoi

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

* 只需要反转一半的数字进行比较
* O(n)/O(1)

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

* 竖式乘法
  * 如何进位？从低位开始，得到p1,p2，其中p2的值一定<10，而p1的值可以大于10，由于是从大到小更新digits，最终只有最高位的值可以>10
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

