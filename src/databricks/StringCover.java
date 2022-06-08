package databricks;

import java.util.*;

public class StringCover {
    public int[][] delete(int[][] covers, int index, String refStr) {
        int curIdx = 0;
        List<int[]> resList = new ArrayList<>();
        for (int[] cover : covers) {
            int diff = cover[1] - cover[0];
            if (index >= curIdx && index < curIdx + diff) {
                if (diff > 1) {
                    if (curIdx == index) {
                        resList.add(new int[]{cover[0] + 1, cover[1]});
                    } else if (index == curIdx + diff - 1) {
                        resList.add(new int[]{cover[0], cover[1] - 1});
                    } else {
                        resList.add(new int[]{cover[0], cover[0] + index - curIdx});
                        resList.add(new int[]{cover[0] + index - curIdx, cover[1]});
                    }
                }
            } else {
                resList.add(cover);
            }
            curIdx += diff;
        }

        List<int[]> combinedRes = new ArrayList<>();
        int[] pre = resList.get(0);
        for (int i = 1; i < resList.size(); i ++) {
            int[] cover = combine(pre, resList.get(i), refStr);
            if (cover[0] == pre[0] && cover[1] == pre[1]) {
                combinedRes.add(cover);
                pre = resList.get(i);
            } else {
                pre = cover;
            }
        }
        combinedRes.add(pre);
        return combinedRes.toArray(new int[combinedRes.size()][2]);
    }

    private int[] combine(int[] first, int[] second, String refStr) {
        String toMatch = refStr.substring(first[0], first[1]) + refStr.substring(second[0], second[1]);
        boolean flag = true;
        for (int i = 0; i < refStr.length() - toMatch.length() + 1; i ++) {
            for (int j = 0; j < toMatch.length(); j ++) {
                if (refStr.charAt(i + j) != toMatch.charAt(j)) {
                    flag = false;
                    continue;
                }
            }
            if (flag) {
                return new int[]{i, i + toMatch.length()};
            }
            flag = true;
        }
        return first;
    }
    public static void main(String[] args) {
        StringCover stringCover = new StringCover();
        for (int[] cover : stringCover.delete(new int[][]{{7, 11}, {3, 6}}, 3, "abc1234abcd!")) {
            System.out.printf("%d %d%n", cover[0], cover[1]);
        }
    }
}
