public class BinarySearch {
    //有单调性一定可以二分，能二分不一定要有单调性
    //本质是有某种性质，使得区间一分为二,答案一定在区间里面。
    //保证不死循环，则l, r每次循环必须有一个改变！

    //区间为[l, mid],[mid+1, r]
    public int binary_1(int l, int r) {
        while(l < r) {
            //mid可能去到l，所以l一定不能等于mid
            int mid = (l + r) / 2;
            if(check(mid)) r = mid;
            else l = mid+1;
        }
        return l;
    }

    //区间为[l, mid-1], [mid, r]
    public int binary_1(int l, int r) {
        while(l < r) {
            //mid可能去到r，所以r一定不能等于mid
            int mid = (l + r + 1) / 2;
            if(check(mid)) l = mid;
            else r = mid-1;
        }
        return l;
    }

    //浮点数二分
    public double binary(double l, double r) {
        while(r - l > 1e-8) {
            //mid可能去到r，所以r一定不能等于mid
            double mid = (l + r) / 2;
            if(check(mid)) l = mid;
            else r = mid;
        }
        return l;
    }
}
