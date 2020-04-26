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

