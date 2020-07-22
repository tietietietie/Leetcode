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
        }
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

