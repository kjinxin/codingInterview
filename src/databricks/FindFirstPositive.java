package databricks;

import java.util.Arrays;
import java.util.List;

public class FindFirstPositive {
    public int findFirstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i ++) {
            int val = Math.abs(nums[i]);
            if (val >= 0 && val < nums.length) {
                if (nums[val] == 0) {
                    nums[0] *= -1;
                    nums[val] = Integer.MIN_VALUE;
                } else {
                    nums[val] *= -1;
                }
            }
        }
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i ++) {
            if (nums[i] > 0) {
                res = i;
                break;
            }
        }

        for (int i = 0; i < nums.length; i ++) {
            if (nums[i] == Integer.MIN_VALUE) {
                nums[i] = 0;
            } else if (nums[i] < 0) {
                nums[i] *= -1;
            }
            System.out.printf("%d ", nums[i]);
        }
        System.out.println();

        return res == Integer.MIN_VALUE ? nums.length : res;
    }

    public static void main(String[] args) {
        FindFirstPositive findFirstPositive = new FindFirstPositive();
        System.out.println(findFirstPositive.findFirstMissingPositive(new int[]{0, 1, 3}));

        System.out.println(findFirstPositive.findFirstMissingPositive(new int[]{0, 1, 2}));

        System.out.println(findFirstPositive.findFirstMissingPositive(new int[]{3, 2, 1}));

        System.out.println(findFirstPositive.findFirstMissingPositive(new int[]{3, 2, 1, 0, 50, 40, 6}));
    }
}
