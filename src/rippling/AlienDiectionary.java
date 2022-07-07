package rippling;
import java.util.*;
/*
https://www.1point3acres.com/bbs/thread-888019-1-1.html
https://leetcode.com/problems/alien-dictionary/
 */
public class AlienDiectionary {
    public String alienOrder(String[] words) {
        Map<Character, List<Character>> adjList = new HashMap();
        Map<Character, Integer> counts = new HashMap();
        Queue<Character> q = new ArrayDeque();
        StringBuilder res = new StringBuilder();
        // initialize adjList and counts
        for (String word : words) {
            for (Character ch : word.toCharArray()) {
                if (!adjList.containsKey(ch)) {
                    adjList.put(ch, new ArrayList());
                    counts.put(ch, 0);
                }
            }
        }

        // construct adjList and counts
        for (int i = 0; i < words.length - 1; i ++) {
            String cur = words[i];
            String next = words[i + 1];
            if (cur.length() > next.length() && cur.startsWith(next)) {
                return "";
            }
            for (int j = 0; j < cur.length(); j ++) {
                char curCh = cur.charAt(j);
                char nextCh = next.charAt(j);
                if (curCh != nextCh) {
                    adjList.get(curCh).add(nextCh);
                    counts.put(nextCh, counts.get(nextCh) + 1);
                    break;
                }
            }
        }

        // initialize BFS queue
        for (Character ch : counts.keySet()) {
            if (counts.get(ch) == 0) {
                q.add(ch);
            }
        }
        while(!q.isEmpty()) {
            char curCh = q.poll();
            res.append(curCh);
            for (Character ch : adjList.get(curCh)) {
                counts.put(ch, counts.get(ch) - 1);
                if (counts.get(ch) == 0) {
                    q.add(ch);
                }
            }
        }
        return res.length() == counts.size() ? res.toString() : "";
    }
}
