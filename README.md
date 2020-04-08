# Leetcode笔记

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

