package databricks;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

/*
leetcode 239
 */
class Node {
    public int pos, val;
    public Node(int pos, int val) {
        this.pos = pos;
        this.val = val;
    }
}
public class SlidingWindowMaximum {
    public int[] maxSlidingWindow(int[] nums, int k) {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(b.val, a.val));
        int[] res = new int[nums.length - k + 1];

        int st = 0;
        for (int i = 0; i < nums.length; i ++) {
            pq.add(new Node(i, nums[i]));
            if (i >= k - 1) {
                while(!pq.isEmpty()) {
                    Node cur = pq.peek();
                    if (cur.pos >= st) {
                        res[st] = cur.val;
                        break;
                    } else {
                        pq.poll();
                    }
                }
                st ++;
            }
        }
        return res;
    }

    public void cleanQueue(ArrayDeque<Integer> q, int[] nums, int i, int k) {
        if (!q.isEmpty() && q.getFirst() <= i - k) {
            q.removeFirst();
        }
        while(!q.isEmpty() && nums[i] >= nums[q.getLast()]) {
            q.removeLast();
        }
    }
    public int[] maxSlidingWindow1(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];
        ArrayDeque<Integer> q = new ArrayDeque<>();
        int st = 0;
        for (int i = 0; i < nums.length; i ++) {
            cleanQueue(q, nums, i, k);
            q.addLast(i);
            if (i >= k - 1) {
                res[st ++] = nums[q.getFirst()];
            }
        }
        return res;
    }
}
