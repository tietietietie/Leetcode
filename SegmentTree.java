import java.util.*;
public class SegmentTree {
    int[] arr, tree, lazy;
    private int merge(int i, int j) {
        return i + j;
    }
    public SegmentTree(int[] arr) {
        this.arr = arr;
        tree = new int[arr.length * 4];
        lazy = new int[arr.length * 4];
        buildTree(0, 0, arr.length - 1);
    }

    private void buildTree(int node, int l, int r) {
        if(l == r) {
            tree[node] = arr[l];
            return;
        }
        int lNode = node * 2 + 1, rNode = node * 2 + 2, mid = (l + r) / 2;
        buildTree(lNode, l, mid);
        buildTree(rNode, mid + 1, r);
        tree[node] = merge(tree[lNode], tree[rNode]);
    }
    //更新arr[idx]的值为val
    public void update(int idx, int val) {
        update(0, 0, arr.length - 1, idx, val);
    }

    private void update(int node, int l, int r, int idx, int val) {
        if(l == r) {
            tree[node] = val;
            return;
        }
        int lNode = node * 2 + 1, rNode = node * 2 + 2, mid = (l + r) / 2;
        if(idx <= mid) {
            update(lNode, l, mid, idx, val);
        } else {
            update(rNode, mid + 1, r, idx, val);
        }
        tree[node] = merge(tree[lNode], tree[rNode]);
    }

    public int query(int l, int r) {
        return query(0, 0, arr.length - 1, l, r);
    }

    private int query(int node, int s, int e, int l, int r) {
        if(s == l && e == r) {
            return tree[node];
        }
        int lNode = node * 2 + 1, rNode = node * 2 + 2, mid = (s + e) / 2;
        if(r <= mid) {
            return query(lNode, s, mid, l, r);
        } else if(l > mid) {
            return query(rNode, mid + 1, e, l, r);
        }
        return merge(query(lNode, s, mid, l, mid), query(rNode, mid + 1, e, mid + 1, r));
    }

    public void updateLazyInTree(int l, int r, int val) {
        update(0, 0, arr.length - 1, l, r, val);
    }

    private void updateLazyInTree(int node, int s, int e, int l, int r, int val) {
        if(s >= l && e <= r) {
            
        }
    }


    public static void main(String[] args) {
        int[] arr = new int[]{1, 1, 1, 1, 1};
        SegmentTree st = new SegmentTree(arr);
        for(int num : st.tree) {
            System.out.print(num + " ");
        }
    }
}