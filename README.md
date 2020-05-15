# Leetcode笔记

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

