/*
 * @lc app=leetcode.cn id=232 lang=java
 *
 * [232] 用栈实现队列
 */

// @lc code=start
class MyQueue {
    Stack<Integer> stackOrder;
    Stack<Integer> queueOrder;

    /** Initialize your data structure here. */
    public MyQueue() {
        stackOrder = new Stack<>();
        queueOrder = new Stack<>();
    }
    
    /** Push element x to the back of queue. */
    public void push(int x) {
        stackOrder.push(x);
    }
    
    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        if(queueOrder.isEmpty()){
            while(!stackOrder.isEmpty())
                queueOrder.push(stackOrder.pop());
        }
        return queueOrder.pop();
    }
    
    /** Get the front element. */
    public int peek() {
        if(queueOrder.isEmpty()){
            while(!stackOrder.isEmpty())
                queueOrder.push(stackOrder.pop());
        }
        return queueOrder.peek();
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        return stackOrder.isEmpty() && queueOrder.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
// @lc code=end

