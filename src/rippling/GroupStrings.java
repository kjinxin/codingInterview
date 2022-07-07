package rippling;
import java.util.*;
/*
leetcode 839: https://leetcode.com/problems/similar-string-groups/
https://www.1point3acres.com/bbs/thread-904814-1-1.html
 */
public class GroupStrings {
    private int findFa(int[] fa, int cur) {
        if (fa[cur] != cur) {
            fa[cur] = findFa(fa, fa[cur]);
        }
        return fa[cur];
    }

    private void union(int[] fa, int i, int j) {
        int faI = findFa(fa, i);
        int faJ = findFa(fa, j);
        fa[faJ] = faI;
    }
    public int numSimilarGroups(String[] strs) {
        int[] fa = new int[strs.length];
        //initialize union find
        for (int i = 0; i < fa.length; i ++) {
            fa[i] = i;
        }
        for (int i = 0; i < strs.length; i ++) {
            for (int j = 0; j < strs.length; j ++) {
                if (isConnected(strs[i], strs[j])) {
                    union(fa, i, j);
                }
            }
        }
        Set<Integer> groups = new HashSet();
        for (int i = 0; i < fa.length; i ++) {
            int faI = findFa(fa, i);
            groups.add(faI);
        }
        return groups.size();
    }

    private boolean isConnected(String a, String b) {
        int cnt = 0;
        for (int i = 0; i < a.length(); i ++) {
            if (a.charAt(i) != b.charAt(i)) {
                cnt ++;
            }
        }
        return cnt <= 2;
    }
}
