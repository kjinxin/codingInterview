package rippling;
import java.util.*;
/*
https://leetcode.com/problems/wildcard-matching/
https://www.1point3acres.com/bbs/thread-876238-1-1.html
 */
public class WildCardMatching {
    public boolean isMatch(String s, String p) {
        int sLen = s.length(), pLen = p.length();

        boolean[][] dp = new boolean[pLen + 1][sLen + 1];
        if (p.equals(s)) {
            return true;
        }
        if (pLen > 0 && p.chars().allMatch(c -> c== '*')) {
            return true;
        }
        if (sLen == 0 || pLen == 0) {
            return false;
        }

        dp[0][0] = true;
        for (int pIdx = 1; pIdx < pLen + 1; pIdx ++) {
            char pCh = p.charAt(pIdx - 1);
            if (pCh == '*') {
                int sIdx = 1;
                while(sIdx < sLen + 1 && !dp[pIdx - 1][sIdx - 1]) {
                    sIdx ++;
                }
                dp[pIdx][sIdx - 1] = dp[pIdx - 1][sIdx - 1];
                while(sIdx < sLen + 1) {
                    dp[pIdx][sIdx ++] = true;
                }
            } else {
                for (int sIdx = 1; sIdx < sLen + 1; sIdx ++) {
                    if (pCh == '?') {
                        dp[pIdx][sIdx] = dp[pIdx - 1][sIdx - 1];
                    } else {
                        dp[pIdx][sIdx] = dp[pIdx - 1][sIdx - 1] && (pCh == s.charAt(sIdx - 1));
                    }
                }
            }
        }

        return dp[pLen][sLen];
    }
}
