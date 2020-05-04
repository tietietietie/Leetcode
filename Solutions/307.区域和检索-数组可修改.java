/*
 * @lc app=leetcode.cn id=307 lang=java
 *
 * [307] 区域和检索 - 数组可修改
 */

// @lc code=start
class NumArray {
    private int[] nums;
    private int[] tree;

    public NumArray(int[] nums) {
        this.nums = nums;
        this.tree = new int[4 * nums.length];
        buildTree(0,0,nums.length-1);
    }

    private void buildTree(int node, int start, int end){
        if(start > end) return;
        if(start == end){
            tree[node] = nums[start];
            return;
        }
        int leftNode  = 2 * node + 1;
        int rightNode = 2 * node + 2;
        int mid = (start + end) / 2;
        buildTree(leftNode,start,mid);
        buildTree(rightNode,mid+1,end);
        tree[node] = tree[leftNode] + tree[rightNode];
    }
    
    public void update(int i, int val) {
        update(0,0,nums.length-1,i,val);
    }

    private void update(int node, int start, int end, int idx, int val){
        if(idx < start || idx > end) return;
        if(start == end){
            tree[node] = val;
            nums[idx] = val;
            return;
        }
        int leftNode = 2 * node + 1;
        int rightNode = 2 * node + 2;
        int mid = (start + end) / 2;
        if(idx <= mid){
            update(leftNode,start,mid,idx,val);
        }else{
            update(rightNode,mid+1,end,idx,val);
        }
        tree[node] = tree[leftNode] + tree[rightNode];
    }
    
    public int sumRange(int i, int j) {
        return sumRange(0,0,nums.length-1,i,j);
    }

    private int sumRange(int node, int start, int end, int L, int R){
        if(end < L || start > R) return 0;
        if(start >= L && end <= R) return tree[node];
        int leftNode = 2 * node + 1;
        int rightNode = 2 * node + 2;
        int mid = (start + end) / 2;
        int leftSum = sumRange(leftNode,start,mid,L,R);
        int rightSum = sumRange(rightNode,mid+1,end,L,R);
        return leftSum+rightSum;
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */
// @lc code=end

