package databricks;
/*
leetcode 42
https://leetcode.com/problems/trapping-rain-water/
https://www.1point3acres.com/bbs/thread-657438-1-1.html
https://www.1point3acres.com/bbs/thread-668417-1-1.html
https://www.1point3acres.com/bbs/thread-668402-1-1.html
 */
public class TrappingRainWater {
    public int trap(int[] height) {
        int leftMax = height[0], rightMax = height[height.length - 1], l = 0, r = height.length - 1, res = 0;
        while(l <= r) {
            leftMax = Math.max(leftMax, height[l]);
            rightMax = Math.max(rightMax, height[r]);
            if (leftMax < rightMax) {
                res += leftMax - height[l];
                l ++;
            } else {
                res += rightMax - height[r];
                r --;
            }
        }
        return res;
    }
}
