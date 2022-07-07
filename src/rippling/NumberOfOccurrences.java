package rippling;
import java.util.*;
/*
https://leetcode.com/problems/unique-number-of-occurrences/
https://www.1point3acres.com/bbs/thread-893795-1-1.html
 */
public class NumberOfOccurrences {
    //hashmap and set
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> count = new HashMap();
        Set<Integer> set = new HashSet();
        for (int i : arr) {
            count.put(i, count.getOrDefault(i, 0) + 1);
        }
        for (int key : count.keySet()) {
            if (set.contains(count.get(key))) {
                return false;
            }
            set.add(count.get(key));
        }
        return true;
    }

    public boolean uniqueOccurrences2(int[] arr) {
        Arrays.sort(arr);
        int cnt = 1, prevVal = arr[0];
        arr[0] = Integer.MIN_VALUE;
        for (int i = 1; i < arr.length; i ++) {
            if (arr[i] != prevVal) {
                if (arr[cnt - 1] != Integer.MIN_VALUE) {
                    return false;
                }
                arr[cnt - 1] = cnt;
                cnt = 1;
            } else {
                cnt ++;
            }
            prevVal = arr[i];
            arr[i] = Integer.MIN_VALUE;
        }
        return arr[cnt - 1] == Integer.MIN_VALUE;
    }
}
