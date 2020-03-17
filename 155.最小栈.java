/*
 * @lc app=leetcode.cn id=155 lang=java
 *
 * [155] 最小栈
 */

// @lc code=start
class MinStack {
    private Stack<Integer> stack;
    private Stack<Integer> preMin;
    private int min;
    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<>();
        preMin = new Stack<>();
        min = Integer.MAX_VALUE;
    }
    
    public void push(int x) {
        stack.push(x);
        if(x < min){
            preMin.push(min);
            min = x;
        }else
            preMin.push(min);
    }
    
    public void pop() {
        int x = stack.pop();
        if(x == min)
            min = preMin.pop();
        else
            preMin.pop();
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return min;
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
// @lc code=end

